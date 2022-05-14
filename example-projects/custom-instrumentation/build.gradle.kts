plugins {
  id("com.ryandens.javaaagent.example.java-library-conventions")
  id("io.opentelemetry.instrumentation.muzzle-check") version "1.13.1-alpha"
  id("io.opentelemetry.instrumentation.muzzle-generation") version "1.13.1-alpha"
}

configurations {
  val bootstrap by creating {
    isCanBeResolved = false
    isCanBeConsumed = false
  }

  named("compileOnly") {
    extendsFrom(bootstrap)
  }
  named("muzzleBootstrap") {
    extendsFrom(bootstrap)
  }
}

dependencies {
  compileOnly("com.google.auto.service:auto-service-annotations:1.0.1")
  annotationProcessor("com.google.auto.service:auto-service:1.0.1")
  val otelInstrumentationVersion = "1.13.1-alpha"
  enforcedPlatform("io.opentelemetry.instrumentation:opentelemetry-instrumentation-bom-alpha:$otelInstrumentationVersion")
  muzzleBootstrap("io.opentelemetry.instrumentation:opentelemetry-instrumentation-api")
  muzzleBootstrap("io.opentelemetry.instrumentation:opentelemetry-instrumentation-api-semconv")
  muzzleBootstrap("io.opentelemetry.instrumentation:opentelemetry-instrumentation-api-annotation-support")
  muzzleBootstrap( "io.opentelemetry.instrumentation:opentelemetry-instrumentation-appender-api-internal")
  muzzleTooling("io.opentelemetry.javaagent:opentelemetry-javaagent-extension-api")
  muzzleTooling("io.opentelemetry.javaagent:opentelemetry-javaagent-tooling")
  // for some reason, when pulling this version value from the platform bom, a byte buddy task can't be created
  compileOnly("io.opentelemetry.javaagent:opentelemetry-javaagent-extension-api:$otelInstrumentationVersion")
  compileOnly("io.opentelemetry.javaagent:opentelemetry-javaagent-tooling:$otelInstrumentationVersion")
  compileOnly("io.opentelemetry.instrumentation:opentelemetry-instrumentation-api:$otelInstrumentationVersion")


  /*
    Dependencies added to this configuration will be found by the muzzle gradle plugin during code
    generation phase. These classes become part of the code that plugin inspects and traverses during
    references collection phase.
   */
  add("codegen", "io.opentelemetry.javaagent:opentelemetry-javaagent-tooling:$otelInstrumentationVersion")
}