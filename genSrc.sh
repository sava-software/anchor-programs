#!/usr/bin/env bash

# ./genSrc.sh --log=[INFO|WARN|DEBUG] --tabLength=2 --sourceDirectory="anchor-programs/src/main/java" --basePackageName="software.sava.anchor.programs" --programsCSV="anchor-generator/main_net_programs.csv" --screen=[1|0] --rpc=""
# ./genSrc.sh --log="INFO" --tabLength=2 --sourceDirectory="anchor-programs/src/main/java" --basePackageName="software.sava.anchor.programs"
set -e

readonly projectName="generator"
readonly moduleName="software.sava.anchor_generator"
readonly package="software.sava.anchor.programs"
projectDirectory="$(pwd)/generator"
readonly projectDirectory
readonly mainClass="$package.Entrypoint"

javaArgs=(
  '--enable-preview'
  '-XX:+UseZGC'
  '-Xms256M'
  '-Xmx1024M'
)

screen=0;
targetJavaVersion=22
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

      tjv | targetJavaVersion) targetJavaVersion="$val";;

      bdm | baseDelayMillis) baseDelayMillis="$val";;
      bp | basePackageName) basePackageName="$val";;
      mn | moduleName) outputModuleName="$val";;
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

javaVersion=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | grep -oEi '^[0-9]+')
readonly javaVersion
if [[ "$javaVersion" -ne "$targetJavaVersion" ]]; then
  echo "Invalid Java version $javaVersion must be $targetJavaVersion."
  exit 3
fi

./gradlew --stacktrace "-PmainClassName=$mainClass" clean ":$projectName:jlink"

vcsRef="$(git rev-parse --short HEAD)"
readonly vcsRef
readonly javaExe="$projectDirectory/build/$vcsRef/bin/java"

javaArgs+=(
  "-D$moduleName.baseDelayMillis=$baseDelayMillis"
  "-D$moduleName.basePackageName=$basePackageName"
  "-D$moduleName.outputModuleName=$outputModuleName"
  "-D$moduleName.numThreads=$numThreads"
  "-D$moduleName.programsCSV=$programsCSV"
  "-D$moduleName.rpc=$rpc"
  "-D$moduleName.sourceDirectory=$sourceDirectory"
  "-D$moduleName.tabLength=$tabLength"
  '-m' "$moduleName/$mainClass"
)

if [[ "$screen" == 0 ]]; then
  set -x
  "$javaExe" "${javaArgs[@]}"
else
  set -x
  screen -S "anchor-src-gen" "$javaExe" "${javaArgs[@]}"
fi