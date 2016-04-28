

object Model {
  type Amount = BigDecimal
  case class Account(name: String, balance: Amount)
  case class Payment(fromAccount: Account, toAccount: Account, amount: Amount)
}

val myAccount = Model.Account("My Account", 1000)
val yourAccount = Model.Account("Your Account", 2000)
val payment = Model.Payment(myAccount, yourAccount, 500)

// how to show this?
/*
first inline (now)
then with implicit class
or implicit conversions a la spray json
then circe?
 */
object ModelSerializer {
  trait JsonWriter[T] {
    def toJsonString(value: T): String
  }

  implicit val jsonString = new JsonWriter[String] {
    override def toJsonString(value: String): String = s""""$value""""
  }

  implicit val jsonBigDecimal = new JsonWriter[BigDecimal] {
    override def toJsonString(value: BigDecimal): String = value.toString
  }

  implicit val jsonAccount = new JsonWriter[Model.Account] {
    override def toJsonString(value: Model.Account): String = {
      val jsonString = implicitly[JsonWriter[String]]
      val jsonBigDecimal = implicitly[JsonWriter[BigDecimal]]
      s"""{"name": "${jsonString.toJsonString(value.name)}", "balance": "${jsonBigDecimal.toJsonString(value.balance)}"}"""
    }
  }
}

import ModelSerializer._

implicit class JsonSerializable[T](serializable: T)
                                  (implicit serializer: JsonWriter[T]) {
  def toJson: String = serializer.toJsonString(serializable)
}


myAccount.toJson

// next

object NicerSerializableModel {
  // Or even nicer
  object JsonWriter {
    // Helper to construct writers more easily
    def apply[T](func: T => String): JsonWriter[T] = new JsonWriter[T] {
      override def toJsonString(value: T): String = func(value)
    }
  }

  implicit def jsonMap[K: JsonWriter, V: JsonWriter]: JsonWriter[Map[K, V]] = {
    JsonWriter[Map[K, V]]((map: Map[K, V]) => {
      val items = for {
        (k, v) <- map
      } yield s"${k.toJson} : ${v.toJson}"
      s"{${items.mkString(",")}}"
    })
  }

  implicit val jsonPayment: JsonWriter[Model.Payment] =
    JsonWriter[Model.Payment](payment =>
      Map("from" -> payment.fromAccount.name.toJson,
        "to" -> payment.toAccount.name.toJson,
        "balance" -> payment.amount.toJson).toJson)
}

import NicerSerializableModel._

Map("a" -> "1", "b" -> "2").toJson

payment.toJson

// This won't work
//10.toJson

