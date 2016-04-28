package nl.timmybankers.implicits.scoping

/** Holder for testcases
  * `implicitly[T]` is a way to get the value that is the implicit for type `T` in this scope
  */
object Examples {

  def companionObject = {
    implicitly[CaseClass]
  }

  def packageObject = {
    implicitly[CaseClass]
  }

  def wildcard = {
    object Wildcard {
      implicit val x = CaseClass("defined in object Wildcard")
    }
    import Wildcard._
    implicitly[CaseClass]
  }

  def explicit = {
    object Explicit {
      implicit val x = CaseClass("defined in object Explicit")
    }
    object Wildcard {
      implicit val x = CaseClass("defined in object Wildcard")
    }
    import Explicit.x
    //    import Wildcard._
    implicitly[CaseClass]
  }

}