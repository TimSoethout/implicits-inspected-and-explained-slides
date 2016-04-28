// amazing code examples


import scala.language.implicitConversions

def toString(i: Int): String = i.toString

toString(1)

case class Balance(i: Int)
object Balance {
  implicit def balance2Int(balance: Balance): Int = balance.i
}

val balance = Balance(100)
toString(balance)

// See with :javap
def example : Int = balance


// command + click
// control + q
1 to 10

// JavaConversions
import java.util

val javaList: util.List[Int] = java.util.Arrays.asList(1, 2, 3)

def someDefUsingList(list: scala.collection.mutable.Buffer[Int]) = {
  println(list)
}

// Will not work
//someDefUsingList(javaList)

// with implicit conversions
import scala.collection.JavaConversions._

someDefUsingList(javaList)


// without Conversions, because they are evil
import scala.collection.JavaConverters._

someDefUsingList(javaList.asScala)