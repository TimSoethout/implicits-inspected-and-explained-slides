import scala.language.implicitConversions

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
class StringWrapper(s: String) {
  def quoted = s""""$s""""
}

//"my String".quoted

new StringWrapper("my String").quoted

object StringWrapper {
  implicit def wrapString(s : String): StringWrapper = new StringWrapper(s)
}
import StringWrapper.wrapString

"my other String".quoted

def someDefUsingQuotedStrings[T <% StringWrapper](t : T) = t.quoted
// View bounds are deprecated

someDefUsingQuotedStrings("my String")

// We need to evidence that there is a view of `T` to `StringWrapper`
def useStringWrapper[T](t : T)(implicit ev: T => StringWrapper) = t.quoted

useStringWrapper("my other string")

//useStringWrapper(42) // no implicit view available

implicit def int2StringWrapper : Int => StringWrapper = i => new StringWrapper(i.toString)
useStringWrapper(42)

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
