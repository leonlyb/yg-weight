package com.example.dechuan.hktv.ClientDemo;

import com.sun.jna.NativeLong;

public class MonitorCameraInfo {
    private String cameraIp;
    private short cameraPort;
    private String userName;
    private String userPwd;

    private String barrierName;
    private NativeLong barrierId;
    private String mqttTopic;
    private NativeLong barrierType;//0:入口,1:出口
    private String barrierIp;

    public MonitorCameraInfo(String cameraIp, short cameraPort, String userName, String userPwd, String barrierName) {
        this.cameraIp = cameraIp;
        this.cameraPort = cameraPort;
        this.userName = userName;
        this.userPwd = userPwd;
        this.barrierName = barrierName;
    }

    public MonitorCameraInfo() {
    }

    public String getBarrierIp() {
        return barrierIp;
    }

    public void setBarrierIp(String barrierIp) {
        this.barrierIp = barrierIp;
    }

    public NativeLong getBarrierId() {
        return barrierId;
    }

    public void setBarrierId(NativeLong barrierId) {
        this.barrierId = barrierId;
    }

    public String getMqttTopic() {
        return mqttTopic;
    }

    public void setMqttTopic(String mqttTopic) {
        this.mqttTopic = mqttTopic;
    }

    public NativeLong getBarrierType() {
        return barrierType;
    }

    public void setBarrierType(NativeLong barrierType) {
        this.barrierType = barrierType;
    }

    public String getCameraIp() {
        return cameraIp;
    }

    public void setCameraIp(String cameraIp) {
        this.cameraIp = cameraIp;
    }

    public short getCameraPort() {
        return cameraPort;
    }

    public void setCameraPort(short cameraPort) {
        this.cameraPort = cameraPort;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getBarrierName() {
        return barrierName;
    }

    public void setBarrierName(String barrierName) {
        this.barrierName = barrierName;
    }
}