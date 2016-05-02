package nl.timmybankers.implicits.scoping

object Wildcard {
  implicit val explicitCaseClass: CaseClass = CaseClass("defined in object Wildcard")
}
