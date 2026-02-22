@ECHO OFF
setlocal
cd /d "%~dp0"

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
if exist ACTUAL.TXT del ACTUAL.TXT
if exist ACTUAL_FILTERED.TXT del ACTUAL_FILTERED.TXT

REM delete saved data from previous run
if exist data\vigil.txt del data\vigil.txt

REM compile the code into the bin folder
javac -encoding UTF-8 -Xlint:none -d ..\bin ^
  ..\src\main\java\vigil\*.java ^
  ..\src\main\java\vigil\exception\*.java ^
  ..\src\main\java\vigil\task\*.java ^
  ..\src\main\java\vigil\storage\*.java ^
  ..\src\main\java\vigil\ui\*.java

IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ..\bin vigil.Vigil < input.txt > ACTUAL.TXT

REM filter out ASCII logo: keep lines from first divider onwards
powershell -NoProfile -Command ^
  "$lines = Get-Content 'ACTUAL.TXT';" ^
  "$idx = $lines.IndexOf('_______________________________________________________________________________');" ^
  "if ($idx -lt 0) { $lines | Set-Content 'ACTUAL_FILTERED.TXT' } else { $lines[$idx..($lines.Length-1)] | Set-Content 'ACTUAL_FILTERED.TXT' }"

REM compare the output to the expected output
FC ACTUAL_FILTERED.TXT EXPECTED.TXT
