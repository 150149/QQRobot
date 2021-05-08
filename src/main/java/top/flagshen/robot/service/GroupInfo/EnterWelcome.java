package top.flagshen.robot.service.GroupInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.flagshen.robot.RobotApplication;
import top.flagshen.robot.Utils.AdminGroup;
import top.flagshen.robot.Utils.MessageHandler;
import top.flagshen.xsrobot.entity.request.MessageRequest;
import top.flagshen.xsrobot.entity.response.GroupInfo;
import top.flagshen.xsrobot.entity.response.MsgReponse;
import top.flagshen.xsrobot.template.XsRobotTemplate;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class EnterWelcome implements MessageHandler {

    @Autowired
    private XsRobotTemplate xsRobotTemplate;

    @Override
    public void handleMessage(MessageRequest messageRequest) throws IOException {
        if (!AdminGroup.isAdminGroup(messageRequest.getGroup().getId())) {
            return;
        }

        SimpleDateFormat slf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String msg =
                "[pic,hash=AB7D3532CA9920376738528B97026042]\n" +
                "┏欢迎 @"+ messageRequest.getTrigger().getName() + "(" + messageRequest.getTrigger().getId() +")\n" +
                "┣加入 " + messageRequest.getGroup().getName() + "(" + messageRequest.getGroup().getId() + ")\n" +
                "┣审批人: "+ messageRequest.getOperator().getName() +"\n" +
                "┗进群时间: " + slf.format(Calendar.getInstance().getTime());

        MsgReponse resp = xsRobotTemplate.sendGroupMsg(messageRequest.getBot(), messageRequest.getGroup().getId(), msg, false);

    }

    public EnterWelcome() {
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setType("event");
        messageRequest.setSubtype("MemberJoinedGroup");
        RobotApplication.messageManager.registerMessageHandler(this,messageRequest);
        System.out.println("[信息]进群欢迎模块已启动");
    }
}
