package examples

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite

@RunWith(classOf[JUnitRunner])
class ListExamples extends FunSuite {

  test("collections") {
    val list = List(1)
    val seq = Seq(1)
    val vector = Vector(1)
    val map = Map(("kal", 100), ("ele", 200))
  }

  test("Craete Lists") {
    val immutableList = List(1, 2, 3, 4)
    println(immutableList)

    // Prepend - very fast / efficient
    val fast = 6 :: immutableList
    println(fast)

    // Post Pend - not efficient
    val slow = immutableList ++ List(7)
    println(slow)
  }

  trait InitLists {
    val strings = List("one", "two", "three", "four", "five")
    val numbers = List(1, 2, 3, 4, 5)
    val map = Map(("one", 1), ("two", 2), ("three", 3), ("four", 2), ("five", 5))
  }

  test("working with Lists") {
    new InitLists {
      // Getting
      println("first element: " + strings(0))

      // Map get
      map.get("can't find") match {
        case Some(i) => println("Map get: " + i)
        case None => println("Map get: oh no")
      }
      
      // Filter
      println("filter long: " + strings.filter(string => {
        string.contains("f") // return true/false
      }))

      // Filter short hand
      println("filter short: " + strings.filter(_.contains("f")))

      // Filter then count
      println("filter count: " + strings.filter(_.contains("f")).size)

      // Filter then count
      println("count if: " + strings.count(_.contains("f")))

      // For each
      for (string <- strings)
        print(string + " ")
      println

      // Same
      strings.foreach(string => {
        print(string + ",")
      })
      println

      // Print nice
      println("mkstring: " + strings.mkString("|"))

      // for comprehension
      val comp = for {
        string <- strings
        num <- numbers
        if (string.contains("f"))
      } yield (string + num)
      println("comp: " + comp)

      // take
      println("take: " + strings.take(3))

      // Map - for each element - returns new list
      println("map: " + strings.map("-" + _))

      // fold 10 + 1 + 2 + 3 + 4 + 5 = 25
      println("fold: " + numbers.fold(10)((counter, next) => counter + next))

    }
  }
}