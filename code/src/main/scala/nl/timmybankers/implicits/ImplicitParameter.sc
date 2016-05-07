def giveMeAnInt(implicit i : Int) = i

// Use it like a normal method
giveMeAnInt(100)

// Try commenting this out and compiling
implicit val someInt : Int = 42

// and this one
//implicit val someOtherInt : Int = 52

giveMeAnInt

//another way
implicitly[Int]

// show with javap -v what happens
def someCall = giveMeAnInt

// Implicit view
// TODO make nice code example for implicit view. i.e. File => FileWrapper, Int => RichInt

// Typeclass example
trait StringHelper[A] {
  def readable(a: A): String
}

def readable[A](a: A)(implicit helper: StringHelper[A]) =
  helper.readable(a)

case class Balance(amount: Int)
object Balance {
  implicit val toStringHelper: StringHelper[Balance] =
    new StringHelper[Balance] {
      override def readable(balance: Balance): String = s"EUR ${balance.amount}"
    }
}

readable(Balance(100))(Balance.toStringHelper)
// ===
readable(Balance(100))
