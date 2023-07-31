package io.github.javenue.jvmlibs.autovalidator.plugin;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class AutoValidatorPluginTest {

  @SuppressWarnings("ConstantConditions")
  @Test
  void whenPluginAppliedThenCompilerArgsAddedToAllJavaCompileTasks(@TempDir File projectDir) {
    // Given
    var targetProject = ProjectBuilder.builder()
        .withProjectDir(projectDir)
        .build();
    targetProject.getPlugins().apply(JavaPlugin.class);

    // When
    targetProject.getPlugins().apply(AutoValidatorPlugin.class);

    // Then
    var compileJavaTask = (JavaCompile) targetProject.getTasks().findByName("compileJava");
    assertThat(compileJavaTask.getOptions().getCompilerArgs()).contains("-parameters");
    var compileTestJavaTask = (JavaCompile) targetProject.getTasks().findByName("compileTestJava");
    assertThat(compileTestJavaTask.getOptions().getCompilerArgs()).contains("-parameters");
  }

}
