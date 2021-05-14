package top.flagshen.robot.Entity;

/**
 * @author 150149
 */
public class FiveTextList {

    private String[] texts = new String[5];

    public void add(String text) {
        if (texts[0]==null) {
            texts[0]=text;
        } else {
            texts[4]=texts[3];
            texts[3]=texts[2];
            texts[2]=texts[1];
            texts[1]=texts[0];

            texts[0]=text;
        }
    }

    public boolean isRepeat() {
        if (!isAllNotNull()) {
            return false;
        }

        if (texts[0].equalsIgnoreCase(texts[1])) {
            if (texts[1].equalsIgnoreCase(texts[2])) {
                if (texts[2].equalsIgnoreCase(texts[3])) {
                    if (texts[3].equalsIgnoreCase(texts[4])) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isAllNotNull() {
        if (texts[0]!=null) {
            if (texts[1]!=null) {
                if (texts[2]!=null) {
                    if (texts[3]!=null) {
                        if (texts[4]!=null) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
