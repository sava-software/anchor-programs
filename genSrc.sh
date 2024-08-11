#!/usr/bin/env bash

# ./genSrc.sh --log=[INFO|WARN|DEBUG] --tabLength=2 --sourceDirectory="anchor-programs/src/main/java" --basePackageName="software.sava.anchor.programs" --programsCSV="anchor-generator/main_net_programs.csv" --screen=[1|0] --rpc=""
# ./genSrc.sh --log="INFO" --tabLength=2 --sourceDirectory="anchor-programs/src/main/java" --basePackageName="software.sava.anchor.programs"
set -e

readonly JAVA_VERSION="22"

readonly projectName="generator"
readonly moduleName="software.sava.anchor_generator"
readonly package="software.sava.anchor.programs"
projectDirectory="$(pwd)/generator"
readonly projectDirectory
readonly mainClass="$package.Entrypoint"

javaArgs=(
  '-XX:+UseZGC'
  '--enable-preview'
)

screen=0;
logLevel="INFO";
tabLength=2;
sourceDirectory="src/main/java";
outputModuleName="";
basePackageName="$package";
rpc="";
programsCSV="generator/main_net_programs.csv";
numThreads="5";
baseDelayMillis=200;

for arg in "$@"
do
  if [[ "$arg" =~ ^--.* ]]; then
    key="${arg%%=*}"
    key="${key##*--}"
    val="${arg#*=}"

    case "$key" in
      l | log)
          case "$val" in
            INFO|WARN|DEBUG) logLevel="$val";;
            *)
              printf "'%slog=[INFO|WARN|DEBUG]' not '%s'.\n" "--" "$arg";
              exit 2;
            ;;
          esac
          javaArgs+=("-D$moduleName.logLevel=$logLevel")
        ;;

      screen)
        case "$val" in
          1|*screen) screen=1 ;;
          0) screen=0 ;;
          *)
            printf "'%sscreen=[0|1]' or '%sscreen' not '%s'.\n" "--" "--" "$arg";
            exit 2;
          ;;
        esac
        ;;

      bdm | baseDelayMillis) baseDelayMillis="$val";;
      bp | basePackageName) basePackageName="$val";;
      mn | outputModuleName) outputModuleName="$val";;
      nt | numThreads) numThreads="$val";;
      pcsv | programsCSVs) programsCSV="$val";;
      rpc) rpc="$val";;
      sd | sourceDirectory) sourceDirectory="$val";;
      tl | tabLength) tabLength="$val";;

      *)
          printf "Unsupported flag '%s' [key=%s] [val=%s].\n" "$arg" "$key" "$val";
          exit 1;
        ;;
    esac
  else
    printf "Unhandled argument '%s', all flags must begin with '%s'.\n" "$arg" "--";
    exit 1;
  fi
done

javaArgs+=("-D$moduleName.baseDelayMillis=$baseDelayMillis")
javaArgs+=("-D$moduleName.basePackageName=$basePackageName")
javaArgs+=("-D$moduleName.outputModuleName=$outputModuleName")
javaArgs+=("-D$moduleName.numThreads=$numThreads")
javaArgs+=("-D$moduleName.programsCSV=$programsCSV")
javaArgs+=("-D$moduleName.rpc=$rpc")
javaArgs+=("-D$moduleName.sourceDirectory=$sourceDirectory")
javaArgs+=("-D$moduleName.tabLength=$tabLength")

javaVersion=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | grep -oEi '^[0-9]+')
readonly javaVersion
if [[ "$javaVersion" -ne "$JAVA_VERSION" ]]; then
  echo "Invalid Java version $javaVersion must be $JAVA_VERSION."
  exit 3
fi

osName="$(uname)"
readonly osName
if [[ "$osName" == "Linux" ]]; then
  memTotalKB=$(grep MemTotal /proc/meminfo | awk '{print $2}')
  readonly memTotalKB
  if (( memTotalKB < 2000000 )); then
    javaArgs+=(
      '-Xms128M'
      '-Xmx384M'
    )
    gradleArgs+=('--no-daemon')
    ./gradlew --no-daemon --stop
  else
    javaArgs+=(
      '-Xms256M'
      '-Xmx1024M'
    )
  fi
else
  gradleArgs+=('clean')
fi

./gradlew --no-daemon "-PmainClassName=$mainClass" "${gradleArgs[@]}" ":$projectName:jlink"

javaArgs+=('-m' "$moduleName/$mainClass")

vcsRef="$(git rev-parse --short HEAD)"
readonly vcsRef
readonly javaExe="$projectDirectory/build/$vcsRef/bin/java"

if [[ "$screen" == 0 ]]; then
  set -x
  "$javaExe" "${javaArgs[@]}"
else
  set -x
  screen -S "anchor-src-gen" "$javaExe" "${javaArgs[@]}"
fi