![](https://github.com/sava-software/sava/blob/003cf88b3cd2a05279027557f23f7698662d2999/assets/images/solana_java_cup.svg)

# Anchor Programs [![Build](https://github.com/sava-software/anchor-programs/actions/workflows/gradle.yml/badge.svg)](https://github.com/sava-software/anchor-programs/actions/workflows/gradle.yml) [![Release](https://github.com/sava-software/anchor-programs/actions/workflows/release.yml/badge.svg)](https://github.com/sava-software/anchor-programs/actions/workflows/release.yml)

## Generate Source

Replace the values below to fit your needs.

```bash
./genSrc.sh \
 --log=[INFO|WARN|DEBUG] \
 --tabLength=2 \
 --sourceDirectory="programs/src/main/java" \
 --moduleName="software.sava.anchor_programs" \
 --basePackageName="software.sava.anchor.programs" \
 --rpc="https://rpc.com" \
 --programsCSV="generator/main_net_programs.csv" \
 --baseDelayMillis=200 \
 --numThreads=5 \
 --screen=[0|1]
```
