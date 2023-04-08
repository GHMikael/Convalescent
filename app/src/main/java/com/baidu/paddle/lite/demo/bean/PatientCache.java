package com.baidu.paddle.lite.demo.bean;

public class PatientCache {
    private static String account;

    private static String info;

    private static int actionId;

    private static  String doctorId;

    public static String getAccount() {
        return account;
    }

    public static void setAccount(String account) {
        PatientCache.account = account;
    }

    public static String getInfo() {
        return info;
    }

    public static void setInfo(String info) {
        PatientCache.info = info;
    }

    public static int getActionId() {
        return actionId;
    }

    public static void setActionId(int actionId) {
        PatientCache.actionId = actionId;
    }

    public static String getDoctorId() {
        return doctorId;
    }

    public static void setDoctorId(String doctorId) {
        PatientCache.doctorId = doctorId;
    }
}
