// see usage -> same package has precedence
import nl.timmybankers.implicits.scoping.Examples

//Scoping.exampleSamePackage
Examples.companionObject

// try commenting out the implicit in the companion object
Examples.packageObject

// wildcard import
Examples.wildcard

// explicit more precendence
Examples.explicit