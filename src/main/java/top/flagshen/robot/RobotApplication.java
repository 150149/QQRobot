package top.flagshen.robot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.flagshen.robot.Utils.MessageManager;

@SpringBootApplication
@MapperScan("top.flagshen.robot.mapper")
public class RobotApplication {

    public static MessageManager messageManager = new MessageManager();

    public static void main(String[] args) {
        SpringApplication.run(RobotApplication.class, args);
    }
}
