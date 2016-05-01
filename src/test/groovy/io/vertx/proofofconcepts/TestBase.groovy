package io.vertx.proofofconcepts

import io.vertx.core.Vertx
import org.junit.After
import org.junit.Before

abstract class TestBase {

  protected Vertx vertx

  @Before
  void setup() {
    vertx = Vertx.vertx()
  }

  @After
  void tearDown() {
    vertx.close()
  }

}
