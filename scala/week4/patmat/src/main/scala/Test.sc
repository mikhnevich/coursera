import scala.io.Source

val in = Source.fromFile("/home/user/Downloads/linuxwords.txt")
val words = in.getLines().toList filter (word => word forall (chr => chr.isLetter)) map (word => word.toLowerCase)
words.size
val mnemonics = Map(
  '2' -> "abc", '3' -> "def", '4' -> "ghi", '5' -> "jkl",
  '6' -> "mno", '7' -> "pqrs", '8' -> "tuv", '9' -> "wxyz"
)
val charCode: Map[Char, Char] = for ((digit, str) <- mnemonics; ltr <- str) yield ltr -> digit
def wordCode(word: String): String = word map charCode
val wordsForNum: Map[String, Seq[String]] = words groupBy wordCode withDefaultValue Seq()
def encode(number: String): Set[List[String]] = {
  if (number.isEmpty) Set(List())
  else {
    for {
      split <- 1 to number.length
      word <- wordsForNum(number take split)
      rest <- encode(number drop split)
    } yield word :: rest
  }.toSet
}

def translate(number: String): Set[String] =
  encode(number) map (_ mkString " ")

translate("4232867")

