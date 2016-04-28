package nl.timmybankers.implicits.scoping

case class CaseClass(content: String)

object CaseClass {
  implicit val x = CaseClass("defined in companion object of CaseClass")
}