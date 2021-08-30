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

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 150149
 */
@Service
public class AutoReply implements MessageHandler {

    @Autowired
    private XsRobotTemplate xsRobotTemplate;

    private HashMap<String,String> keywords = new HashMap<>();

    public AutoReply() {
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setType("message");
        messageRequest.setSubtype("group");
        RobotApplication.messageManager.registerMessageHandler(this,messageRequest);

        File dirs = new File("C:/Users/Administrator/Desktop/小栗子/新建文件夹/关键词");
        if (!dirs.exists()) {
            dirs.mkdir();
        } else {
            File[] files = dirs.listFiles();
            for (File file :files) {
                StringBuilder sb = new StringBuilder();
                try {
                    InputStreamReader read = new InputStreamReader(new FileInputStream(file),"GBK");//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    while((lineTxt = bufferedReader.readLine()) != null){
                        sb.append(lineTxt).append("\n");
                    }
                    read.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
                keywords.put(file.getName().replaceAll(".txt",""),sb.toString());
            }
        }

        System.out.println("[信息]关键词回复模块已启动");
    }

    @Override
    public void handleMessage(MessageRequest messageRequest) throws IOException {
        if (!AdminGroup.isAdminGroup(messageRequest.getGroup().getId())) {
            return;
        }

        String msg = messageRequest.getMessage().getText();
        for (Map.Entry<String,String> entry :keywords.entrySet() ) {
            if (msg.contains(entry.getKey())) {
                MsgReponse resp = xsRobotTemplate.sendGroupMsg(messageRequest.getBot(), messageRequest.getGroup().getId(), entry.getValue(), false);
                System.out.println("[信息]关键词发送完成");
                return;
            }
        }


    }
}
