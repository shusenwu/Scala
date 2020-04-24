package com.shusen.blog.utils

import java.time.format.DateTimeFormatter
import java.time.{ Instant, LocalDateTime, ZoneId }

object tools {

  def getCurrentDigitTime = System.currentTimeMillis

  def convertTimeFromDigitToString(time: Long) =
     LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()).
       format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

}
