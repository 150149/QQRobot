package top.flagshen.robot.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.flagshen.robot.RobotApplication;
import top.flagshen.xsrobot.entity.request.MessageRequest;

import java.io.IOException;

@RestController
public class MainController {

    @PostMapping("/robot")
    public void dealMsg(@RequestBody MessageRequest messageRequest) throws IOException {
//        //获取消息基本信息
//        String type = messageRequest.getType();
//        String subtype = messageRequest.getSubtype();
//        long bot = messageRequest.getBot();
//        long time = messageRequest.getTime();
//        //信息和群组
//        Message message = messageRequest.getMessage();
//        Group group = messageRequest.getGroup();
//        //事件相关
//        Operator operator = messageRequest.getOperator();
//        Trigger trigger = messageRequest.getTrigger();
//        //发送者信息
//        Sender sender = messageRequest.getSender();
//        //对不同的消息进行处理
//        if (type.equals("message")) {//类型为消息
//            if (subtype.equals("private")) {
//                //当前为私聊消息
//                messageService.dealPrivateMsg(bot, sender, message);
//            } else if (subtype.equals("group")) {
//                messageService.dealGroupMsg(bot, group, sender, message);
//            }
//        } else if (type.equals("event")) {//事件类型处理
//            switch (subtype) {
//                case "BeInvitedJoinGroup"://群事件_我被邀请加入群
//                    break;
//                case "MemberJoinedGroup"://群事件_某人加入了群
//                    break;
//                default:
//                    break;
//            }
//        }
        try{

            if (messageRequest.getType().equals("message")) {
                if (messageRequest.getSender()!=null) {
                    if (messageRequest.getSender().getId()==(long)1497855934) {
                        return;
                    }
                }
                System.out.println("[信息] 接收到消息: 发送人:" + messageRequest.getSender().getId() + " 发送内容: " + messageRequest.getMessage().getText());
            } else {
                if (messageRequest.getTrigger()!=null) {
                    if (messageRequest.getTrigger().getId()==(long)1497855934) {
                        return;
                    }
                }
                System.out.println("[信息] 接收到消息" + messageRequest.getType());
            }
            RobotApplication.messageManager.onMessage(messageRequest);
        }catch (Throwable throwable) {
            System.out.println("-----发现错误-----");
            throwable.printStackTrace();
            System.out.println("------------------");
        }
    }
}
