import scala.io.Source
import scala.util.matching.Regex

/**
  * Created by Matt on 14/12/2017.
  */
object LanguageDetectionConsole extends App {

  val text: String = Source.fromResource("loremIpsum.txt").getLines().mkString("")

  // Single chars
  val charCounts = text
    .toCharArray
    .map(_.toLower)
    .filter(allowableCharacter)
    .groupBy(x => x)
    .map(x => (x._1, x._2.length))

  charCounts.foreach(println(_))

  //Pairs of chars
  val textArray = text.toLowerCase.toCharArray

  val groupedPairs = textArray
    .zip(textArray.tail)
    .filter(x => allowableCharacter(x._1) && allowableCharacter(x._2))
    .groupBy(x => x)
    .map(x => (x._1, x._2.length))

  val totalOccurance = groupedPairs.values.sum

  val weightedGroupsPairs = groupedPairs.map(x => (x._1, BigDecimal(x._2) / BigDecimal(totalOccurance)))

  weightedGroupsPairs.foreach(println(_))

  
  def allowableCharacter(character: Char): Boolean = {
    val pattern: Regex = """[a-z]""".r

    pattern.pattern.matcher(character.toString).matches()
  }
}
