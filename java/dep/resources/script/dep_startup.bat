@ECHO OFF

    REM …Ë÷√CLASSPATH
    SET PRJPATH=F:\fm-dep\out\production\main

    SET classpath=%JAVA_HOME%\jre\lib\alt-rt.jar;%JAVA_HOME%\jre\lib\charsets.jar;%JAVA_HOME%\jre\lib\rt.jar

    SET classpath=%CLASSPATH%;%PRJPATH%

    SET LIBPATH=F:\fm-dep\lib

    FOR %%i IN ("%LIBPATH%\*.jar") DO SET CLASSPATH=!CLASSPATH!;%%~fi

    @ECHO OFF

SETLOCAL  EnableDelayedExpansion

	javaw dep.DEPLauncher

ENDLOCAL