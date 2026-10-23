@echo off
cd /d "%~dp0"

if not exist out mkdir out

echo Compiling Java sources...
javac -encoding UTF-8 -cp "lib/gson-2.10.1.jar" -d out src/*.java
if %ERRORLEVEL% neq 0 (
    echo Compilation failed!
    pause
    exit /b %ERRORLEVEL%
)

echo Launching Travel Planner Application...
java -Dfile.encoding=UTF-8 -cp "out;lib/gson-2.10.1.jar" TravelPlannerApp
