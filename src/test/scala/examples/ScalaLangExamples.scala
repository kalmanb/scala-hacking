package examples

import scala.annotation.tailrec
import scala.beans.BeanProperty

import org.junit.runner.RunWith
import org.scalatest.FunSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

class ScalaLangExamples extends FunSpec with ShouldMatchers {

  describe("base lang") {
    it("variables") {
      var variable = "var" // string var, mutable - try not to use
      val value = "one " + "two" // string val - evaluated once
      lazy val lazie = "one " + "two" // only evaluated when needed but only once (be careful tho)
      def function = "one " + "two" // evaluated every time it's called
    }

    it("functions") {
      def coolMethod(count: Int = 0, name: String = "default") =
        s"""count:$count name:$name"""
      println(coolMethod())
      println(coolMethod(10, "two"))
      println(coolMethod(name = "just a string"))
    }

    it("optional examples") {
      // Option
      var badVar: String = null
      val goodVar: Option[String] = None //note val vs var

      // Using Optional
      goodVar match {
        case Some(value) ⇒ { println(value) }
        case None        ⇒ { println("not found") }
      }

      // Other ways
      val usableVar = goodVar.getOrElse("not found")
      println(usableVar)

      val another = goodVar.map(v ⇒ s"hey $v")
      println(another)
      val another2 = goodVar map (v ⇒ s"hey $v")

      // awesome way
      val user: Option[String] = Some("kal")
      val pass: Option[String] = None
      def validUser(user: String, pass: String) = user == pass

      user.map { u ⇒
        pass.map { p ⇒
          if (validUser(u, p))
            println(s"""user:$u logged in""")
        }
      }

      // Same thing different syntax
      // For comprensions
      for {
        u ← user // continue if found
        p ← pass // continue if found
        if validUser(u, p) // continue if valid
      } println(s"""user:$u logged in""")

    }

    it("for comprehensions") {
      val listInts = List(1, 2, 3)
      val names = List("a", "b")
      val listNames = for {
        listInt ← listInts
        name ← names
      } yield s"name: $listInt, $name"

      listNames foreach println
      listNames should contain("name: 2, b")
    }

    it("block examples") {
      // Bad if block
      var badResult = ""
      if (1 == 1)
        badResult = "good"
      else
        badResult = "bad"
      println(badResult)

      // Better (no vars)
      val goodResult = if (1 == 1)
        "good"
      else
        "bad"
      println(goodResult)

      /** TRY Blocks */
      var badTry = ""
      try {
        // try to read file
        badTry = "..."
      } catch {
        case e: Throwable ⇒ e.printStackTrace
      }
      println(badTry)

      val goodTry = try {
        "..."
      } catch {
        case e: Throwable ⇒ e.printStackTrace
      }
      println(goodTry)

      // Use 'T'ry
      import scala.util.Try
      val bestTry = Try {
        "..."
      }

      import scala.util.Success
      import scala.util.Failure
      bestTry match {
        case Success(string) ⇒ println(string)
        case Failure(e)      ⇒ println(e)
      }

      // Can compose them
      def bestTry2(input: String) = Try {
        Failure(new Exception("oh no"))
      }

      for {
        firstResult ← bestTry
        secondResult ← bestTry2(firstResult)
      } secondResult
    }

    it("entities") {
      /**
       * Java Example
       *
       * public class Person {
       *   private String firstName;
       *   private String lastName;
       *
       * public Person(String firstName, String lastName) {
       *   this.firstName = firstName;
       *   this.lastName = lastName;
       * }
       *
       * public String getFirstName() {
       *   return firstName;
       * }
       *
       * public void setFirstName(String firstName) {
       *   this.firstName = firstName;
       * }
       *
       * public String getLastName() {
       *   return lastName;
       * }
       *
       * public void setLastName(String lastName) {
       *   this.lastName = lastName;
       * }
       * }
       *
       */
      class JavaPerson(
        @BeanProperty var firstName: String,
        @BeanProperty var lastName: String)

      val javaPerson = new JavaPerson("kal", "bek")
      javaPerson.setFirstName("updated")
      println(javaPerson.getFirstName)

      class ScalaPerson(var firstName: String, var lastName: String)
      val scalaPerson = new ScalaPerson("kal", "bek")
      scalaPerson.firstName = "updated"
      println(scalaPerson.firstName)

      // Better (val's not vars)(case class)
      case class ScalaPersonVals(val firstName: String, val lastName: String, val age: Int = 100)
      println(ScalaPersonVals("kal", "bek"))
    }

    it("static classes - objects") {
      object SomeUtil {
        def add(i: Int, j: Int) = i + j
      }
      assert(SomeUtil.add(1, 2) === 3)

      import SomeUtil._
      assert(add(1, 2) === 3)
    }

    it("companion objects") {
      class Item(price: Int)
      object Item { // companion = same name
        def apply(price: Int) = new Item(price)
        def apply(price: Integer) = new Item(price.intValue)
      }
      // Old way
      val old = new Item(99)

      // With Apply Method
      val withApply = Item(99)
      val withJInt = Item(new Integer(99))

      // Automatically done with case class
      case class CaseItem(price: Int)
      val caseItem = CaseItem(99)
    }

    it("implicit method parameters") {
      case class Context()
      def ask(question: String)(implicit context: Context) = 42

      implicit val context = Context()
      ask("my question") should be(42)
    }

    // just an example, this would be a bad idea
    // see also scala.collections.JavaConversions._
    it("implicit conversions") {
      implicit def javaIntToScalaString(in: Integer): String = in.toString()
      val javaInteger = new Integer(123)

      def onlyTakesStrings(in: String) {}
      onlyTakesStrings(javaInteger) // automagically converted
    }

    it("recursion without tailrec") {
      def factorial(n: BigInt): BigInt =
        if (n == 1) 1
        else n * factorial(n - 1)

      factorial(5) should be(120)
      //		println(factorial(10000)) //uncomment to get StackOverflowError
    }

    it("recursion with tailrec") {
      @tailrec
      def factorial(n: BigInt, accu: BigInt = 1): BigInt =
        if (n == 1) accu
        else factorial(n - 1, accu * n)

      factorial(5) should be(120)
      factorial(10000) //number with 35k digits ;)
    }

    it("closures 1") {
      // this method returns (Double => Double)
      def multiplyBy(factor: Double) = (x: Double) ⇒ factor * x
      //def multiplyBy(factor: Double): Double => Double = (x: Double) ⇒ factor * x
      
      val triple = multiplyBy(3)
      val half = multiplyBy(0.5)

      triple(14) should be(42)
      half(14) should be(7)
    }

    it("closures 2") {
      def multiplyBy(factor: Double, map: (Double) ⇒ Double) = (x: Double) ⇒ map(factor * x)

      val triplePlusTwo = multiplyBy(3, (d: Double) ⇒ d + 2)
      val halfMinusTwo = multiplyBy(0.5, (d: Double) ⇒ d - 2)

      triplePlusTwo(14) should be(44)
      halfMinusTwo(14) should be(5)
    }

    it("traits can contain implementation") {
      trait T {
        def answer = 42
      }
      class C extends T
      new C().answer should be(42)
    }

    it("traits can be abstract") {
      trait T {
        def answer: Int
      }
      trait TImpl extends T {
        override def answer = 42
      }

      abstract class C extends T
      val instance = new C with TImpl
      instance.answer should be(42)
      // Notice that there could be different implementations of T!

      //advanced: self types
      class C2 { this: T ⇒ }
      val instance2 = new C2 with TImpl
      instance2.answer should be(42)
    }

    it("traits can be used for multiple inheritance") {
      trait T1 {
        def answer = 42
      }
      trait T2 {
        def somethingElse = 43
      }
      class C extends T1 with T2

      val instance = new C
      instance.answer should be(42)
      instance.somethingElse should be(43)
    }
  }
}
