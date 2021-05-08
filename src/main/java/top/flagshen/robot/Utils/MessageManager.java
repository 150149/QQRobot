package top.flagshen.robot.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.flagshen.xsrobot.entity.request.MessageRequest;
import top.flagshen.xsrobot.template.XsRobotTemplate;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author 150149
 */
@Service
public class MessageManager {

    @Autowired
    private XsRobotTemplate xsRobotTemplate;

    private ConcurrentLinkedQueue<MessageRequest> messageList = new ConcurrentLinkedQueue<>();
    private ConcurrentHashMap<MessageHandler,MessageRequest> handlers = new ConcurrentHashMap<>();

    public void addMessage(MessageRequest messageRequest) {
        messageList.add(messageRequest);
    }

    public MessageRequest getMessage() {
        return messageList.poll();
    }

    public void registerMessageHandler(MessageHandler messageHandler,MessageRequest messageRequest) {
        handlers.put(messageHandler,messageRequest);
    }

    public void onMessage(MessageRequest messageRequest) throws IOException {
        for (Map.Entry<MessageHandler, MessageRequest> entry : handlers.entrySet()) {
            if (messageRequest.getType().equals(entry.getValue().getType()) || entry.getValue().getType()==null) {
                if (messageRequest.getSubtype().equals(entry.getValue().getSubtype()) || entry.getValue().getSubtype()==null) {
                    if ( entry.getValue().getMessage()==null) {
                        entry.getKey().handleMessage(messageRequest);
                    } else {
                        if(messageRequest.getMessage().getText().toLowerCase().contains(entry.getValue().getMessage().getText().toLowerCase())) {
                            entry.getKey().handleMessage(messageRequest);
                        }
                    }
                }
            }
        }
    }

}
