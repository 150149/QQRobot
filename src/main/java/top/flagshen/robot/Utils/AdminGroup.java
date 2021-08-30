package top.flagshen.robot.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 150149
 */
public class AdminGroup {

    public static List<Long> groups = new ArrayList<>();

    static {
        groups.add((long) 428757595);
        groups.add((long) 1054842957);
    }

    public static boolean isAdminGroup(Long s) {
        return groups.contains(s);
    }

}
