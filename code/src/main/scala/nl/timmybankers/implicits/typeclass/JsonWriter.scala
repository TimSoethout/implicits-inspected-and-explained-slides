package nl.timmybankers.implicits.typeclass

trait JsonWriter[T] {
  def toJsonString(value: T): String
}

object Model {
  type Amount = BigDecimal
  case class Account(name: String, balance: Amount)
  case class Payment(fromAccount: Account, toAccount: Account, amount: Amount)
}

object JsonWriter {
  implicit val jsonString = new JsonWriter[String] {
    override def toJsonString(value: String): String = value
  }

  implicit val jsonBigDecimal = new JsonWriter[BigDecimal] {
    override def toJsonString(value: BigDecimal): String = value.toString()
  }

  implicit val jsonAccount = new JsonWriter[Model.Account] {
    override def toJsonString(value: Model.Account): String = {
      val jsonString = implicitly[JsonWriter[String]]
      val jsonBigDecimal = implicitly[JsonWriter[BigDecimal]]
      s"{name: ${jsonString.toJsonString(value.name)}, balance: ${jsonBigDecimal.toJsonString(value.balance)}}"
    }
  }
}