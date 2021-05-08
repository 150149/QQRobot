@echo ####################
@echo      重启次数: %a%         
@echo ####################

java -Xmx1000M -Xms256M -jar xsrobot-spring-boot-starter-1.0-SNAPSHOT.jar

@echo 已关闭，将于5秒后重启
@ping -n 2 127.0.0.1>nul
@set /a a=%a%+1
pause