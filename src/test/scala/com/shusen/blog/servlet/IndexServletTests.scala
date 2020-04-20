package com.shusen.blog.servlet

import org.scalatra.test.scalatest._

class IndexServletTests extends ScalatraFunSuite {

  addServlet(classOf[IndexServlet], "/*")

  test("GET / on IndexServlet should return status 200") {
    get("/") {
      status should equal (200)
    }
  }

}
