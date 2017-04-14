@echo off

rem Find archive.jar, or we can't continue

set RUNJAR=.\Out\archive.jar
if exist "%RUNJAR%" goto FOUND_RUN_JAR
echo Could not locate %RUNJAR%. Please check that you are in the
echo bin directory when running this script.
goto END

:FOUND_RUN_JAR

if not "%JAVA_HOME%" == "" goto ADD_TOOLS

echo JAVA_HOME is not set.
set JAVA_HOME=C:\jdk1.5.0_14

:ADD_TOOLS
echo   JAVA_HOME: %JAVA_HOME%

set JAVA=%JAVA_HOME%\bin\java

if not exist "%JAVA_HOME%\lib\tools.jar" goto END


set ARCHIVE_PATCH_CLASSPATH=%RUNJAR%;.\conf;.\lib\JDBCDrivers\Merlia.jar;.\lib\JDBCDrivers\ojdbc6.jar;.\lib\JakartaCommons\commons-beanutils.jar;.\lib\JakartaCommons\commons-collections.jar;.\lib\JakartaCommons\commons-dbcp.jar;.\lib\JakartaCommons\commons-dbutils.jar;.\lib\JakartaCommons\commons-digester.jar;.\lib\JakartaCommons\commons-fileupload.jar;.\lib\JakartaCommons\commons-httpclient.jar;.\lib\JakartaCommons\commons-io.jar;.\lib\JakartaCommons\commons-lang.jar;.\lib\JakartaCommons\commons-logging.jar;.\lib\JakartaCommons\commons-pool.jar;.\lib\JakartaCommons\commons-validator.jar;
set ARCHIVE_HOME=.

set ARCHIVE_OPTIONS=-Darchive.home=%ARCHIVE_HOME%

rem Normal Phrase java options
set JAVA_OPTS=-Xms512m -Xmx1024m %ARCHIVE_OPTIONS%

echo ===============================================================================
echo .
echo   Nana Archive Tool -- v1.0
echo .
echo   JAVA: %JAVA%
echo .
echo   JAVA_OPTS: %JAVA_OPTS%
echo .
echo   CLASSPATH: %ARCHIVE_PATCH_CLASSPATH%
echo .
echo ===============================================================================
echo .

rem 下面%JAVA%變數前後的"不可被拿掉, otherwise會讓有些電腦執行的時候, 產生問題
"%JAVA%" %JAVA_OPTS% -classpath %ARCHIVE_PATCH_CLASSPATH% archive.backupOrganization DBSetting.properties TableName.TXT

:END
rem if "%NOPAUSE%" == "" pause