![](https://github.com/sava-software/sava/blob/003cf88b3cd2a05279027557f23f7698662d2999/assets/images/solana_java_cup.svg)

# Anchor Programs [![Build](https://github.com/sava-software/anchor-programs/actions/workflows/gradle.yml/badge.svg)](https://github.com/sava-software/anchor-programs/actions/workflows/gradle.yml) [![Release](https://github.com/sava-software/anchor-programs/actions/workflows/release.yml/badge.svg)](https://github.com/sava-software/anchor-programs/actions/workflows/release.yml)

Generated programs can be found in
the [root source package directory](programs/src/main/java/software/sava/anchor/programs). For each project generated
code is under the `anchor` package, and manually written code is directly under the project package.

Code is generated using [sava-software/anchor-src-gen](https://github.com/sava-software/anchor-src-gen), see that
project for more context on which features are provided.

## Requirements

- The latest generally available JDK. This project will continue to move to the latest and will not maintain
  versions released against previous JDK's.

## [Dependencies](programs/src/main/java/module-info.java)

- [JSON Iterator](https://github.com/comodal/json-iterator?tab=readme-ov-file#json-iterator)
- [sava-core](https://github.com/sava-software/sava)
- [sava-rpc](https://github.com/sava-software/sava)
- [solana-programs](https://github.com/sava-software/solana-programs)
- [anchor-src-gen](https://github.com/sava-software/anchor-src-gen)

## Contribution

Unit tests are needed and welcomed. Otherwise, please open an issue or send an email before working on a pull request.

## Warning

Young project, under active development, breaking changes are to be expected.