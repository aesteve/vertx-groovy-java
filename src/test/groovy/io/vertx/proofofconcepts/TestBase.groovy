package io.vertx.proofofconcepts

import io.vertx.core.Vertx
import io.vertx.ext.unit.junit.VertxUnitRunner
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(VertxUnitRunner.class)
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
