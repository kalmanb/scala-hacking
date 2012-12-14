package examples

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSpec
import org.scalatest.OneInstancePerTest
import org.scalatest.matchers.ShouldMatchers
import org.mockito.Mockito._

@RunWith(classOf[JUnitRunner])
class ScalaTestSpecExamples extends FunSpec with OneInstancePerTest with ShouldMatchers {

	class TestMe {
		val Default = "kal"
		def getString(input: String = Default) = input
	}

	// Setup some mocks
	val testMeMock = mock(classOf[TestMe])

	when(testMeMock.getString()).thenReturn("mocked")

	val testMe = new TestMe
	describe("testme should return") {
		describe("when given no input") {
			it("should return default") {
				testMe.getString() should be("kal")
			}
			describe("when given input") {
				it("should return the same value") {
					testMe.getString("expected") should be("expected")
				}
				it("should return special chars") {
					testMe.getString("!@#$%^&*()") should be("!@#$%^&*()")
				}
			}
		}
	}
}
