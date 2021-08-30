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

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 150149
 */
@Service
public class ADMessageCheck implements MessageHandler {

    @Autowired
    private XsRobotTemplate xsRobotTemplate;

    private List<String> keywords = new ArrayList<>();

    public ADMessageCheck() {
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setType("message");
        messageRequest.setSubtype("group");
        RobotApplication.messageManager.registerMessageHandler(this,messageRequest);

        File dirs = new File("C:/Users/Administrator/Desktop/小栗子/新建文件夹/广告词.txt");
        if (dirs.exists()) {
            try {
                InputStreamReader read = new InputStreamReader(new FileInputStream(dirs),"GBK");//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    if (!"".equals(lineTxt)) {
                        keywords.add(lineTxt);
                    }
                }
                read.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("[信息]广告监测模块已启动");
    }

    @Override
    public void handleMessage(MessageRequest messageRequest) throws IOException {
        if (!AdminGroup.isAdminGroup(messageRequest.getGroup().getId())) {
            return;
        }

        String msg = messageRequest.getMessage().getText();
        for (String s: keywords ) {
            if (msg.contains(s)) {
                String rep = "发现广告词【" + s + "】已将 " + messageRequest.getSender().getName() + "(" + messageRequest.getSender().getId() + ") 踢出";
                MsgReponse resp = xsRobotTemplate.sendGroupMsg(messageRequest.getBot(), messageRequest.getGroup().getId(), rep, false);
                HttpUtil.deleteGroupMessage(messageRequest.getBot(),messageRequest.getGroup().getId(),messageRequest.getMessage().getId(),messageRequest.getMessage().getFlag());
                xsRobotTemplate.delMember(messageRequest.getBot(),messageRequest.getGroup().getId(),messageRequest.getSender().getId(),false);
                return;
            }
        }


    }
}
