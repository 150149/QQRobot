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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author 150149
 */
@Service
public class QuickSendMute implements MessageHandler {

    @Autowired
    private XsRobotTemplate xsRobotTemplate;

    private ConcurrentHashMap<Long,Integer> counter = new ConcurrentHashMap<>();
    private ScheduledExecutorService checkThreadPool = Executors.newScheduledThreadPool(10);

    public QuickSendMute() {
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setType("message");
        messageRequest.setSubtype("group");
        RobotApplication.messageManager.registerMessageHandler(this,messageRequest);
        System.out.println("[信息]快速发消息禁言模块已启动");

        checkThreadPool.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for (Map.Entry<Long,Integer> entry: counter.entrySet()) {
                    if (entry.getValue()!=0) {
                        counter.put(entry.getKey(),0);
                    }
                }
            }
        },1L,1000L, TimeUnit.MILLISECONDS);
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

        counter.putIfAbsent(messageRequest.getSender().getId(),0);
        if (counter.get(messageRequest.getSender().getId())>=5) {
            xsRobotTemplate.banGroupOne(messageRequest.getBot(),messageRequest.getGroup().getId(),messageRequest.getSender().getId(),60);
            String msg = "[@" + messageRequest.getSender().getId() + "] (" + messageRequest.getSender().getId() + ")发言过快，已被禁言1分钟";
            MsgReponse resp = xsRobotTemplate.sendGroupMsg(messageRequest.getBot(), messageRequest.getGroup().getId(), msg, false);
            counter.put(messageRequest.getSender().getId(),0);
        } else {
            counter.put(messageRequest.getSender().getId(),counter.get(messageRequest.getSender().getId())+1);
        }
    }
}
