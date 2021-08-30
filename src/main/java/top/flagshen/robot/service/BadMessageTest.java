package top.flagshen.robot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.flagshen.robot.RobotApplication;
import top.flagshen.robot.Utils.AdminGroup;
import top.flagshen.robot.Utils.BadMsgUtil;
import top.flagshen.robot.Utils.HttpUtil;
import top.flagshen.robot.Utils.MessageHandler;
import top.flagshen.robot.mapper.BadMessageMapper;
import top.flagshen.robot.pojo.BadMessage;
import top.flagshen.xsrobot.entity.request.MessageRequest;
import top.flagshen.xsrobot.entity.response.MsgReponse;
import top.flagshen.xsrobot.template.XsRobotTemplate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * @author 150149
 */
@Service
public class BadMessageTest implements MessageHandler {

    @Autowired
    private XsRobotTemplate xsRobotTemplate;

    @Autowired
    private BadMessageMapper badMessageMapper;

    public BadMessageTest() {
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setType("message");
        messageRequest.setSubtype("group");
        RobotApplication.messageManager.registerMessageHandler(this,messageRequest);

        System.out.println("[信息]脏话撤回测试模块已启动");
    }

    @Override
    public void handleMessage(MessageRequest messageRequest) throws IOException {

        List<BadMessage> badMsgList = badMessageMapper.search();
        BadMsgUtil.count++;

        String rec = messageRequest.getMessage().getText().toLowerCase();
        if (BadMsgUtil.isZang(rec)) {
            cheHui(BadMsgUtil.reason,messageRequest);
        }

        for (BadMessage b:badMsgList) {
            String s = b.getBadmsg().toLowerCase();
            if (messageRequest.getMessage().getText().toLowerCase().contains(s) ) {
                cheHui(s,messageRequest);
            }
        }

    }

    private void cheHui(String reason,MessageRequest messageRequest) throws IOException {
        System.out.println("[测试]匹配脏话：" + reason);
        File file = new File("badmsg.log");
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat slf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        bw.write( "[" + slf.format(calendar.getTime()) + "] " + "脏话(" + reason + ")(" + BadMsgUtil.count +") - " + messageRequest.getMessage().getText());
        bw.newLine();
        bw.flush();
        bw.close();

    }




}
