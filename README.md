I made every test pass by adding meta-programming where needed.

Note that in comments I've added notices to what's really missing to have the Groovy equivalent.
Sometimes it's just a method the Java class should contain for the test to be working.
Sometimes it's the GroovyExtensionMethod implementation we could add.

In both cases, it's hard-coded in the test as a meta-class implementation.