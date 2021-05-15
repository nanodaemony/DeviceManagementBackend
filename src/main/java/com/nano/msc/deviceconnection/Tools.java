package com.nano.msc.deviceconnection;


import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;


/**
 * 一些基础的工具方法类
 * @author cz
 */
public class Tools {

    /**
    * @Description: 获取设备的MAC地址
    * @Param: []
    * @return: MAC String
    */
    public static String getMacAddress() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")){
                    continue;
                }

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }




    /**
     * 将16进制的byte数组转换成字符串
     * @param raw byte数组
     * @return 返回16进制字符串
     */
    public static String getBufHexStr(byte[] raw){
        String hexCode = "0123456789ABCDEF";
        if ( raw == null ) {
            return null;
        }
        final StringBuilder hex = new StringBuilder( 2 * raw.length );
        for ( final byte b : raw ) {
            hex.append(hexCode.charAt((b & 0xF0) >> 4))
                    .append(hexCode.charAt((b & 0x0F)));
        }
        return hex.toString();
    }


    /**
     * 将16进制的字符串转成字符数组
     * @param str 待转换字符串
     * @return 返回byte数组
     */
    public static byte[] getHexBytes(String str){
        byte[] bytes = new byte[str.length() / 2];
        for(int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }
        return bytes;
    }

    // TODO: 在此封装日志打印的类

    public static void printInfo(String data){
        System.out.println("admin: " + data);
    }


    public static void printInfo(int data){
        System.out.println("admin: " + data);
    }


    public static void printInfo(String data, String data2){
        System.out.println("admin: " + data + ", " + data2);
    }



}
