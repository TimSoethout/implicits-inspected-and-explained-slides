package nl.timmybankers.implicits.scoping

object Explicit {
  implicit val explicitCaseClass: CaseClass = CaseClass("defined in object Explicit")
}