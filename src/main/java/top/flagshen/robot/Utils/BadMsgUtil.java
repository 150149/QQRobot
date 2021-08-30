package top.flagshen.robot.Utils;

import top.flagshen.robot.mapper.RestrictMessageMapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 150149
 */
public class BadMsgUtil {

    public static String reason="";

    public static int count=0;

    public static boolean isZang(String  rec) {
        return isZang1(rec);
    }

    public static boolean isZangRestrict(String rec) {
        return isZang2(rec) || isZang1(rec);
    }

    public static boolean isZang1(String rec) {
        if(!rec.contains("[") && !rec.contains("{") && !rec.contains("<") ) {
            if ((!Pattern.matches(".*[a-z]*m[a-z]*d[a-z]+.*",rec) || Pattern.matches(".*[a-z]*md[a-z]*.*",rec) ) && Pattern.matches(".*[Mm]+.*[Dd]+.*",rec) && !rec.contains("mod") && !rec.contains("motherland") && !rec.contains("[") && !rec.contains("{"))   {
                reason="md";
                return true;
            }
            if ((!Pattern.matches(".*[A-Za-z]*a.*v[A-Za-z]+.*",rec) || Pattern.matches(".*[A-Za-z]*av[A-Za-z]*.*",rec) ) && Pattern.matches(".*[aA]+.*[Vv]+.*",rec) && !rec.contains("java") ) {
                reason="av";
                return true;
            }
            if (rec.contains("ma") && rec.contains("bi") && (rec.contains("你") || rec.contains("ni"))) {
                reason="ni ma bi";
                return true;
            }
            if (Pattern.matches(".*cao.*[你我他她它啊阿吖]+.*",rec) || rec.equals("cao")) {
                reason="cao";
                return true;
            }
            if (Pattern.matches(".*([是=]|is).*d.*o.*g.*",rec) || rec.equals("dog")) {
                reason="dog";
                return true;
            }
            if (Pattern.matches(".*f.*u.*c.*k.*[你我他她它啊啊吖]+.*",rec) || rec.contains("fuck")) {
                reason="fuck";
                return true;
            }
            if ((!Pattern.matches(".*[A-Za-z]*f[a-z]*w[A-Za-z]+.*",rec) || Pattern.matches(".*[A-Za-z]*fw[A-Za-z]*.*",rec) ) && Pattern.matches(".*[fF]+.*[Ww]+.*",rec) && !rec.contains("http") ) {
                reason="fw";
                return true;
            }
            if ((!Pattern.matches(".*[a-z]+m[a-z]*m[b-hj-z]*p[a-z]+.*",rec) || Pattern.matches(".*[a-z]*mmp[a-z]*.*",rec) ) && Pattern.matches(".*[Mm]+.*[Mm]+.*[Pp]+.*",rec) && !rec.contains("http") ) {
                reason="mmp";
                return true;
            }
            if ((!Pattern.matches(".*[a-z]*n[a-z]*t[a-z]+.*",rec) || Pattern.matches(".*[a-z]*nt[a-z]*.*",rec) ) && Pattern.matches(".*[Nn]+.*[Tt]+.*",rec) && !rec.contains("minecraft") && !rec.contains("net") && !rec.contains("http") ) {
                reason="nt";
                return true;
            }
            if ((!Pattern.matches(".*[a-z]*n[a-z]*m[a-z]+.*",rec) || Pattern.matches(".*[a-z]*nm[a-z]*.*",rec) ) && Pattern.matches(".*[Nn]+.*[Mm]+.*",rec) && !rec.contains("nme") && !rec.contains("http") && !rec.contains("http") ) {
                reason="nm";
                return true;
            }
            if ((!Pattern.matches(".*[a-z]*n[a-z]*m[a-z]*d[a-z]+.*",rec) || Pattern.matches(".*[a-z]*nmd[a-z]*.*",rec) ) && Pattern.matches(".*[Nn]+.*[Mm]+.*[Dd]+.*",rec) && !rec.contains("http") ) {
                reason="nmd";
                return true;
            }
            if ((!Pattern.matches(".*[a-z]*n[a-z]*m[a-z]*s[a-hj-z]*l[a-z]+.*",rec) || Pattern.matches(".*[a-z]*nmsl[a-z]*.*",rec) ) && Pattern.matches(".*[Nn]+.*[Mm]+.*[Ss]+.*[Ll]+.*",rec) && !rec.contains("http") ) {
                reason="nmsl";
                return true;
            }
            if ((!Pattern.matches(".*[a-z]*s[a-z]*b[a-z]+.*",rec) || Pattern.matches(".*[a-z]*sb[a-z]*.*",rec) ) && Pattern.matches(".*[Ss]+.*[Bb]+.*",rec) && !rec.contains("usb") && !rec.contains("steam") && !rec.contains("boss") && !rec.contains("http")) {
                reason="sb";
                return true;
            }
            if ((!Pattern.matches(".*[a-z]*t[a-z]*m[a-z]+.*",rec) || Pattern.matches(".*[a-z]*tm[a-z]*.*",rec) ) && Pattern.matches(".*[Tt]+.*[Mm]+.*",rec) && !rec.contains("steam") && !rec.contains("tpm") && !rec.contains("http") ) {
                reason="tm";
                return true;
            }
            if ((!Pattern.matches(".*[a-z]*w[a-z]*t[a-z]*m[a-z]+.*",rec) || Pattern.matches(".*[a-z]*wtm[a-z]*.*",rec) ) && Pattern.matches(".*[Ww]+.*[Tt]+.*[Mm]+.*",rec) && !rec.contains("http") ) {
                reason="wtm";
                return true;
            }
            if ((!Pattern.matches(".*[a-z]*w[a-z]*t[a-z]*m[a-z]*d[a-z]+.*",rec) || Pattern.matches(".*[a-z]*wtmd[a-z]*.*",rec) ) && Pattern.matches(".*[Ww]+.*[Tt]+.*[Mm]+.*[Dd]+.*",rec) && !rec.contains("http") ) {
                reason="wtmd";
                return true;
            }
            if ((!Pattern.matches(".*[a-z]*x[a-z]*s[a-z]+.*",rec) || Pattern.matches(".*[a-z]*xs[a-z]*.*",rec) ) && Pattern.matches(".*[Xx]+.*[Ss]+.*",rec) && !rec.contains("http") ) {
                reason="xs";
                return true;
            }
            if ((!Pattern.matches(".*[a-z]*x[a-z]*s[a-z]*w[a-z]+.*",rec) || Pattern.matches(".*[a-z]*xsw[a-z]*.*",rec) ) && Pattern.matches(".*[Xx]+.*[Ss]+.*[wW]+.*",rec) && !rec.contains("http") ) {
                reason="xsw";
                return true;
            }
            if ((!Pattern.matches(".*[a-z]*x[a-z]*s[a-z]*w[a-z]*l[a-z]+.*",rec) || Pattern.matches(".*[a-z]*xswl[a-z]*.*",rec) ) && Pattern.matches(".*[Xx]+.*[Ss]+.*[wW]+.*[lL]+.*",rec) && !rec.contains("http") ) {
                reason="xswl";
                return true;
            }
            if ((!Pattern.matches(".*[a-z]*y[a-z]*y[a-z]*r[a-z]+.*",rec) || Pattern.matches(".*[a-z]*yyr[a-z]*.*",rec) ) && Pattern.matches(".*[Yy]+.*[Yy]+.*[Rr]+.*",rec) && !rec.contains("http") ) {
                reason="yyr";
                return true;
            }
            if ((!Pattern.matches(".*[a-z]*z[a-z]*z[a-z]+.*",rec) || Pattern.matches(".*[a-z]*zz[a-z]*.*",rec) ) && Pattern.matches(".*[Zz]+.*[Zz]+.*",rec) && !rec.contains("http") ) {
                reason="zz";
                return true;
            }
            if ((!Pattern.matches(".*[a-z]*j[a-z]*b[a-z]+.*",rec) || Pattern.matches(".*[a-z]*jb[a-z]*.*",rec) ) && Pattern.matches(".*[jJ]+.*[Bb]+.*",rec) && !rec.contains("http") ) {
                reason="jb";
                return true;
            }
            if ((!Pattern.matches(".*[a-z]*l[a-z]*j[a-hj-z]+.*",rec) || Pattern.matches(".*[a-z]*lj[a-z]*.*",rec) ) && Pattern.matches(".*[Ll]+.*[Jj]+.*",rec) && !rec.contains("http") ) {
                reason="lj";
                return true;
            }
            if ((!Pattern.matches(".*[a-z]*c[a-z]*j[a-hj-z]+.*",rec) || Pattern.matches(".*[a-z]*cj[a-z]*.*",rec) ) && Pattern.matches(".*[Cc]+.*[Jj]+.*",rec) && !rec.contains("http") ) {
                reason="cj";
                return true;
            }
        }

        if (rec.contains("{")) {
            return false;
        } else if (!rec.contains("[@") && rec.contains("[") ) {
            return false;
        } else if (rec.contains("[reply") && rec.contains("]")) {
            String rep1 = rec.substring(0,rec.indexOf("[reply"));
            String rep2 = rec.substring(rec.indexOf("]"),rec.length());
            rec=rep1+rep2;
        } else if (rec.contains("[bigface") && rec.contains("]")) {
            String rep1 = rec.substring(0,rec.indexOf("[bigface"));
            String rep2 = rec.substring(rec.indexOf("]"),rec.length());
            rec=rep1+rep2;
        } else if (rec.contains("[pic") && rec.contains("]")) {
            String rep1 = rec.substring(0,rec.indexOf("[pic"));
            String rep2 = rec.substring(rec.indexOf("]"),rec.length());
            rec=rep1+rep2;
        }

        rec=rec.replaceAll("[a-zA-z]+://[^\\s]*","");
        rec=rec.replaceAll("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$","");
        rec=rec.replaceAll("[\\pP‘’“”]","");
        rec=rec.replaceAll(" ","");

        if (Pattern.matches(".*[h哈]{4,500}.*",rec)) {
            reason="hhh";
            return true;
        }
        if (Pattern.matches(".*[6六]{4,500}.*",rec)) {
            reason="666";
            return true;
        }
        if ("c".equals(rec)) {
            reason="c";
            return true;
        }
        if ("去世".equals(rec) || (!rec.contains("去世界") && rec.contains("去世"))) {
            reason="去世";
            return true;
        }
        if (!Pattern.matches(".*去.*你[家就房界服饮堵屋也那]+.*",rec) && Pattern.matches(".*去.*你.*",rec) && !rec.contains("过去") && !Pattern.matches(".*去[看带帮找]+你.*",rec) ) {
            reason="去你";
            return true;
        }
        if (Pattern.matches("^求[\\pP‘’“”]*$",rec) || Pattern.matches(".*求[爆暴被你锤打死]+.*",rec)) {
            reason="求";
            return true;
        }
        if (Pattern.matches("^球[\\pP‘’“”]*$",rec) || Pattern.matches(".*球[爆暴被你锤打死]+.*",rec)) {
            reason="球";
            return true;
        }
        if (Pattern.matches(".*草.*[你尼泥我他她它啊阿吖]+.*",rec) || "草".equals(rec) || Pattern.matches(".*[了吗嘛吖的马]+草$",rec) && !rec.contains("草泥土") && !rec.contains("草方块")) {
            reason="草";
            return true;
        }
        if (Pattern.matches(".*操.*[你尼泥我他她它啊阿吖]+.*",rec) || "操".equals(rec) || Pattern.matches(".*[了吗嘛吖的马]+操$",rec) || rec.contains("我操") && !Pattern.matches(".*操[作练场]+.*",rec) && !Pattern.matches(".*[微]+操.*",rec) ) {
            reason="操";
            return true;
        }
        if (rec.contains("骂") && !rec.contains("骂人") && !rec.contains("别骂") && !rec.contains("别再骂")) {
            reason="骂";
            return true;
        }
        if (Pattern.matches(".*[你我他她它是]+.*瞎.*",rec) && Pattern.matches(".*瞎[,.，。/?？:：'了子]*$",rec)) {
            reason="瞎";
            return true;
        }
        if (Pattern.matches(".*原来.*有.*啊.*",rec)) {
            reason="原来 有 啊";
            return true;
        }
        if (rec.contains("弟") && !rec.contains("兄弟") && !rec.contains("小弟")) {
            reason="弟";
            return true;
        }
        if (Pattern.matches(".*爷[是很给打锤捶你我他她它的]+.*",rec) || Pattern.matches(".*[是很给打锤捶你我他她它的]+爷.*",rec) || "爷".equals(rec)) {
            reason="爷";
            return true;
        }
        if (!Pattern.matches(".*[早明昨终不丽今他何值假生前半周团当改抗末整每春红节连隔白]+日.*",rec) && rec.contains("日") && !Pattern.matches(".*[0-9]+.*日.*",rec) && !Pattern.matches(".*日[产光出军历在后服增子夜葵头寇常心志文数斑月冕蚀本期照用程落记货间需食珥晷]+.*",rec)) {
            reason="日";
            return true;
        }
        if (!Pattern.matches(".*啥b[a-z]+.*",rec) && rec.contains("啥b") ) {
            reason="啥b";
            return true;
        }
        if (!Pattern.matches(".*[催勒威强紧懵进牛追]+逼.*",rec) && rec.contains("逼") && !Pattern.matches(".*逼[上人仄供债我勒和宫抢真肖良视近迫]+.*",rec)) {
            reason="逼";
            return true;
        }
        if (Pattern.matches(".*[你尼泥腻逆妮倪伱妳他她它]+.*[吗嘛吖祃妈玛码嫲麻駡蚂馬鎷媽马]+.*",rec) && !Pattern.matches(".*[你尼泥腻逆拟妮倪伱妳他她它]+.*[能可以行收相的愿玩去是愁守挖播不怎指倒压喜用熟欢懂要咋抢在多少跑代发换家服买吃卖有群给同了好]+.*[吗嘛吖祃玛码妈嫲上麻駡蚂馬鎷媽马]+.*",rec) ) {
            reason="妈";
            return true;
        }
        if (!Pattern.matches(".*病[人休原例假入况包区危友句因号夫员室害床症情故愈毒株毒源灶状理痛粒菌虫退重魔院]+.*",rec) && rec.contains("病") && !Pattern.matches(".*[发患得见疾探治看该防]+病.*",rec) ) {
            reason="病";
            return true;
        }
        if (!Pattern.matches(".*没[事什关准命完心戏收有治法空阳用毛病落人奖了钱时辙谱趣来去回错门接开碰顶齿]+.*",rec) && rec.contains("又没")) {
            reason="又没";
            return true;
        }
        if (rec.contains("就这") && !Pattern.matches(".*这[一个么会儿些天时样点几种次歌般边部里]+.*",rec)) {
            reason="就这";
            return true;
        }
        if (!Pattern.matches(".*菜[价农刀单叶团园脯圃地场子市店板枯汤单油牛瓜田畦篮籽肴色羊花苗疏芽谱豆饭馆鹅鸽]+.*",rec) && rec.contains("菜") && !Pattern.matches(".*[主做冷凉剩卤名咸偷种买卖切头常烧套白小野川粤榨油甜点生紫荤素青鲁芥花香韭酸酒]+菜.*",rec)) {
            reason="菜";
            return true;
        }
        if (rec.contains("蔡ji") || rec.contains("蔡鸡") || rec.contains("蔡狗")) {
            reason="蔡";
            return true;
        }
        if (Pattern.matches(".*[你他她它母公跟傻狗是像]+.*猪.*",rec)) {
            reason="猪";
            return true;
        }
        if (!Pattern.matches(".*丑[事剧化态恶时牛类角话闻陋]+.*",rec) && rec.contains("丑") && !Pattern.matches(".*[丁出家好过太献美辛]+丑.*",rec)) {
            reason="丑";
            return true;
        }
        if (rec.contains("仙人") && !rec.contains("仙人掌")) {
            reason="仙人";
            return true;
        }
        if (rec.contains("啥比") && !rec.contains("啥比较")) {
            reason="啥比";
            return true;
        }
        if (rec.contains("怂") && !rec.contains("怂恿")) {
            reason="怂";
            return true;
        }
        return false;
    }

