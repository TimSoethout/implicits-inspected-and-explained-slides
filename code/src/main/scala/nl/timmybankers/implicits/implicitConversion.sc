// amazing code examples
import java.util

import scala.language.implicitConversions

def toString(i: Int): String = i.toString

toString(1)

case class Balance(i: Int)
object Balance {
  implicit def balance2Int(balance: Balance): Int = balance.i
}

val balance = Balance(100)
toString(balance)


// command + click
// control + q
1 to 10


// JavaConversions
val javaSet: util.List[Int] = java.util.Arrays.asList(1, 2, 3)

def someDefUsingList(list: scala.collection.mutable.Buffer[Int]) = {
  // do something
}

// Will not work
//someDefUsingList(javaSet)

// with implicit conversions
import scala.collection.JavaConversions._

someDefUsingList(javaSet)


// without Conversions, because they are evil
import scala.collection.JavaConverters._

someDefUsingList(javaSet.asScala)