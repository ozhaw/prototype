cd ..
mvn -pl '!:swagger,!:hystrix-configuration' clean package
PAUSE

call:run_app eureka-service 8010
call:run_app hystrix-dashboard 8099
goto:eof

:run_app
@ECHO Starting %~1 on port %~2
start /min java -Xmx512m -Xss256k -jar -Dserver.port=%~2 .\%~1\target\%~1-1.0-SNAPSHOT.jar
PAUSE
goto:eof