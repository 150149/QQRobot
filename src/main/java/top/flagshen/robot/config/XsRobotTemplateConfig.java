package top.flagshen.robot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.flagshen.xsrobot.prop.HttpApiProperty;
import top.flagshen.xsrobot.template.XsRobotTemplate;

@Configuration
public class XsRobotTemplateConfig {
    @Value("${xsrobot.httpapi.host}")
    private String host;
    @Value("${xsrobot.httpapi.port}")
    private int port;
    @Value("${xsrobot.httpapi.secret}")
    private String secret;

    @Bean
    public XsRobotTemplate createRobotTemplate() {
        HttpApiProperty prop = new HttpApiProperty(host,port,secret);
        return new XsRobotTemplate(prop);
    }
}
