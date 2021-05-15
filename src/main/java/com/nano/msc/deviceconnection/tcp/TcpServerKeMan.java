package com.nano.msc.deviceconnection.tcp;

import com.nano.msc.deviceconnection.DeviceAccessCons;
import com.nano.msc.deviceconnection.ReceiveBufferLength;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static com.nano.msc.deviceconnection.Tools.getBufHexStr;
import static com.nano.msc.deviceconnection.Tools.getHexBytes;
import static com.nano.msc.deviceconnection.Tools.printInfo;


/**
 * 科曼的服务器类
 * @author cz
 */
public class TcpServerKeMan {

    /**
     * 监听的端口号
     */
    private int port;
    /**
     * 客户端
     */
    private Socket dataSocket;
    /**
     * 服务器套接字
     */
    private ServerSocket serverSocket;
    /**
     * TCP接收线程
     */
    private Thread keManReceiveThread;
    /**
     * 连接计数器
     */
    private int connectionCounter = 0;
    /**
     * 监听线程
     */
    private Thread keManListenThread;
    /**
     * 输入输出流
     */
    private InputStream inputStream = null;
    private OutputStream outputStream = null;
    /**
     * 数据接收时间
     */
    private long receiveTime;


    public static void main(String[] args) {
        new TcpServerKeMan().startServer(10087);
    }


    public void startServer(int port) {
        this.port = port;
        keManListenThread = new Thread(listenWork);
        keManListenThread.setName("科曼监听");
        keManListenThread.start();
    }

    /**
     * 监听线程
     */
    private Runnable listenWork = new Runnable() {
        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("开启TCP端口" + port);
            } catch (IOException e) {
                System.out.println("TCP端口异常" + e.getMessage());
            }

            while (true){
                try{
                    // 开启监听并在此阻塞，直到有接入
                    dataSocket = serverSocket.accept();

                    // 启动接收线程 TCP接收线程
                    keManReceiveThread = new Thread(receiveWork);
                    keManReceiveThread.setName("科曼接收");
                    keManReceiveThread.start();
                    connectionCounter++;        // TCP连接的数量
                    printInfo("admin", "TCP连接数量:" + connectionCounter);
                    printInfo("admin", "getRemoteSocketAddress:" + dataSocket.getRemoteSocketAddress());

                    dataSocket.setKeepAlive(true);
                }
                catch (IOException e){
                    System.out.println("dataSocket IOException:" + e.getMessage());
                }
            }
        }
    };

    /**
     * 接收数据的线程
     */
    private Runnable receiveWork = new Runnable() {
        @Override
        public void run() {
            System.out.println("TCP可以接收数据:" + port);
            try {
                inputStream = dataSocket.getInputStream();
                outputStream = dataSocket.getOutputStream();
                System.out.println("TCP获取输入与输出流:" + port);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int receiveCounter = 0;
            while (true) {
                try {
                    // 接收Buffer长度在此设置
                    final byte[] buf = new byte[ReceiveBufferLength.KE_MAN];
                    // 获取Buffer长度,在此阻塞
                    final int len = inputStream.read(buf);
                    if (len != -1) {
                        // 原始数据字符串
                        final String text = new String(buf, 0, len);
                        System.out.println(text);
                        // 获取16进制数据
                        String hexData = getBufHexStr(buf);
                        // BBBB09 上电注册包
                        // 深圳科曼
                        if (hexData.startsWith("BBBB")){
                            System.out.println(hexData);
                            receiveCounter++;
                            if (receiveCounter == 2){
                                // 设备上电注册应答包
                                byte[] dataSendOut = getHexBytes(DeviceAccessCons.DEVICE_REGISTER_RESPONSE);
                                outputStream.write(dataSendOut);
                            }

                            // 5s每次的心跳包
                            if (receiveCounter % 5 == 0){
                                // 心跳包
                                byte[] dataSendOut = getHexBytes(DeviceAccessCons.HERAT_BEAT_PACKAGE);
                                outputStream.write(dataSendOut);
                            }

                            if (hexData.startsWith("BBBB0E")){
                                receiveTime = System.currentTimeMillis();
                            }else {
                                receiveTime = System.currentTimeMillis();
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    };


    public void restartServer() {
        try {
            if (dataSocket.isClosed()){
                dataSocket.close();
            }
            if (serverSocket != null){
                serverSocket.close();
            }
            if (inputStream != null){
                inputStream.close();
            }
            if (outputStream != null){
                outputStream.close();
            }
            if (keManReceiveThread != null){
                keManReceiveThread.interrupt();
                keManReceiveThread = null;
            }
            if (keManListenThread != null){
                keManListenThread.interrupt();
                keManListenThread = null;
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean isServerOn() {
        return System.currentTimeMillis() - receiveTime > 30000;
    }



}


