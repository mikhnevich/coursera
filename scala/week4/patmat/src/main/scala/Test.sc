
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
decode(frenchCode, secret)

