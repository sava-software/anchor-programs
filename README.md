![](https://github.com/sava-software/sava/blob/003cf88b3cd2a05279027557f23f7698662d2999/assets/images/solana_java_cup.svg)

# Anchor Programs [![Build](https://github.com/sava-software/anchor-programs/actions/workflows/gradle.yml/badge.svg)](https://github.com/sava-software/anchor-programs/actions/workflows/gradle.yml) [![Release](https://github.com/sava-software/anchor-programs/actions/workflows/release.yml/badge.svg)](https://github.com/sava-software/anchor-programs/actions/workflows/release.yml)

Generated programs can be found in
the [root source package directory](programs/src/main/java/software/sava/anchor/programs). For each project generated
code is under the `anchor` package, and manually written code is directly under the project package.

Code is generated using [sava-software/anchor-src-gen](https://github.com/sava-software/anchor-src-gen), see that
project for more context on which features are provided.

## Documentation

User documentation lives at [sava.software](https://sava.software/).

* [Anchor Programs](https://sava.software/libraries/anchor-programs)

## Contribution

Unit tests are needed and welcomed. Otherwise, please open
a [discussion](https://github.com/sava-software/sava/discussions), issue, or send an email before working on a pull
request.

## Build

[Generate a classic token](https://github.com/settings/tokens) with the `read:packages` scope needed to access
dependencies hosted on GitHub Package Repository.

Create a `gradle.properties` file in the sava project directory root or under `$HOME/.gradle/`.

### gradle.properties

```properties
gpr.user=GITHUB_USERNAME
gpr.token=GITHUB_TOKEN
```

```shell
./gradlew check
```
