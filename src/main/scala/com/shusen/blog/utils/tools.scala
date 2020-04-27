package com.shusen.blog.utils

import java.time.format.DateTimeFormatter
import java.time.{ Instant, LocalDateTime, ZoneId }
import scala.util.Random

object tools {

  def getCurrentDigitTime = System.currentTimeMillis

  def convertTimeFromDigitToString(time: Long) =
     LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()).
       format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

  def randon1t4 = {
    val start = 1
    val end = 4
    start + Random.nextInt( (end - start) + 1 )
  }


}
