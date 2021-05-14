package top.flagshen.robot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.flagshen.robot.RobotApplication;
import top.flagshen.robot.Utils.AdminGroup;
import top.flagshen.robot.Utils.MessageHandler;
import top.flagshen.xsrobot.entity.request.Message;
import top.flagshen.xsrobot.entity.request.MessageRequest;
import top.flagshen.xsrobot.entity.response.MsgReponse;
import top.flagshen.xsrobot.template.XsRobotTemplate;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
        System.out.println("[信息]菜单显示模块已启动");
    }

    @Override
    public void handleMessage(MessageRequest messageRequest) throws IOException {
        if (!AdminGroup.isAdminGroup(messageRequest.getGroup().getId())) {
            return;
        }

        StringBuilder sb = new StringBuilder("☆------[ 机器人菜单 ]------☆\n");
        sb.append("\n");
        int i=1;

        ConcurrentHashMap<MessageHandler,MessageRequest> handlers = RobotApplication.messageManager.getHandlers();
        for (Map.Entry<MessageHandler,MessageRequest> entry: handlers.entrySet()) {
            if ( entry.getValue().getMessage()!=null) {
                if(!"菜单".equals(entry.getValue().getMessage().getText().toLowerCase())) {
                    sb.append(String.valueOf(i)).append(". ").append(entry.getValue().getMessage().getText()).append("\n");
                    i++;
                }
            }
        }

        sb.append("\n☆--------------------------☆");
        MsgReponse resp = xsRobotTemplate.sendGroupMsg(messageRequest.getBot(), messageRequest.getGroup().getId(), sb.toString(), false);
        System.out.println("[信息]菜单发送完成");
    }
}
