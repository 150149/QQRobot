package top.flagshen.robot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import top.flagshen.robot.RobotApplication;
import top.flagshen.robot.Utils.AdminGroup;
import top.flagshen.robot.Utils.HttpUtil;
import top.flagshen.robot.Utils.MessageHandler;
import top.flagshen.robot.mapper.BadMessageMapper;
import top.flagshen.robot.pojo.BadMessage;
import top.flagshen.xsrobot.entity.request.Message;
import top.flagshen.xsrobot.entity.request.MessageRequest;
import top.flagshen.xsrobot.entity.response.MsgReponse;
import top.flagshen.xsrobot.template.XsRobotTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 150149
 */
@Service
public class BadMessageCheck implements MessageHandler {

    @Autowired
    private XsRobotTemplate xsRobotTemplate;

    @Autowired
    private BadMessageMapper badMessageMapper;

    public BadMessageCheck() {
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setType("message");
        messageRequest.setSubtype("group");
        RobotApplication.messageManager.registerMessageHandler(this,messageRequest);

        System.out.println("[信息]脏话撤回模块已启动");
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

        List<BadMessage> badMsgList = badMessageMapper.search();

        for (BadMessage b:badMsgList) {
            String s = b.getBadmsg().toLowerCase();
            if (messageRequest.getMessage().getText().toLowerCase().contains(s) && !messageRequest.getMessage().getText().contains("[")) {
                System.out.println("[信息]发现脏话：" + s);
                HttpUtil.deleteGroupMessage(messageRequest.getBot(),messageRequest.getGroup().getId(),messageRequest.getMessage().getId(),messageRequest.getMessage().getFlag());
            }
        }

    }
}
