package com.shusen.blog.db

import org.squeryl.Session
import org.squeryl.SessionFactory
import org.scalatra.ScalatraBase
import DatabaseSessionSupport.key

object DatabaseSessionSupport {
  val key = {
    val n = getClass.getName
    if (n.endsWith("$")) n.dropRight(1) else n
  }
}

trait DatabaseSessionSupport { this: ScalatraBase =>

  def dbSession = request.get(key).orNull.asInstanceOf[Session]

  // before and after filters which open a new database session on each
  // request and close it after the request is finished.
  before() {
    request(key) = SessionFactory.newSession
    dbSession.bindToCurrentThread
  }

  after() {
    dbSession.close
    dbSession.unbindFromCurrentThread
  }

}