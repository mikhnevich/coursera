package streams

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.concurrent.Timeouts
import org.scalatest.junit.JUnitRunner

import Bloxorz._
import org.scalatest.time.{Milliseconds, Seconds, Span}
import org.scalatest.time.SpanSugar._

@RunWith(classOf[JUnitRunner])
class BloxorzSuite extends FunSuite with Timeouts {

  trait SolutionChecker extends GameDef with Solver with StringParserTerrain {
    /**
     * This method applies a list of moves `ls` to the block at position
     * `startPos`. This can be used to verify if a certain list of moves
     * is a valid solution, i.e. leads to the goal.
     */
    def solve(ls: List[Move]): Block =
      ls.foldLeft(startBlock) { case (block, move) => move match {
        case Left => block.left
        case Right => block.right
        case Up => block.up
        case Down => block.down
      }
      }
  }

  trait Level1 extends SolutionChecker {
    /* terrain for level 1*/

    val level =
      """ooo-------
        |oSoooo----
        |ooooooooo-
        |-ooooooooo
        |-----ooToo
        |------ooo-""".stripMargin

    val optsolution = List(Right, Right, Down, Right, Right, Right, Down)
  }

  test("terrain function level 1") {
    new Level1 {
      assert(terrain(Pos(0, 0)), "0,0")
      assert(!terrain(Pos(4, 11)), "4,11")
    }
  }

  test("findChar level 1") {
    new Level1 {
      assert(startPos == Pos(1, 1))
    }
  }

  test("neighborsWithHistory") {
    new Level1 {
      val testBlock = new Block(Pos(1, 1), Pos(1, 1))
      val stream = neighborsWithHistory(testBlock, List(Left, Up))
      val set = stream.toSet
      assert(set.size == 2)
      assert(set.contains((Block(Pos(1, 2), Pos(1, 3)), List(Right, Left, Up))))
      assert(set.contains((Block(Pos(2, 1), Pos(3, 1)), List(Down, Left, Up))))
    }
  }


  test("newNeighborsOnly") {
    new Level1 {
      val t = newNeighborsOnly(
        Set(
          (Block(Pos(1, 2), Pos(1, 3)), List(Right, Left, Up)),
          (Block(Pos(2, 1), Pos(3, 1)), List(Down, Left, Up))
        ).toStream,
        Set(Block(Pos(1, 2), Pos(1, 3)), Block(Pos(1, 1), Pos(1, 1)))
      )
      assert(t == Set((Block(Pos(2, 1), Pos(3, 1)), List(Down, Left, Up))).toStream)
    }
  }

  test("optimal solution for level 1") {
    new Level1 {
      val foundSolution = solve(solution)
      //info("Found solution: " + foundSolution)
      assert(foundSolution == Block(goal, goal), "Found solution: " + foundSolution)
    }
  }

  test("optimal solution length for level 1") {
    new Level1 {
      assert(solution.length == optsolution.length)
    }
  }

  trait Level3 extends SolutionChecker {
    /* terrain for level 3*/

    val level =
      """------ooooooo--
        |oooo--ooo--oo--
        |ooooooooo--oooo
        |oSoo-------ooTo
        |oooo-------oooo
        |------------ooo""".stripMargin

    val optsolution = List(Right, Up, Right, Right, Right, Up, Left, Down, Right, Up, Up, Right, Right, Right, Down, Down, Down, Right, Up)
  }

  test("optimal solution for level 3") {
    new Level3 {
      val foundSolution = solve(solution)
      info("Found solution: " + solution)

      assert(foundSolution == Block(goal, goal), "Found solution: " + solution)
      assert(solution.length == optsolution.length)
      assert(solution == optsolution)
    }
  }


