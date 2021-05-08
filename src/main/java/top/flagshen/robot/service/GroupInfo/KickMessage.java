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
public class KickMessage implements MessageHandler {

    @Autowired
    private XsRobotTemplate xsRobotTemplate;

    public KickMessage() {
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setType("event");
        messageRequest.setSubtype("MemberBeKicked");
        RobotApplication.messageManager.registerMessageHandler(this,messageRequest);
        System.out.println("[信息]踢人提示模块已启动");
    }

    @Override
    public void handleMessage(MessageRequest messageRequest) throws IOException {
        if (!AdminGroup.isAdminGroup(messageRequest.getGroup().getId())) {
            return;
        }

        String msg = messageRequest.getTrigger().getName() + "(" + messageRequest.getTrigger().getId() +") " +
                "被管理员 ("+ messageRequest.getOperator().getName() +") 踢掉了";

        MsgReponse resp = xsRobotTemplate.sendGroupMsg(messageRequest.getBot(), messageRequest.getGroup().getId(), msg, false);
    }
}
