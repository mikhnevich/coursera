import streams.{StringParserTerrain, Solver, GameDef}

println("test")
object MyTest extends GameDef with Solver with StringParserTerrain {
  val level =
    """ooST
      |--""".stripMargin


  val paths = pathsFromStart

}

val g = MyTest
g.paths.head
g.paths.tail.head
g.paths.tail.tail.head

