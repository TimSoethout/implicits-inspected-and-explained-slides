object NaiveSerializer {
  trait JsonSerializable2 {
    def toJsonString: String
  }

  type Amount = BigDecimal
  case class Account(name: String, balance: Amount) extends JsonSerializable2 {
    override def toJsonString: String = s"{name: $name, balance: $balance}"
  }
}

// We want to make JSON from these event
// for example to return for a new created account response message
val naiveAccount = NaiveSerializer.Account("My Account", 1000)
naiveAccount.toJsonString