import scala.util.Random

object RandomFeedback extends App {

  val s = Set("Emp_10001", "Emp_20002", "Emp_30003", "Emp_40004", "Emp_50005", "Emp_60006", "Emp_70007", "Emp_80008")
  val sample = 3

  def --() = print(s"\n ---------------------")

  if (sample > -1 && sample < s.size) {
    val persRev = recSupp(sample, s) filter (_._1._1.nonEmpty)
    print(s"\n appraise -> appraiser")
    --()
    persRev foreach { kv => print(s"\n${kv._1._1.get}\t\t${kv._1._2.flatten}") }

    print(s"\n\n appraiser -> appraise")
    --()
    persRev.map(_._1).flatMap(pr => pr._2.map(_.get -> pr._1.get)).groupBy(_._1) foreach { rp => print(s"\n${rp._1}\t\t${rp._2.map(_._2).toList}") }
  }
  else println("Sample should be between Zero and team size")


  def recSupp(sample: Int, s: Set[String]): Set[((Option[String], List[Option[String]]), List[Option[String]])] = {

    val removeNone = generatePaper(sample, s) filter (_._1._1.nonEmpty)
    removeNone.count(_._1._2.flatten.size != sample) match {
      case 0 => removeNone
      case _ => recSupp(sample, s)
    }
  }

  def generatePaper(sample: Int, s: Set[String]) = {

    print("- ")
    val l: List[Set[String]] = List.fill(sample)(s)
    s.scanLeft((None: Option[String]) -> (Nil: List[Option[String]]) -> (Nil: List[Option[String]])) {
      (listOfP, eachP) =>
        val sendNewLs: List[Set[String]] = l.map(_ -- listOfP._2.flatten.groupBy(identity).view.mapValues(_.size).filter(_._2 == sample).keySet)
        val calFun = giveRandomPerson(sendNewLs, eachP)
        val overAll = listOfP._2 ::: calFun._2
        Some(calFun._1) -> calFun._2 -> overAll
    }
  }

  def giveRandomPerson(l: List[Set[String]], p: String) = {

    val removedP: List[Set[String]] = l.map(_ - p)
    val random = new Random

    p -> removedP.foldLeft(Nil: List[Option[String]]) {
      (holdVal, e) =>
        val flatHoldVal: List[String] = holdVal.flatten
        val sToL: List[String] = (e -- flatHoldVal).toList
        val v = sToL.size match {
          case 0 => None
          case _ => Some(sToL(random.nextInt(sToL.size)))
        }
        v :: holdVal
    }
  }
}
