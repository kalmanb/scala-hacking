package examples

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import scala.beans.BeanProperty
import scala.tools.util.Javap

@RunWith(classOf[JUnitRunner])
class ScalaLangExamples extends FunSuite {

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
      case e: Throwable => e.printStackTrace
    }
    println(badTry)

    val goodTry = try {
      "..."
    } catch {
      case e: Throwable => e.printStackTrace
    }
    println(goodTry)
  }

  // just an example, this would be a bad idea
  // see also scala.collections.JavaConversions._
  test("implicit conversions") {
    implicit def javaIntToScalaString(in: Integer): String = in.toString()
    val javaVersion = new Integer(123)

    def onlyTakesInts(in: String) {}
    onlyTakesInts(javaVersion) // automagically converted
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

  test("static classes - objects") {
    object SomeUtil {
      def add(i: Int, j: Int) = i + j
    }
    import SomeUtil._
    assert(add(1, 2) === 3)
  }

  test("companion objects") {
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
}