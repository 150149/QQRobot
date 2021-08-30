package top.flagshen.robot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.flagshen.xsrobot.entity.response.MsgReponse;
import top.flagshen.xsrobot.entity.response.Robot;
import top.flagshen.xsrobot.template.XsRobotTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 150149
 */
@Service
public class AutoLogin {

    @Autowired
    private XsRobotTemplate xsRobotTemplate;

    public AutoLogin() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    MsgReponse<ArrayList<Robot>> msgReponse =xsRobotTemplate.getQqList();
                    for (Robot robot:msgReponse.getData()) {
                        if (!"登录完毕".equals(robot.getState())) {
                            System.out.println("[-------自动登录任务--------]");
                            MsgReponse msgReponse2 =xsRobotTemplate.login(1497855934);
                            System.out.println("登录尝试: " + msgReponse2.getMsg());
                            System.out.println("[-------自动登录任务--------]");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, 1000,60*1000*60);
        // 设定指定的时间time,此处为2000毫秒
    }
}
