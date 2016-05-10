package nl.timmybankers.implicits.scoping

/** Holder for testcases
  * `implicitly[T]` is a way to get the value that is the implicit for type `T` in this scope
  */
object Examples {
  def resolve(implicit caseClass : CaseClass) = println(caseClass)

  def implicitlyCaseClass() = {
    resolve
  }

  def local() = {
    implicit val localCaseClass = CaseClass("defined in local val")

    resolve
  }

  def wildcard() = {
    import Wildcard._
    resolve
  }

  def explicit() = {
    import Explicit.explicitCaseClass
//    import Wildcard._
    resolve
  }



}
