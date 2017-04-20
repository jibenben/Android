package com.example.jpushdemo;
/**
 * Created by Kaike on 2017/4/1.
 */
/*
修改各种网址的路径然后方便以后修改
 */
public class Routing {
    private static String domainname="http://www.haolexue100.com/";
    private static  String studenturl="http://192.168.1.3";
    private static  String orderurl="http://139.199.5.64/login/color.html";
    private static  String courserul="http://m.haolexue100.com/course/index?r=1491959604";
    private static String loginurl="http://139.199.5.64/login/";
    private static String logouturl="http://139.199.5.64/login/logout.html";
    private static String version="http://139.199.5.64/login/version.php";
//    private static String ;
    public static  String getStudenturl(){
        return domainname+studenturl;
    }
    public static String getOrderurl(){
        return domainname+orderurl;
    }
    public static String getcourserul(){
        return domainname+courserul;
    }
    public static String getLoginurl(){
        return domainname+loginurl;
    }
    public static String getLogouturl(){
        return domainname+logouturl;
    }
    public static String getVersion(){
        return domainname+version;
    }
}
