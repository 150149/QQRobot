package top.flagshen.robot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.flagshen.robot.RobotApplication;
import top.flagshen.robot.Utils.AdminGroup;
import top.flagshen.robot.Utils.HttpUtil;
import top.flagshen.robot.Utils.MessageHandler;
import top.flagshen.xsrobot.entity.request.Message;
import top.flagshen.xsrobot.entity.request.MessageRequest;
import top.flagshen.xsrobot.entity.response.MsgReponse;
import top.flagshen.xsrobot.template.XsRobotTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author 150149
 */
@Service
public class LongTextCheck implements MessageHandler {

    @Autowired
    private XsRobotTemplate xsRobotTemplate;

    public LongTextCheck() {
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setType("message");
        messageRequest.setSubtype("group");
        RobotApplication.messageManager.registerMessageHandler(this,messageRequest);
        System.out.println("[信息]长文本检测模块已启动");
    }

    @Override
    public void handleMessage(MessageRequest messageRequest) throws IOException {
        if (!AdminGroup.isAdminGroup(messageRequest.getGroup().getId())) {
            return;
        }

        MsgReponse<ArrayList<Long>> admins = xsRobotTemplate.getGroupAdmins(messageRequest.getBot(),messageRequest.getGroup().getId());
        for (long l:admins.getData()) {
            if (messageRequest.getSender().getId()==l) {
                return;
            }
        }

        if (messageRequest.getMessage().getText().length()>500 && !messageRequest.getMessage().getText().contains("<?xml")) {
            String msg = "[@" + messageRequest.getSender().getId() + "] ("+ messageRequest.getSender().getId() + ")发言字数超过500字,已撤回消息";
            xsRobotTemplate.sendGroupMsg(messageRequest.getBot(), messageRequest.getGroup().getId(), msg, false);
            System.out.println("[信息]文本过长已撤回信息，长度:" + messageRequest.getMessage().getText().length());
            HttpUtil.deleteGroupMessage(messageRequest.getBot(),messageRequest.getGroup().getId(),messageRequest.getMessage().getId(),messageRequest.getMessage().getFlag());

        }

    }
}
