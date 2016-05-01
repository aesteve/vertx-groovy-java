package io.vertx.proofofconcepts

import io.vertx.core.http.HttpClientOptions
import io.vertx.core.http.HttpServerOptions
import io.vertx.core.json.JsonObject
import io.vertx.ext.unit.junit.VertxUnitRunner
import org.codehaus.groovy.runtime.DefaultGroovyMethods
import org.junit.Test
import org.junit.runner.RunWith

import static junit.framework.Assert.assertEquals

@RunWith(VertxUnitRunner.class)
public class TestMaps {

  /**
   * works ootb
   */
  @Test
  void testMapToClientOptions() {
    HttpClientOptions options = [defaultHost: 'localhost'] as HttpClientOptions
    assertEquals 'localhost', options.defaultHost
  }

  /**
   * This test would fail with the following exception :
   * No signature of method: io.vertx.core.http.HttpClientOptions.get() is applicable for argument types: (java.lang.String)
   * Note : The metaClass implementation could be a pattern for every @DataObject
   */
  @Test
  void testClientOptionsToMap() {
    HttpClientOptions.metaClass.get { String key ->
      delegate."${key}" // relies on getter though, be careful
    }
    HttpClientOptions options = new HttpClientOptions().setDefaultHost 'localhost'
    Map map = options as Map
    assertEquals 'localhost', map['defaultHost']
  }

  /**
   * works ootb
   */
  @Test
  void testMapToServerOptions() {
    HttpServerOptions options = [host: 'localhost'] as HttpServerOptions
    assertEquals 'localhost', options.host
  }

  /**
   * This test would fail with the following exception :
   * No signature of method: io.vertx.core.http.HttpServerOptions.get() is applicable for argument types: (java.lang.String)
   * Note : The metaClass implementation could be a pattern for every @DataObject
   */
  @Test
  void testServerOptionsToMap() {
    HttpServerOptions.metaClass.get { String key ->
      delegate."${key}" // relies on getter though, careful
    }
    HttpServerOptions options = new HttpServerOptions().setHost 'localhost'
    Map map = options as Map
    assertEquals 'localhost', map['host']
  }

  /**
   * works ootb
   */
  @Test
  void testMapToJsonObject() {
    JsonObject json = [test: 'value'] as JsonObject
    assertEquals 'value', json.getString('test')
  }

  /**
   * This test would fail with the following exception :
   * "No signature of method: io.vertx.core.json.JsonObject.get() is applicable for argument types: (java.lang.String)"
   * Note that from JsonObject implementation, adding a new method :
   *     public Object get(String key) { return map.get(key); }
   * would be enough
   * I had to cheat a little with meta-programming
   */
  @Test
  void testJsonObjectToMap() {
    JsonObject.metaClass.get = { String key ->
      delegate.@map.get key
    }
    JsonObject json = new JsonObject().put 'test', 'value'
    Map<String, Object> map = json as Map<String, Object>
    assertEquals 'value', map['test'] // fail if no meta-programming
  }

  /**
   * This test would fail with the following exception :
   * "No signature of method: io.vertx.core.json.JsonObject.get() is applicable for argument types: (java.lang.String)"
   * This is a second possible implementation requiring a JsonObjectExtension
   */
  @Test
  void testJsonObjectToMap2() {
    JsonObject.metaClass.asType = { Class<?> type ->
      if (Map.class.isAssignableFrom(type)) {
        return delegate.@map
      }
      DefaultGroovyMethods.asType delegate, type // standard behaviour
    }
    JsonObject json = new JsonObject().put 'test', 'value'
    Map<String, Object> map = json as Map<String, Object>
    assertEquals 'value', map['test'] // fail if no meta-programming
  }

}