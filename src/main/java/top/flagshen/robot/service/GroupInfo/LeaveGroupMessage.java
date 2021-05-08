package top.flagshen.robot.service.GroupInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.flagshen.robot.RobotApplication;
import top.flagshen.robot.Utils.AdminGroup;
import top.flagshen.robot.Utils.MessageHandler;
import top.flagshen.xsrobot.entity.request.MessageRequest;
import top.flagshen.xsrobot.entity.response.MsgReponse;
import top.flagshen.xsrobot.template.XsRobotTemplate;

import java.io.IOException;

/**
 * @author 150149
 */
@Service
public class LeaveGroupMessage implements MessageHandler {

    @Autowired
    private XsRobotTemplate xsRobotTemplate;

    public LeaveGroupMessage() {
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setType("event");
        messageRequest.setSubtype("MemberQuit");
        RobotApplication.messageManager.registerMessageHandler(this,messageRequest);
        System.out.println("[信息]退群提示模块已启动");
    }

    @Override
    public void handleMessage(MessageRequest messageRequest) throws IOException {
        if (!AdminGroup.isAdminGroup(messageRequest.getGroup().getId())) {
            return;
        }

        String msg = messageRequest.getTrigger().getName() + "("  + messageRequest.getTrigger().getId() + ") 退出了群";
        MsgReponse resp = xsRobotTemplate.sendGroupMsg(messageRequest.getBot(), messageRequest.getGroup().getId(), msg, false);

    }
}
