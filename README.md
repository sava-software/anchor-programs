# Anchor Programs [![Gradle Check](https://github.com/sava-software/anchor-programs/actions/workflows/build.yml/badge.svg)](https://github.com/sava-software/anchor-programs/actions/workflows/build.yml) [![Publish Release](https://github.com/sava-software/anchor-programs/actions/workflows/publish.yml/badge.svg)](https://github.com/sava-software/anchor-programs/actions/workflows/publish.yml) 

Generated programs can be found in
the [root source package directory](anchor-programs/src/main/java/software/sava/anchor-programs/programs). For each project generated
code is under the `anchor` package, and manually written code is directly under the project package.

Code is generated using [sava-software/anchor-src-gen](https://github.com/sava-software/anchor-src-gen), see that
project for more context on which features are provided.

## Documentation

User documentation lives at [sava.software](https://sava.software/).

* [Dependency Configuration](https://sava.software/quickstart)
* [Anchor Programs](https://sava.software/libraries/anchor-programs)

## Contribution

Unit tests are needed and welcomed. Otherwise, please open a discussion, issue, or send an email before working on a
pull request.

## Build

[Generate a classic token](https://github.com/settings/tokens) with the `read:packages` scope needed to access
dependencies hosted on GitHub Package Repository.

Add the following properties to `$HOME/.gradle/gradle.properties`.

```gradle.properties
savaGithubPackagesUsername=GITHUB_USERNAME
savaGithubPackagesPassword=GITHUB_TOKEN
```

```shell
./gradlew check
```
