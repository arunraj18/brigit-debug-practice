#!/usr/bin/env bash
# Usage: ./run.sh                     -> all tests against src/ (your version)
#        ./run.sh getRefreshesRecency -> one test against src/
#        ./run.sh solution            -> all tests against solution/ (reference fix)
# Needs Java 11+ (javac/java). Downloads the JUnit 5 console jar once.
set -e
cd "$(dirname "$0")"
JAR=lib/junit-platform-console-standalone-1.10.2.jar
if [ ! -f "$JAR" ]; then
  mkdir -p lib
  echo "Downloading JUnit 5..."
  curl -sSL -o "$JAR" https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.2/junit-platform-console-standalone-1.10.2.jar
fi
SRC=src
SELECT="--select-class LruCacheTest"
if [ "$1" = "solution" ]; then SRC=solution
elif [ -n "$1" ]; then SELECT="--select-method LruCacheTest#$1"; fi
rm -rf out && mkdir out
javac -d out -cp "$JAR" $SRC/LruCache.java test/LruCacheTest.java
java $DEBUG -jar "$JAR" --class-path out $SELECT --disable-banner --details=tree
