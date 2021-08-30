package top.flagshen.robot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import top.flagshen.robot.RobotApplication;
import top.flagshen.robot.Utils.AdminGroup;
import top.flagshen.robot.Utils.BadMsgUtil;
import top.flagshen.robot.Utils.HttpUtil;
import top.flagshen.robot.Utils.MessageHandler;
import top.flagshen.robot.mapper.BadMessageMapper;
import top.flagshen.robot.mapper.RestrictMessageMapper;
import top.flagshen.robot.pojo.BadMessage;
import top.flagshen.xsrobot.entity.request.Message;
import top.flagshen.xsrobot.entity.request.MessageRequest;
import top.flagshen.xsrobot.entity.response.MsgReponse;
import top.flagshen.xsrobot.template.XsRobotTemplate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author 150149
 */
@Service
public class BadMessageCheck implements MessageHandler {

    @Autowired
    private XsRobotTemplate xsRobotTemplate;

    @Autowired
    private BadMessageMapper badMessageMapper;

    @Autowired
    private RestrictMessageMapper restrictMessageMapper;

    private Map<Long,Integer> gp = new HashMap<>();
    private Map<Long,Boolean> restrict = new HashMap<>();

    public BadMessageCheck() {
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setType("message");
        messageRequest.setSubtype("group");
        RobotApplication.messageManager.registerMessageHandler(this,messageRequest);

        Timer timer = new Timer();
        TimerTask myTask = new TimerTask() {
            @Override
            public void run() {
                for (Map.Entry<Long,Integer> entry:gp.entrySet()) {
                    restrict.putIfAbsent(entry.getKey(),false);
                    if (restrict.get(entry.getKey()) && gp.get(entry.getKey())<=3) {
                        restrict.put(entry.getKey(),false);
                        if (!AdminGroup.isAdminGroup(entry.getKey())) {
                            continue;
                        }
                        String msg = "本群火势降级，机器人退出严格模式!";
                        try {
                            MsgReponse resp = xsRobotTemplate.sendGroupMsg(1497855934, entry.getKey(), msg, false);
                        } catch (IOException e) { }
                    }
                    gp.put(entry.getKey(),0);
                }
            }
        };
        timer.schedule(myTask, 2000, 20*1000);
        //延迟2秒后，每2秒执行一次myTask任务


        System.out.println("[信息]脏话撤回模块已启动");
    }

    @Override
    public void handleMessage(MessageRequest messageRequest) throws IOException {
        if (!AdminGroup.isAdminGroup(messageRequest.getGroup().getId())) {
            return;
        }

        if (messageRequest.getGroup().getId()==428757595) {
            return;
        }

        List<BadMessage> badMsgList = badMessageMapper.search();
        List<BadMessage> restrictMsgList = restrictMessageMapper.search();

        MsgReponse<ArrayList<Long>> admins = xsRobotTemplate.getGroupAdmins(messageRequest.getBot(),messageRequest.getGroup().getId());
        for (long l:admins.getData()) {
            if (messageRequest.getSender().getId()==l) {
                return;
            }
        }


        boolean isChehui = false;
        boolean hasBadName = false;

        String rec = messageRequest.getMessage().getText().toLowerCase();
        restrict.putIfAbsent(messageRequest.getGroup().getId(),false);
        if (!restrict.get(messageRequest.getGroup().getId())) {
            if (BadMsgUtil.isZang(rec)) {
                isChehui=true;
            }
            for (BadMessage b:badMsgList) {
                String s = b.getBadmsg().toLowerCase();
                if (messageRequest.getMessage().getText().toLowerCase().contains(s) ) {
                    isChehui=true;
                    BadMsgUtil.reason=s;
                }
                if (messageRequest.getSender().getName().toLowerCase().contains(s)) {
                    hasBadName=true;
                } else {
                    if (BadMsgUtil.isZang(messageRequest.getSender().getName().toLowerCase())) {
                        hasBadName=true;
                    }
                }
            }
        } else {
            if (BadMsgUtil.isZangRestrict(rec)) {
                isChehui=true;
            }
            for (BadMessage b:restrictMsgList) {
                String s = b.getBadmsg().toLowerCase();
                if (messageRequest.getMessage().getText().toLowerCase().contains(s) ) {
                    isChehui=true;
                    BadMsgUtil.reason=s;
                }
            }
        }


        if (isChehui) {
            cheHui(BadMsgUtil.reason,messageRequest);
        }
        if (hasBadName) {
            xsRobotTemplate.setGroupCard(messageRequest.getBot(),messageRequest.getGroup().getId(),messageRequest.getSender().getId(),"违规昵称" + getRandomString(8));
            String msg = "[@" + messageRequest.getSender().getId() + "] (" + messageRequest.getSender().getId() + ")昵称含有不当词汇，已修改";
            MsgReponse resp = xsRobotTemplate.sendGroupMsg(messageRequest.getBot(), messageRequest.getGroup().getId(), msg, false);
            System.out.println("[信息]发现发言人: " + messageRequest.getSender().getName() + " 昵称违规,已修正");
        }

    }

    private String getRandomString(int length){
        String str="0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(10);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    private void cheHui(String reason,MessageRequest messageRequest) throws IOException {
        System.out.println("[信息]匹配脏话：" + reason);
        HttpUtil.deleteGroupMessage(messageRequest.getBot(),messageRequest.getGroup().getId(),messageRequest.getMessage().getId(),messageRequest.getMessage().getFlag());
        gp.putIfAbsent(messageRequest.getGroup().getId(),0);
        gp.put(messageRequest.getGroup().getId(),gp.get(messageRequest.getGroup().getId())+1);
        restrict.putIfAbsent(messageRequest.getGroup().getId(),false);
        if (gp.get(messageRequest.getGroup().getId())>=5 && !restrict.get(messageRequest.getGroup().getId())) {
            restrict.put(messageRequest.getGroup().getId(),true);
            String msg = "本群火势升级，机器人进入严格模式!";
            MsgReponse resp = xsRobotTemplate.sendGroupMsg(messageRequest.getBot(), messageRequest.getGroup().getId(), msg, false);
        }
        File file = new File("badmsg.log");
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat slf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        bw.write( "[" + slf.format(calendar.getTime()) + "] " + "脏话(" + reason + ")(严格:" + (boolean)restrict.get(messageRequest.getGroup().getId()) + ")(" + BadMsgUtil.count +") - " + messageRequest.getMessage().getText());
        bw.newLine();
        bw.flush();
        bw.close();

    }






}