    private static String quChong(String str) {
        String s = "";
        int count=0;
        Matcher m = Pattern.compile("(\\w)\\1*").matcher(str);
        while (m.find()) {
            s +=  m.group().subSequence(0, 1);
        }
        return s;
    }

    private static boolean isZang2(String rec) {
        if ((!Pattern.matches(".*[A-Za-z]*a.*v[A-Za-z]+.*",rec) || Pattern.matches(".*[A-Za-z]*av[A-Za-z]*.*",rec) ) && Pattern.matches(".*[aA]+.*[Vv]+.*",rec) && !rec.contains("java") ) {
            reason="av";
            return true;
        }
        if (rec.contains("ma") && rec.contains("bi") && (rec.contains("你") || rec.contains("ni"))) {
            reason="ni ma bi";
            return true;
        }
        if (Pattern.matches(".*cao.*[你我他她它啊阿吖]+.*",rec) || rec.equals("cao")) {
            reason="cao";
            return true;
        }
        if (Pattern.matches(".*([是=]|is).*d.*o.*g.*",rec) || rec.equals("dog")) {
            reason="dog";
            return true;
        }
        if (Pattern.matches(".*f.*u.*c.*k.*[你我他她它啊啊吖]+.*",rec) || rec.contains("fuck")) {
            reason="fuck";
            return true;
        }
        if ((!Pattern.matches(".*[A-Za-z]*f[\\pP‘’“” A-DF-HJ-Za-df-hj-z]*w[A-Za-z]+.*",rec) || Pattern.matches(".*[A-Za-z]*fw[A-Za-z]*.*",rec) ) && Pattern.matches(".*[fF]+.*[Ww]+.*",rec) ) {
            reason="fw";
            return true;
        }
        if ((!Pattern.matches(".*[a-z]*m[\\pP‘’“” b-z]*d[a-z]+.*",rec) || Pattern.matches(".*[a-z]*md[a-z]*.*",rec) ) && Pattern.matches(".*[Mm]+.*[Dd]+.*",rec) && !rec.contains("mod")) {
            reason="md";
            return true;
        }
        if ((!Pattern.matches(".*[a-z]+m[\\pP‘’“” b-z]*m[b-hj-z]*p[a-z]+.*",rec) || Pattern.matches(".*[a-z]*mmp[a-z]*.*",rec) ) && Pattern.matches(".*[Mm]+.*[Mm]+.*[Pp]+.*",rec) ) {
            reason="mmp";
            return true;
        }
        if ((!Pattern.matches(".*[a-z]*n[\\pP‘’“” b-np-z]*t[a-z]+.*",rec) || Pattern.matches(".*[a-z]*nt[a-z]*.*",rec) ) && Pattern.matches(".*[Nn]+.*[Tt]+.*",rec) ) {
            reason="nt";
            return true;
        }
        if ((!Pattern.matches(".*[a-z]*n[\\pP‘’“” a-hj-z]*m[a-z]+.*",rec) || Pattern.matches(".*[a-z]*nm[a-z]*.*",rec) ) && Pattern.matches(".*[Nn]+.*[Mm]+.*",rec) && !rec.contains("nme") ) {
            reason="nm";
            return true;
        }
        if ((!Pattern.matches(".*[a-z]*n[\\pP‘’“” a-hj-z]*m[\\pP‘’“” b-z]*d[a-z]+.*",rec) || Pattern.matches(".*[a-z]*nmd[a-z]*.*",rec) ) && Pattern.matches(".*[Nn]+.*[Mm]+.*[Dd]+.*",rec) ) {
            reason="nmd";
            return true;
        }
        if ((!Pattern.matches(".*[a-z]*n[\\pP‘’“” a-hj-z]*m[\\pP‘’“” b-z]*s[a-hj-z]*l[a-z]+.*",rec) || Pattern.matches(".*[a-z]*nmsl[a-z]*.*",rec) ) && Pattern.matches(".*[Nn]+.*[Mm]+.*[Ss]+.*[Ll]+.*",rec) ) {
            reason="nmsl";
            return true;
        }
        if ((!Pattern.matches(".*[a-z]*s[\\pP‘’“” b-gi-rt-z]*b[a-z]+.*",rec) || Pattern.matches(".*[a-z]*sb[a-z]*.*",rec) ) && Pattern.matches(".*[Ss]+.*[Bb]+.*",rec) && !rec.contains("usb")) {
            reason="sb";
            return true;
        }
        if ((!Pattern.matches(".*[a-z]*t[\\pP‘’“” b-z]*m[a-z]+.*",rec) || Pattern.matches(".*[a-z]*tm[a-z]*.*",rec) ) && Pattern.matches(".*[Tt]+.*[Mm]+.*",rec) ) {
            reason="tm";
            return true;
        }
        if ((!Pattern.matches(".*[a-z]*w[\\pP‘’“” a-np-z]*t[\\pP‘’“” b-z]*m[a-z]+.*",rec) || Pattern.matches(".*[a-z]*wtm[a-z]*.*",rec) ) && Pattern.matches(".*[Ww]+.*[Tt]+.*[Mm]+.*",rec) ) {
            reason="wtm";
            return true;
        }
        if ((!Pattern.matches(".*[a-z]*w[\\pP‘’“” a-np-z]*t[\\pP‘’“” b-z]*m[\\pP‘’“” b-hj-z]*d[a-z]+.*",rec) || Pattern.matches(".*[a-z]*wtmd[a-z]*.*",rec) ) && Pattern.matches(".*[Ww]+.*[Tt]+.*[Mm]+.*[Dd]+.*",rec) ) {
            reason="wtmd";
            return true;
        }
        if ((!Pattern.matches(".*[a-z]*x[b-hj-np-z \\pP‘’“”]*s[a-z]+.*",rec) || Pattern.matches(".*[a-z]*xs[a-z]*.*",rec) ) && Pattern.matches(".*[Xx]+.*[Ss]+.*",rec) ) {
            reason="xs";
            return true;
        }
        if ((!Pattern.matches(".*[a-z]*x[\\pP‘’“” b-hj-np-z]*s[\\pP‘’“” a-hj-z]*w[a-z]+.*",rec) || Pattern.matches(".*[a-z]*xsw[a-z]*.*",rec) ) && Pattern.matches(".*[Xx]+.*[Ss]+.*[wW]+.*",rec) ) {
            reason="xsw";
            return true;
        }
        if ((!Pattern.matches(".*[a-z]*x[\\pP‘’“” b-hj-np-z]*s[\\pP‘’“” a-hj-z]*w[\\pP‘’“” a-np-z]*l[a-z]+.*",rec) || Pattern.matches(".*[a-z]*xswl[a-z]*.*",rec) ) && Pattern.matches(".*[Xx]+.*[Ss]+.*[wW]+.*[lL]+.*",rec) ) {
            reason="xswl";
            return true;
        }
        if ((!Pattern.matches(".*[a-z]*y[\\pP‘’“” a-fhj-mo-z]*y[\\pP‘’“” a-fhj-mo-z]*r[a-z]+.*",rec) || Pattern.matches(".*[a-z]*yyr[a-z]*.*",rec) ) && Pattern.matches(".*[Yy]+.*[Yy]+.*[Rr]+.*",rec) ) {
            reason="yyr";
            return true;
        }
        if ((!Pattern.matches(".*[a-z]*z[\\pP‘’“” a-gj-z]*z[a-z]+.*",rec) || Pattern.matches(".*[a-z]*zz[a-z]*.*",rec) ) && Pattern.matches(".*[Zz]+.*[Zz]+.*",rec) ) {
            reason="zz";
            return true;
        }
        if ((!Pattern.matches(".*[a-z]*j[\\pP‘’“” a-gj-z]*b[a-z]+.*",rec) || Pattern.matches(".*[a-z]*jb[a-z]*.*",rec) ) && Pattern.matches(".*[jJ]+.*[Bb]+.*",rec) ) {
            reason="jb";
            return true;
        }
        if ((!Pattern.matches(".*[a-z]*l[\\pP‘’“” b-z]*j[a-hj-z]+.*",rec) || Pattern.matches(".*[a-z]*lj[a-z]*.*",rec) ) && Pattern.matches(".*[Ll]+.*[Jj]+.*",rec) ) {
            reason="lj";
            return true;
        }
        if ((!Pattern.matches(".*[a-z]*c[\\pP‘’“” b-z]*j[a-hj-z]+.*",rec) || Pattern.matches(".*[a-z]*cj[a-z]*.*",rec) ) && Pattern.matches(".*[Cc]+.*[Jj]+.*",rec) ) {
            reason="cj";
            return true;
        }
        return false;
    }


}
