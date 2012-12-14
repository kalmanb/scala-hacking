package examples

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import scala.beans.BeanProperty
import scala.tools.util.Javap
import org.scalatest.matchers.ShouldMatchers
import scala.annotation.tailrec

@RunWith(classOf[JUnitRunner])
class ScalaLangExamples extends FunSuite with ShouldMatchers {

	test("variables") {
		var variable = "var" // string var, mutable - try not to use
		val value = "one " + "two" // string val - evaluated once
		lazy val lazie = "one " + "two" // only evaluated when needed but only once (be careful tho)
		def function = "one " + "two" // evaluated every time it's called
	}

	test("functions") {
		def coolMethod(count: Int = 0, name: String = "default") = s"""count:$count name:$name"""
		println(coolMethod())
		println(coolMethod(10, "two"))
		println(coolMethod(name = "just a string"))
	}

	test("optional examples") {
		// Option
		var badVar: String = null
		var goodVar: Option[String] = None

		// Using Optional
		goodVar match {
			case Some(value) => { println(value) }
			case None => { println("not found") }
		}

		// Other ways
		val usableVar = goodVar.getOrElse("not found")
		println(usableVar)

		// awesome way
		val user: Option[String] = Some("kal")
		val pass: Option[String] = None
		def validUser(user: String, pass: String) = user == pass
		for {
			u <- user // continue if found
			p <- pass // continue if found
			if validUser(u, p) // continue if valid
		} println(s"""user:$u logged in""")

	}

	test("block examples") {
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
			case e => e.printStackTrace
		}
		println(badTry)

		val goodTry = try {
			"..."
		} catch {
			case e: Throwable => e.printStackTrace
		}
		println(goodTry)
	}

	test("entities") {
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
		case class ScalaPersonVals(val firstName: String, val lastName: String)
		println(ScalaPersonVals("kal", "bek"))
	}

	test("implicit method parameters") {
		case class Context
		def ask(question: String)(implicit context: Context) = 42

		implicit val context = Context()
		ask("my question") should be(42)
	}

	// just an example, this would be a bad idea
	// see also scala.collections.JavaConversions._
	test("implicit conversions") {
		implicit def javaIntToScalaString(in: Integer): String = in.toString()
		val javaInteger = new Integer(123)

		def onlyTakesStrings(in: String) {}
		onlyTakesStrings(javaInteger) // automagically converted
	}

	test("recursion without tailrec") {
		def factorial(n: BigInt): BigInt =
			if (n == 1) 1
			else n * factorial(n - 1)

		factorial(5) should be(120)
		//		println(factorial(10000)) //uncomment to get StackOverflowError
	}

	test("recursion with tailrec") {
		def factorial(n: BigInt, accu: BigInt = 1): BigInt =
			if (n == 1) accu
			else factorial(n - 1, accu * n)

		factorial(5) should be(120)
		println(factorial(10000)) //number with 35k digits ;)
	}
}