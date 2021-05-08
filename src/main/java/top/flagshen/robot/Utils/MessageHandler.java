package top.flagshen.robot.Utils;

import top.flagshen.xsrobot.entity.request.MessageRequest;

import java.io.IOException;

/**
 * @author 150149
 */
public interface MessageHandler {

    public void handleMessage(MessageRequest messageRequest) throws IOException;

}
