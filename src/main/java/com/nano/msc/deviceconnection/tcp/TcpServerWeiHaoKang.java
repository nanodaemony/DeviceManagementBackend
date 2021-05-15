package com.nano.msc.deviceconnection.tcp;



import com.nano.msc.deviceconnection.FilterRegex;
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
 * 威浩康的数据接收服务类
 * @author cz
 */
public class TcpServerWeiHaoKang {

    /**
     * 监听的端口号
     */
    private int port;
    /**
     * 客户端Socket
     */
    private Socket dataSocket;
    /**
     * 服务器套接字
     */
    private ServerSocket serverSocket;
    /**
     * 本地等待接入线程
     */
    private Thread weiHaoKangListenThread;
    /**
     * TCP接收数据线程
     */
    private Thread weiHaoKangReceiveThread;
    /**
     * 连接数量
     */
    private int connectionCounter = 0;
    /**
     * 输入流
     */
    private InputStream inputStream = null;
    /**
     * 输出流
     */
    private OutputStream outputStream = null;
    /**
     * 数据接收时间
     */
    private long receiveTime;


    public static void main(String[] args) {
        new TcpServerWeiHaoKang().startServer(10086);
    }

    /**
     * 开启服务器
     */
    public void startServer(int port) {
        this.port = port;
        weiHaoKangListenThread = new Thread(listenWork);
        weiHaoKangListenThread.setName("威浩康监听");
        weiHaoKangListenThread.start();
    }


    /**
     * 等待连接的线程
     */
    private Runnable listenWork = new Runnable() {
        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(port);
                printInfo("开启TCP端口" + port);
            } catch (IOException e) {
                printInfo("TCP端口异常" + e.getMessage());
            }

            // 循环等待接入
            while (true){
                try{
                    // 开启监听并在此阻塞，直到有接入
                    dataSocket = serverSocket.accept();
                    connectionCounter++;                    // TCP连接的数量

                    // 启动接收线程 TCP接收线程
                    weiHaoKangReceiveThread = new Thread(receiveWork);
                    weiHaoKangReceiveThread.setName("威浩康接收" + connectionCounter);
                    weiHaoKangReceiveThread.start();

                    printInfo("admin", "TCP连接数量:" + connectionCounter);
                    printInfo("admin", "getRemoteSocketAddress:" + dataSocket.getRemoteSocketAddress());
                    printInfo("admin", "TCP接收数据线程数:" + connectionCounter);
                    printInfo("admin", "getInetAddress:" + dataSocket.getInetAddress());
                    printInfo("admin", "getPort:" + dataSocket.getPort());
                    printInfo("admin", "getLocalAddress:" + dataSocket.getLocalAddress());
                    printInfo("admin", "getLocalPort:" + dataSocket.getLocalPort());
                    printInfo("admin", "getLocalSocketAddress:" + dataSocket.getLocalSocketAddress());
                    //admin:getInetAddress:/172.20.29.90
                    //admin:getPort:56691
                    //admin:getLocalAddress:/172.20.29.75
                    //admin:getLocalPort:10086
                    //admin:getLocalSocketAddress:/172.20.29.75:10086
                    //admin:getRemoteSocketAddress:/172.20.29.90:56691
                    dataSocket.setKeepAlive(true);
                }
                catch (IOException e){
                    printInfo("dataSocket IOException:", e.getMessage());
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
            printInfo("admin", "TCP可以接收数据:" + port);

            try {
                inputStream = dataSocket.getInputStream();
                outputStream = dataSocket.getOutputStream();
                printInfo("admin", "TCP获取输入与输出流:" + port);
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (true)
            {
                try {
                    // 接收Buffer长度在此设置
                    final byte[] buf = new byte[ReceiveBufferLength.WEI_HAO_KANG];

                    // 获取Buffer长度,在此阻塞
                    final int len = inputStream.read(buf);

                    if (len != -1) {
                        // 原始数据字符串
                        final String text = new String(buf, 0, len);
                        // 获取16进制数据
                        String hexData = getBufHexStr(buf);

                        // 深圳威浩康
                        assert hexData != null;
                        // 深圳威浩康的数据
                        if (hexData.matches(FilterRegex.WEI_HAO_KANG_FILTER)){
                            printInfo("admin", "TCP转换接收数据:" + hexData);

                            // 心跳包的定义是另一个
                            byte[] dataSendOut = getHexBytes("AA3000CC");
                            // 发送查询包，如果不行则试试心跳包
                            outputStream.write(dataSendOut);
                            // 接收计数器
                            // 更新时间
                            receiveTime = System.currentTimeMillis();
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

    /**
     * 重启服务 要清理资源！
     */
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
            if (weiHaoKangReceiveThread != null){
                weiHaoKangReceiveThread.interrupt();
                weiHaoKangReceiveThread = null;
            }
            if (weiHaoKangListenThread != null){
                weiHaoKangListenThread.interrupt();
                weiHaoKangListenThread = null;
            }

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * 判断服务是否接收数据正常
     * @return 是否接收
     */
    public boolean isServerOn() {
        return System.currentTimeMillis() - receiveTime > 30000;
    }


}
