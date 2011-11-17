@ECHO OFF

    REM …Ë÷√CLASSPATH
    SET PRJPATH=F:\fmdep\out\production\main

    SET classpath=%JAVA_HOME%\jre\lib\alt-rt.jar;%JAVA_HOME%\jre\lib\charsets.jar;%JAVA_HOME%\jre\lib\rt.jar

    SET classpath=%CLASSPATH%;%PRJPATH%

    SET LIBPATH=F:\fmdep\lib

    FOR %%i IN ("%LIBPATH%\*.jar") DO SET CLASSPATH=!CLASSPATH!;%%~fi