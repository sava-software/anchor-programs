pluginManagement {
  repositories {
    gradlePluginPortal()
    maven {
      name = "savaGithubPackages"
      url = uri("https://maven.pkg.github.com/sava-software/sava-build")
      credentials(PasswordCredentials::class)
    }
  }
}

plugins {
  id("software.sava.build")  version "0.1.27"
}

rootProject.name = "anchor-programs"

javaModules {
  directory(".") {
    group = "software.sava"
    plugin("software.sava.build.java-module")
  }
}
