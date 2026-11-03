#!/bin/sh
# Compile the included source with the packaged JavaFX JARs, without Maven/network.
set -eu
ROOT=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
if [ -n "${JAVA_HOME:-}" ]; then JAVAC="$JAVA_HOME/bin/javac"; JAR="$JAVA_HOME/bin/jar"; else JAVAC=javac; JAR=jar; fi
cd "$ROOT"
mkdir -p build/classes
find src/main/java -name '*.java' -print > build/sources.txt
"$JAVAC" --release 21 --module-path lib --add-modules javafx.controls,javafx.swing -d build/classes @build/sources.txt
cp -R src/main/resources/. build/classes/
"$JAR" --create --file carbon-saathi.jar --main-class in.carbon.ui.Launcher -C build/classes .
printf '%s\n' 'Build complete. Run ./run.sh --demo to explore isolated examples.'
