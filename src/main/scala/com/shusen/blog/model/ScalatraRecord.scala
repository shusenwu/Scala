package com.shusen.blog.model

import org.squeryl.{PersistenceStatus, KeyedEntity}

/**
  * ScalatraRecord - contains defaults we can easily modify for all records.
  */
trait ScalatraRecord extends KeyedEntity[Long] with PersistenceStatus {

}