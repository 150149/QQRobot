package top.flagshen.robot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.flagshen.robot.RobotApplication;
import top.flagshen.robot.Utils.AdminGroup;
import top.flagshen.robot.Utils.MessageHandler;
import top.flagshen.robot.mapper.QBindMapper;
import top.flagshen.robot.pojo.QBind;
import top.flagshen.xsrobot.entity.request.Message;
import top.flagshen.xsrobot.entity.request.MessageRequest;
import top.flagshen.xsrobot.entity.response.MsgReponse;
import top.flagshen.xsrobot.template.XsRobotTemplate;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author 150149
 */
//@Service
public class SearchQQBind implements MessageHandler {

    @Autowired
    private XsRobotTemplate xsRobotTemplate;

    @Autowired
    private QBindMapper qBindMapper;

    public SearchQQBind() {
        MessageRequest messageRequest = new MessageRequest();
        Message message = new Message();
        message.setText("查Q");
        messageRequest.setType("message");
        messageRequest.setSubtype("group");
        messageRequest.setMessage(message);
        RobotApplication.messageManager.registerMessageHandler(this,messageRequest);
        System.out.println("[信息]Q绑查询模块已启动");
    }


    @Override
    public void handleMessage(MessageRequest messageRequest) throws IOException {
        if (!AdminGroup.isAdminGroup(messageRequest.getGroup().getId())) {
            return;
        }

        SimpleDateFormat slf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String search = messageRequest.getMessage().getText().replaceAll(" ","");
        search = search.replaceAll("查Q","").replaceAll("查q","");

        if ("".equals(search)) {
            MsgReponse resp = xsRobotTemplate.sendGroupMsg(messageRequest.getBot(), messageRequest.getGroup().getId(), "查Q用法: 查Q [Q号|手机号]", false);
            return;
        }



        System.out.println("Q绑开始查询: " + search);
        List<QBind> qBinds = qBindMapper.search( search);
        if (qBinds.size()>0) {
            StringBuilder sb = new StringBuilder();
            sb.append("-------------------------\n搜索内容: ");
            sb.append(search);
            sb.append("\n");
            for (QBind qBind: qBinds) {
                if (qBind.getString().contains("----")) {
                    qBind.setString(qBind.getString().replaceAll(search,"").split("----")[0] );
                }
                sb.append("搜索结果: ");
                sb.append(qBind.getString());
                sb.append("\n");
            }
            sb.append("-------------------------");
            MsgReponse resp = xsRobotTemplate.sendGroupMsg(messageRequest.getBot(), messageRequest.getGroup().getId(), sb.toString(), false);
        } else {
            String msg = "-------------------------\n搜索内容: " + search + "\n搜索结果: " + "找不到该记录" + "\n-------------------------" ;
            MsgReponse resp = xsRobotTemplate.sendGroupMsg(messageRequest.getBot(), messageRequest.getGroup().getId(), msg, false);
        }

        System.out.println("[信息]Q绑查询完成: " + search);
    }
}
