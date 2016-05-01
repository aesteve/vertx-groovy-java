package io.vertx.proofofconcepts

import groovy.transform.CompileStatic
import io.vertx.core.http.HttpClientOptions
import io.vertx.core.http.HttpServerOptions
import io.vertx.ext.unit.Async
import io.vertx.ext.unit.TestContext
import org.junit.Test

@CompileStatic
class TestClosures extends TestBase {

  /**
   * Works ootb
   */
  @Test
  public void testClosureAsHandler(TestContext context) {
    Async async = context.async()
    vertx.createHttpServer([port: 9000] as HttpServerOptions)
        .requestHandler({ async.complete() }) // Closure as Handler (even with @CompileStatic)
        .listen({
          context.assertTrue it.succeeded()
          vertx.createHttpClient([defaultPort: 9000] as HttpClientOptions).getNow('/') {}
        })
  }

}
