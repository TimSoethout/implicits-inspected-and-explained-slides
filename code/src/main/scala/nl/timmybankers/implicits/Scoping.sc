// see usage -> same package has precedence
import nl.timmybankers.implicits.scoping.{CaseClass, Examples}

// package object, companion object?
Examples.implicitlyCaseClass()
//println(implicitly[CaseClass])

// try commenting out the implicit in the companion object

// wildcard import
//Examples.wildcard()

//Examples.explicit()
//Examples.explicit()