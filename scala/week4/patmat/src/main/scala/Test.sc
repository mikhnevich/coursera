
import patmat.Huffman._

val sampleTree = makeCodeTree(
  makeCodeTree(Leaf('x', 1), Leaf('e', 1)),
  Leaf('t', 2)
)

//weight(sampleTree)
//chars(sampleTree)
//val v = times(string2Chars("aaaaaaaabbbcdefgh"))
//val tree = createCodeTree(string2Chars("aaaaabbbbcccdde"))
//decode(tree, List(0, 0, 0))
val ct1 = List(('a', List(0)))
val ct2 = List(('b', List(1)))
val ct3 = List(('c', List(1)))
val a = mergeCodeTables(ct1, ct2)
val b = mergeCodeTables(a, ct3)
