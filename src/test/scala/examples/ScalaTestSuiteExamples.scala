package examples;

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

@RunWith(classOf[JUnitRunner])
class ScalaTestSuiteExamples extends FunSuite with ShouldMatchers {

  test("description goes here") {
    // example matchers see http://www.scalatest.org/user_guide/matchers_quick_reference
    "a" should startWith("a")
    "b" should be("b")

    List(1, 2) should have length (2)
    List(1, 2) should contain(2)
  }
}

