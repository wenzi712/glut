package com.jacknic.glut.util;

import com.jacknic.glut.R;

/**
 * 静态配置文件
 */

public final class Config {
    /**
     * 财务处接口
     */
    private static final String URL_CW_INTERFACE = "http://cwwsjf.glut.edu.cn:8088/chargeonline/interFaceB2C.aspx?method=%s&studentid=%s";
    /**
     * 教务处登录验证码
     */
    public static final String URL_JW_CAPTCHA = "http://202.193.80.58:81/academic/getCaptcha.do";
    /**
     * 教务处登录表单地址
     */
    public static final String URL_JW_LOGIN_CHECK = "http://202.193.80.58:81/academic/j_acegi_security_check";
    /**
     * 课表链接
     */
    public static final String URL_JW_COURSE = "http://202.193.80.58:81/academic/student/currcourse/currcourse.jsdo";
    /**
     * 教务处登录验证码检验
     */
    public static final String URL_JW_CAPTCHA_CHECK = "http://202.193.80.58:81/academic/checkCaptcha.do?captchaCode=";

    /**
     * 财务
     */
    public static final String PREFER_CW = "prefer_cw";
    /**
     * 教务
     */
    public static final String PREFER_JW = "prefer_jw";
    /**
     * 图书
     */
    public static final String PREFER_TS = "prefer_ts";

    /**
     * 登录识别
     */
    public static final int LOGIN_FLAG_JW = 0;
    public static final int LOGIN_FLAG_CW = 1;
    public static final int LOGIN_FLAG_TS = 2;
    /**
     * 设置
     */
    public static final String PREFER_SETTING = "prefer_setting";
    public static final int SETTING_THEME_COLOR_INDEX = 4;//默认蓝色主题样式
    public static final String SETTING_THEME_INDEX = "theme_index";
    public static final String SETTING_COLOR_INDEX = "color_index";
    public static final String IS_REFRESH = "is_refresh";


    /**
     * 教务当前周
     */
    public static final String JW_WEEK_SELECT = "week_select";
    /**
     * 当前周存入时间
     */
    public static final String JW_YEAR_WEEK_OLD = "year_week_old";
    /**
     * 教务最后一周
     */
    public static final String JW_WEEK_END = "end_week";
    /**
     * 教务学年
     */
    public static final String JW_SCHOOL_YEAR = "school_year";
    /**
     * 学号
     */
    public static final String SID = "sid";
    /**
     * 密码
     */
    public static final String PASSWORD = "password";
    /**
     * 教务学期
     */
    public static final String JW_SEMESTER = "semester";
    /**
     * 登录标志
     */
    public static final String LOGIN_FLAG = "login_flag";
    /**
     * 教务学生信息
     */
    public static final String URL_JW_STUDENT_INFO = "http://202.193.80.58:81/academic/student/studentinfo/studentInfoModifyIndex.do?frombase=0&wantTag=0&groupId=&moduleId=2060";
    /**
     * 财务sid
     */
    public static final String STUDENT_ID = "studentid";
    /**
     * 配色数组
     */
    public static int[] colors = {
            R.color.barDark,
            R.color.purple,
            R.color.purpleDeep,
            R.color.indigo,
            R.color.blue,
            R.color.blueLight,
            R.color.cyan,
            R.color.teal,
            R.color.green,
            R.color.greenLight,
            R.color.brown,
            R.color.amber,
            R.color.orangeDeep,
            R.color.red,
            R.color.pink,
    };
    /**
     * 主题样式
     */
    public static final int[] THEME_LIST = new int[]{
            R.style.AppTheme,
            R.style.AppTheme_purple,
            R.style.AppTheme_purpleDeep,
            R.style.AppTheme_indigo,
            R.style.AppTheme_blue,
            R.style.AppTheme_blueLight,
            R.style.AppTheme_cyan,
            R.style.AppTheme_teal,
            R.style.AppTheme_green,
            R.style.AppTheme_greenLight,
            R.style.AppTheme_brown,
            R.style.AppTheme_amber,
            R.style.AppTheme_orangeDeep,
            R.style.AppTheme_red,
            R.style.AppTheme_pink,
    };

    /**
     * 财务接口链接
     *
     * @param method    执行的操作
     * @param studentId 学生财务序号
     * @return URL
     */
    public static String getQueryUrlCW(String method, String studentId) {
        return String.format(URL_CW_INTERFACE, method, studentId);
    }

    private Config() {
    }


}
