@echo ####################
@echo      ��������: %a%         
@echo ####################

java -Xmx1000M -Xms256M -jar xsrobot-spring-boot-starter-1.0-SNAPSHOT.jar

@echo �ѹرգ�����5�������
@ping -n 2 127.0.0.1>nul
@set /a a=%a%+1
pause