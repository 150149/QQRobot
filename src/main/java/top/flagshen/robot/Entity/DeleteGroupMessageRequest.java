package top.flagshen.robot.Entity;

/**
 * @author 150149
 */
public class DeleteGroupMessageRequest {

    private long bot;
    private long group;
    private long id;
    private long flag;

    public long getBot() {
        return bot;
    }

    public void setBot(long bot) {
        this.bot = bot;
    }

    public long getGroup() {
        return group;
    }

    public void setGroup(long group) {
        this.group = group;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFlag() {
        return flag;
    }

    public void setFlag(long flag) {
        this.flag = flag;
    }
}
