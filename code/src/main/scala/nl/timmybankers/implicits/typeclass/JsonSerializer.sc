object Model {
  type Amount = BigDecimal
  case class Account(name: String, balance: Amount)
  case class Payment(fromAccount: Account, toAccount: Account, amount: Amount)
}

val myAccount = Model.Account("My Account", 1000)
val yourAccount = Model.Account("Your Account", 2000)
val payment = Model.Payment(myAccount, yourAccount, 500)

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

val accountWriter = implicitly[JsonWriter[Model.Account]]
accountWriter.toJsonString(myAccount)

implicit class JsonSerializable[T](serializable: T)
                                  (implicit serializer: JsonWriter[T]) {
  def toJson: String = serializer.toJsonString(serializable)
}


myAccount.toJson

// This won't work because there are is no JsonWriter[Int] in scope
//10.toJson

// next

object NicerSerializableModel {
  // Or even nicer
  object JsonWriter {
    // Helper to construct writers more easily
    def apply[T](func: T => String): JsonWriter[T] = new JsonWriter[T] {
      override def toJsonString(value: T): String = func(value)
    }
  }

//  implicit def jsonMap[K, V](implicit kWriter : JsonWriter[K], vWriter : JsonWriter[V]): JsonWriter[Map[K, V]] = {
  // ===
  implicit def jsonMap[K: JsonWriter, V: JsonWriter]: JsonWriter[Map[K, V]] = {
    JsonWriter((map: Map[K, V]) => {
      val items = for {(k, v) <- map}
        yield s"${k.toJson}: ${v.toJson}"
      s"{${items.mkString(", ")}}"
    })
  }

  // :javap NicerSerializableModel, search for map

  implicit val jsonPayment: JsonWriter[Model.Payment] =
    JsonWriter[Model.Payment](payment =>
      Map("from" -> payment.fromAccount.name.toJson,
        "to" -> payment.toAccount.name.toJson,
        "balance" -> payment.amount.toJson).toJson)
}

import NicerSerializableModel._

Map("a" -> "1", "b" -> "2").toJson

payment.toJson


// even more generic, using Product

object GenericSerializableModel {
  implicit def jsonProduct: JsonWriter[Product] = {
    // bit of cheating here to get it working for Any
    implicit val anyProduct: JsonWriter[Any] = JsonWriter(any => any.toString)
    JsonWriter(product => {
      val map = product.getClass.getDeclaredFields.map(_.getName) // all field names
        .zip(product.productIterator.to).toMap // zipped with all values
      map.mapValues(_.toJson).toJson
    })
  }
}

case class Person(firstName: String, lastName: String, age: Int)

val person = Person("Tim", "Soethout", 27)

person.isInstanceOf[Product]

import GenericSerializableModel._

jsonProduct.toJsonString(person)


// due to bugs in worksheet we have to be a bit more explicit about this
// TODO: find out why
// fix with shapeless HList?
//person.toJson
val personProduct : Product = person
personProduct.toJson
