// see usage -> same package has precedence
import nl.timmybankers.implicits.scoping.Examples

// local defined implicit
Examples.local()

// package object, companion object?
Examples.implicitlyCaseClass()

// try commenting out the implicit in the companion object

// wildcard import
Examples.wildcard()

//Examples.explicit()
Examples.explicit()