  /*
    trait Level1V extends Level1 with SolutionVisualizer {}

    trait SolutionVisualizer extends GameDef with Solver with StringParserTerrain {
      def clearScreen: Unit = print("\033[2J\033[1;1H")
      def cursorOff: Unit = print("\033[?25l")
      def cursorOn: Unit = print("\033[?25h")
      def printAt(x: Int, y: Int, c: Char): Unit = {
        print("\033[" + (x + 1) + ";" + (y + 1) + "H" + c)
      }

      def printBlock(b: Block, c: Char): Unit = {
        def clearAndPrintAt(p: Pos, c: Char): Unit = {
          if (c == '\000') printAt(p.x, p.y, vector(p.x)(p.y))
          else printAt(p.x, p.y, c)
        }

        if (!b.isStanding) {
          clearAndPrintAt(b.b1, c)
        }
        clearAndPrintAt(b.b2, c)
      }

      def displayBlock(b: Block): Unit = {
        printBlock(b, '#')
        Thread.sleep(1000)
        printBlock(b, '\000')
      }

      def displayTerrain(levelVector: Vector[Vector[Char]]): Unit = {
        for (i <- 0 to levelVector.size - 1; j <- 0 to levelVector(i).size - 1) {
          printAt(i, j, levelVector(i)(j))
        }
        println
      }

      def displaySolution(ls: List[Move]): Unit = {
        clearScreen
        cursorOff
        displayTerrain(vector)
        ls.foldLeft(startBlock) {
          case (block, move) => move match {
            case Left => { displayBlock(block); block.left }
            case Right => { displayBlock(block); block.right }
            case Up => { displayBlock(block); block.up }
            case Down => { displayBlock(block); block.down }
          }
        }
        displayTerrain(vector)
        cursorOn
      }

    }

    test("display solution Level1V") {
      new Level1V {
        displaySolution(solution)
      }
    }

    trait Level6V extends SolutionVisualizer {

      val level =
        """-----oooooo
          |-----o--ooo
          |-----o--ooooo
          |Sooooo-----oooo
          |----ooo----ooTo
          |----ooo-----ooo
          |------o--oo
          |------ooooo
          |------ooooo
          |-------ooo""".stripMargin

      //    val optsolution = ...
    }

    test("display solution Level6") {
      new Level6V {
        displaySolution(solution)
        println(solution)
      }
    }
  */

  trait InfiniteSolutionChecker extends GameDef with Solver with InfiniteTerrain {
    /**
     * This method applies a list of moves `ls` to the block at position
     * `startPos`. This can be used to verify if a certain list of moves
     * is a valid solution, i.e. leads to the goal.
     */
    def solve(ls: List[Move]): Block =
      ls.foldLeft(startBlock) { case (block, move) => move match {
        case Left => block.left
        case Right => block.right
        case Up => block.up
        case Down => block.down
      }
      }
  }


  trait Level1Infinite extends InfiniteSolutionChecker {
    //     terrain for level 1

    val startPos = new Pos(1, 1)
    val goal = new Pos(4, 7)

    val level =
      """ooo-------
        |oSoooo----
        |ooooooooo-
        |-ooooooooo
        |-----ooToo
        |------ooo-""".stripMargin

    val optsolution = List(Down, Down, Right, Right, Right, Right)
  }


  test("optimal solution for level 1 - infinite terrain") {
    new Level1Infinite {
      //      info(solution.toString)
      assert(solution.length == optsolution.length, solution.length)
      assert(solve(solution) == Block(goal, goal))
    }
  }


  trait NoSolution extends SolutionChecker {
    /* terrain for level 1*/

    //    val level =
    //      """ooST
    //        |--""".stripMargin
    //
    val level =
      """ooo-------
        |oSoooo----
        |ooooooooo-
        |-oooooo-oo
        |-----o-T-o
        |------o-o-""".stripMargin

  }

  trait InfiniteNoSolution extends InfiniteSolutionChecker {
    val startPos = new Pos(1, 1)
    val goal = new Pos(4, 7)
    override val terrain: Terrain = (pos: Pos) => pos match {
      case Pos(0, 1) => false
      case Pos(1, 0) => false
      case Pos(1, 2) => false
      case Pos(2, 1) => false
      case _ => true
    }
  }


  failAfter(Span(20, Seconds)) {
    new InfiniteNoSolution {
      info("Infinite no solution")
      info("Solution block: " + solve(solution))
      info("Solution: " + solution.toString)
      assert(solve(solution) == startBlock)
      assert(solution == List())
    }
  }
}
