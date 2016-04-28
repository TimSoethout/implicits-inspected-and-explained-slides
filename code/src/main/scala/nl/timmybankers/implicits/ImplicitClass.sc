// For example defined in a (Java) library out of your control somewhere
case class Balance(amount: Int)

implicit class RichBalance(val balance: Balance) {
  def unary_- : Balance =
    balance.copy(amount = -balance.amount)

  def +(right: Balance): Balance =
    balance.copy(amount = balance.amount + right.amount)
}

val balance = Balance(1000)

// balance is automatically lifted to `RichBalance`
-balance

-balance + balance