package top.flagshen.robot.service.SpecialWord;

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

/**
 * @author 150149
 */
@Service
public class MODList implements MessageHandler {

    @Autowired
    private XsRobotTemplate xsRobotTemplate;

    public MODList() {
        MessageRequest messageRequest = new MessageRequest();
        Message message = new Message();
        message.setText("MOD资源");
        messageRequest.setType("message");
        messageRequest.setSubtype("group");
        messageRequest.setMessage(message);
        RobotApplication.messageManager.registerMessageHandler(this,messageRequest);
        System.out.println("[信息]MOD资源回复模块已启动");
    }


    @Override
    public void handleMessage(MessageRequest messageRequest) throws IOException {
        if (!AdminGroup.isAdminGroup(messageRequest.getGroup().getId())) {
            return;
        }

        String msg = "[pic,hash=168B5FAC636E07676F1609C844DA3E22]\n\nMOD资源站：\n\nhttp://121.37.239.127:25564/index.php?share/folder&user=100&sid=XPTWIr3H\n";
        MsgReponse resp = xsRobotTemplate.sendGroupMsg(messageRequest.getBot(), messageRequest.getGroup().getId(), msg, false);
    }
}
