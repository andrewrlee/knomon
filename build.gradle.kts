import org.gradle.jvm.tasks.Jar

plugins {
  kotlin("jvm") version "1.7.10"
  application
}
repositories {
  mavenCentral()
}
dependencies {
  implementation("io.reactivex:rxjava:1.1.2")
  implementation("org.immutables:value:2.1.14")
  implementation("com.beust:jcommander:1.48")
  testImplementation("org.assertj:assertj-core:3.4.1")
  testImplementation("junit:junit:4.12")
  testImplementation("junit:junit:4.12")
  testImplementation("org.mockito:mockito-core:4.6.1")
}


java {
  toolchain.languageVersion.set(JavaLanguageVersion.of(18))
}

tasks {
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
      jvmTarget = "18"
      freeCompilerArgs += "-Xemit-jvm-type-annotations"
    }
  }
}

tasks {
  register("fatJar", Jar::class.java) {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
      attributes("Main-Class" to "uk.co.optimisticpanda.jnomon.Main")
    }
    from(configurations.runtimeClasspath.get()
      .onEach { println("add from dependencies: ${it.name}") }
      .map { if (it.isDirectory) it else zipTree(it) })
    val sourcesMain = sourceSets.main.get()
    sourcesMain.allSource.forEach { println("add from sources: ${it.name}") }
    from(sourcesMain.output)
  }

  "build" {
    dependsOn("fatJar")
  }
}
