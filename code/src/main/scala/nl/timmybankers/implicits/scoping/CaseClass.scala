package nl.timmybankers.implicits.scoping

case class CaseClass(content: String)

object CaseClass {
  implicit val cc: CaseClass = CaseClass("defined in companion object")
}