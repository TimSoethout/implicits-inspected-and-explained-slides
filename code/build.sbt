name := "implicits-inspected-and-explained"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "2.4.3",
  "org.scala-lang" % "scala-compiler" % scalaVersion.value
)

initialCommands in console :=
  """  def init(intp: scala.tools.nsc.interpreter.IMain) = {
    |    val repl = REPLesent(0, 0, "src/main/repl/demos.txt", intp=intp)
    |    import repl._
    |    //h
    |    repl
    |  }
    |
    | println("To start presentation:")
    | println("val repl = init($intp); import repl._; f")
  """.stripMargin
