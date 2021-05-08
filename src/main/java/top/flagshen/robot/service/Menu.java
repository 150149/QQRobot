package top.flagshen.robot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.flagshen.robot.RobotApplication;
import top.flagshen.robot.Utils.MessageHandler;
import top.flagshen.xsrobot.entity.request.Message;
import top.flagshen.xsrobot.entity.request.MessageRequest;
import top.flagshen.xsrobot.template.XsRobotTemplate;

import java.io.IOException;

/**
 * @author 150149
 */
@Service
public class Menu implements MessageHandler {

    @Autowired
    private XsRobotTemplate xsRobotTemplate;

    public Menu() {
        MessageRequest messageRequest = new MessageRequest();
        Message message = new Message();
        message.setText("菜单");
        messageRequest.setType("message");
        messageRequest.setSubtype("group");
        messageRequest.setMessage(message);
        RobotApplication.messageManager.registerMessageHandler(this,messageRequest);
        System.out.println("[信息]Q绑查询模块已启动");
    }

    @Override
    public void handleMessage(MessageRequest messageRequest) throws IOException {
        
    }
}
