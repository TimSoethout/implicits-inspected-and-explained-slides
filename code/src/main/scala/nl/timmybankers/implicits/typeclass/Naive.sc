trait JsonSerializable {
  def toJsonString: String
}

type Amount = BigDecimal
case class Account(name: String, balance: Amount) extends JsonSerializable {
  override def toJsonString: String = s"""{"name": "$name", "balance": "$balance"}"""
}
case class Payment(from: Account, to: Account, amount: Amount) extends JsonSerializable {
  override def toJsonString: String = s"""{"from": "${from.name}", "to": "${to.name}", "amount": "$amount"}"""
}
case class Person(firstName: String, lastName: String, age: Int) extends JsonSerializable {
  override def toJsonString: String = s"""{"firstName": "$firstName", "firstName": "$lastName", "age": "$age"}"""
}


// We want to make JSON from these event
// for example to return for a new created account response message
val naiveAccount = Account("My Account", 1000)
naiveAccount.toJsonString

// Works, but error prone (firstName)
Person("Tim", "Soethout", 27).toJsonString
