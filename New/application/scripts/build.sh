#!/usr/bin/env sh
set -eu

ROOT=$(CDPATH= cd -- "$(dirname -- "$0")/.." && pwd)
cd "$ROOT"
mkdir -p build/classes
find src/main/java -name '*.java' -print > build/sources.txt

if command -v mvn >/dev/null 2>&1; then
  mvn clean verify
  exit 0
fi

echo "Maven is required for the normal build. Use: mvn clean verify" >&2
exit 2
