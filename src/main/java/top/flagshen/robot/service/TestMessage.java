package top.flagshen.robot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.flagshen.robot.RobotApplication;
import top.flagshen.robot.Utils.AdminGroup;
import top.flagshen.robot.Utils.HttpUtil;
import top.flagshen.robot.Utils.MessageHandler;
import top.flagshen.xsrobot.entity.request.Message;
import top.flagshen.xsrobot.entity.request.MessageRequest;
import top.flagshen.xsrobot.entity.response.GroupInfo;
import top.flagshen.xsrobot.entity.response.GroupMemberList;
import top.flagshen.xsrobot.entity.response.GroupMemberListInfo;
import top.flagshen.xsrobot.entity.response.MsgReponse;
import top.flagshen.xsrobot.template.XsRobotTemplate;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author 150149
 */
@Service
public class TestMessage implements MessageHandler {

    @Autowired
    private XsRobotTemplate xsRobotTemplate;

    public TestMessage() {
        MessageRequest messageRequest = new MessageRequest();
        Message message = new Message();
        message.setText("测试");
        messageRequest.setType("message");
        messageRequest.setSubtype("group");
        messageRequest.setMessage(message);
        RobotApplication.messageManager.registerMessageHandler(this,messageRequest);
        System.out.println("[信息]测试消息模块已启动");
    }

    @Override
    public void handleMessage(MessageRequest messageRequest) throws IOException {
        if (!AdminGroup.isAdminGroup(messageRequest.getGroup().getId())) {
            return;
        }

        SimpleDateFormat slf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String msg;
        msg = "┏测试消息发送者: [@" + messageRequest.getSender().getId() + "] (" + messageRequest.getSender().getId() +")\n" +
                "┣来源群: " + messageRequest.getGroup().getName() + "(" + messageRequest.getGroup().getId() + ")\n" +
                "┣机器人: " + messageRequest.getBot() + "\n" +
                "┣收到的信息: " + messageRequest.getMessage().getText() + "\n" +
                "┗发送时间: " + slf.format(Calendar.getInstance().getTime());

        MsgReponse resp = xsRobotTemplate.sendGroupMsg(messageRequest.getBot(), messageRequest.getGroup().getId(), msg, false);

        System.out.println("发送完成");
    }
}
