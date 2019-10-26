package com.learning.project.wlk.share.netty;

import java.nio.channels.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * 代码来自：https://my.oschina.net/leoson/blog/106385
 */
public class EchoClient extends  Thread {
    // 空闲计数器,如果空闲超过10次,将检测server是否中断连接.
    private static int idleCounter = 0;
    private Selector selector;
    private SocketChannel socketChannel;
    private ByteBuffer temp = ByteBuffer.allocate(1024);

    public static void main(String[] args) throws IOException {
        EchoClient client= new EchoClient();
        new Thread(client).start();
        //client.sendFirstMsg();
    }

    public EchoClient() throws IOException {
        // 同样的,注册闹钟.
        this.selector = Selector.open();

        // 连接远程server
        socketChannel = SocketChannel.open();
        // 如果快速的建立了连接,返回true.如果没有建立,则返回false,并在连接后出发Connect事件.
        Boolean isConnected = socketChannel.connect(new InetSocketAddress("localhost", 7878));
        socketChannel.configureBlocking(false);
        SelectionKey key = socketChannel.register(selector, SelectionKey.OP_READ);

        if (isConnected) {
            this.sendFirstMsg();
        } else {
            // 如果连接还在尝试中,则注册connect事件的监听. connect成功以后会出发connect事件.
            key.interestOps(SelectionKey.OP_CONNECT);
        }
    }

    public void sendFirstMsg() throws IOException {
        String msg = "Hello NIO.";
        socketChannel.write(ByteBuffer.wrap(msg.getBytes(Charset.forName("UTF-8"))));
    }

    @Override
    public void run() {
        while (true) {
            try {
                // 阻塞,等待事件发生,或者1秒超时. num为发生事件的数量.
                int num = this.selector.select(1000);
                if (num ==0) {
                    idleCounter ++;
                    if(idleCounter >10) {
                        // 如果server断开了连接,发送消息将失败.
                        try {
                            this.sendFirstMsg();
                        } catch(ClosedChannelException e) {
                            e.printStackTrace();
                            this.socketChannel.close();
                            return;
                        }
                    }
                    continue;
                } else {
                    idleCounter = 0;
                }
                Set<SelectionKey> keys = this.selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    it.remove();
                    if (key.isConnectable()) {
                        // socket connected
                        SocketChannel sc = (SocketChannel)key.channel();
                        if (sc.isConnectionPending()) {
                            sc.finishConnect();
                        }
                        // send first message;
                        this.sendFirstMsg();
                    }
                    if (key.isReadable()) {
                        // msg received.
                        SocketChannel sc = (SocketChannel)key.channel();
                        this.temp = ByteBuffer.allocate(1024);
                        int count = sc.read(temp);
                        if (count<0) {
                            sc.close();
                            continue;
                        }
                        // 切换buffer到读状态,内部指针归位.
                        temp.flip();
                        String msg = Charset.forName("UTF-8").decode(temp).toString();
                        System.out.println("Client received ["+msg+"] from server address:" + sc.getRemoteAddress());

                        Thread.sleep(1000);
                        // echo back.
                        sc.write(ByteBuffer.wrap(msg.getBytes(Charset.forName("UTF-8"))));

                        // 清空buffer
                        temp.clear();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
