#!/usr/bin/env bash
set -e

cd "$(dirname "$0")"
mkdir -p out

echo "Compiling Java sources..."
javac -encoding UTF-8 -cp "lib/gson-2.10.1.jar" -d out src/*.java

echo "Launching Travel Planner Application..."
java -Dfile.encoding=UTF-8 -cp "out:lib/gson-2.10.1.jar" TravelPlannerApp
