package io.github.javenue.jvmlibs.autovalidator.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile

class AutoValidatorPlugin : Plugin<Project> {

  override fun apply(project: Project) {
    project.plugins.apply("io.freefair.aspectj.post-compile-weaving")

    project.repositories.let {
      it.mavenCentral()
      it.maven { maven ->
        maven.url = project.uri("https://maven.pkg.github.com/javenue/jvm-libs")
        maven.credentials.username = project.findProperty("gpr.user").toString()
        maven.credentials.password = project.findProperty("gpr.key").toString()
      }
    }

    project.dependencies.let {
      it.add("implementation", "org.aspectj:aspectjrt:1.9.9.1")
      it.add("aspect", "io.github.javenue.jvm-libs:auto-validator:1.0-SNAPSHOT")
    }

    project.tasks.withType(JavaCompile::class.java).forEach {
      it.options.compilerArgs.add("-parameters")
    }
  }

}
