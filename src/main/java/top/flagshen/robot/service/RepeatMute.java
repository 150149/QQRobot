package top.flagshen.robot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.flagshen.robot.Entity.FiveTextList;
import top.flagshen.robot.RobotApplication;
import top.flagshen.robot.Utils.AdminGroup;
import top.flagshen.robot.Utils.MessageHandler;
import top.flagshen.xsrobot.entity.request.Message;
import top.flagshen.xsrobot.entity.request.MessageRequest;
import top.flagshen.xsrobot.entity.response.MsgReponse;
import top.flagshen.xsrobot.template.XsRobotTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 150149
 */
@Service
public class RepeatMute implements MessageHandler {

    @Autowired
    private XsRobotTemplate xsRobotTemplate;

    private HashMap<Long, FiveTextList> words = new HashMap<>();

    public RepeatMute() {
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setType("message");
        messageRequest.setSubtype("group");
        RobotApplication.messageManager.registerMessageHandler(this,messageRequest);
        System.out.println("[信息]复读机禁言模块已启动");
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

        words.putIfAbsent(messageRequest.getSender().getId(),new FiveTextList());
        FiveTextList theWords =  words.get(messageRequest.getSender().getId());
        theWords.add(messageRequest.getMessage().getText());
        if (theWords.isRepeat()) {
            xsRobotTemplate.banGroupOne(messageRequest.getBot(),messageRequest.getGroup().getId(),messageRequest.getSender().getId(),60*10);
            String msg = "[@" + messageRequest.getSender().getId() + "] (" + messageRequest.getSender().getId() + ")重复发言5次" +
                    ",已被禁言10分钟";
            MsgReponse resp = xsRobotTemplate.sendGroupMsg(messageRequest.getBot(), messageRequest.getGroup().getId(), msg, false);
        }
        words.put(messageRequest.getSender().getId(),theWords);

    }
}
