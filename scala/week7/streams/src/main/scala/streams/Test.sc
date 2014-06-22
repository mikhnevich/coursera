import streams.{StringParserTerrain, Solver, GameDef}

println("test")

object MyTest extends GameDef with Solver with StringParserTerrain {
  val level =
    """ooo-------
      |oSooTo----
      |------ooo-""".stripMargin
  /*
    val level =
      """ooo-------
        |oSoooo----
        |ooooooooo-
        |-ooooooooo
        |-----ooToo
        |------ooo-""".stripMargin
  */

  def from2(initial: Stream[(Block, List[Move])], explored: Set[Block]): Stream[(Block, List[Move])] = {
    val more = newNeighborsOnly(initial, explored)
    val more2 = newNeighborsOnly(more,  explored)
    more #::: more2
  }


  val k = neighborsWithHistory(startBlock, List())
  val testN = neighborsWithHistory(startBlock, List())
  val legalNeighborsFromStart = startBlock.legalNeighbors.foldLeft(List[(Block, List[Move])]())((acc, b) => (b._1, List(b._2)) :: acc).toStream
  //val exploredSoFar = testN.foldLeft(Set[Block](startBlock))((acc, tuple) => acc + tuple._1)
  val paths = from(legalNeighborsFromStart, Set[Block](startBlock))
  val paths2 = from2(legalNeighborsFromStart, Set[Block](startBlock))

}

println("test")
val g = MyTest
for (i <- 0 to 5; j <- 0 to 9) {
  if (j == 0) {
    println
  }
  if (g.terrain(MyTest.Pos(i, j))) {
    if (g.startPos.x == i && g.startPos.y == j) print("S")
    else if (g.goal.x == i && g.goal.y == j) print("T")
    else print('o')
  } else print('-')
}

val legalNeighborsFromStart = g.legalNeighborsFromStart
val from2 = g.paths2
val from2Tail = g.paths2.tail
val from2TailTail = g.paths2.tail.tail

