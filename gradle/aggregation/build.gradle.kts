plugins {
  id("software.sava.build.feature.publish-maven-central")
}

dependencies {
  nmcpAggregation(project(":anchor-programs"))
}

tasks.register("publishToGitHubPackages") {
  group = "publishing"
  dependsOn(
    ":anchor-programs:publishMavenJavaPublicationToSavaGithubPackagesRepository"
  )
}
