package com.learning.project.wlk.share.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;


/**
 * 代码来自：https://my.oschina.net/leoson/blog/106385
 */
public class EchoServer {

    public static SelectorLoop connectionBell;
    public static SelectorLoop readBell;
    public boolean isReadBellRunning=false;

    public static void main(String[] args) throws IOException {
        new EchoServer().startServer();
    }

    // 启动服务器
    public void startServer() throws IOException {
        // 准备好一个闹钟.当有链接进来的时候响.
        connectionBell = new SelectorLoop();

        // 准备好一个闹装,当有read事件进来的时候响.
        readBell = new SelectorLoop();

        // 开启一个server channel来监听
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 开启非阻塞模式
        ssc.configureBlocking(false);

        ServerSocket socket = ssc.socket();
        socket.bind(new InetSocketAddress("localhost",7878));

        // 给闹钟规定好要监听报告的事件,这个闹钟只监听新连接事件.
        ssc.register(connectionBell.getSelector(), SelectionKey.OP_ACCEPT);
        new Thread(connectionBell).start();
    }

    // Selector轮询线程类
    public class SelectorLoop implements Runnable {
        private Selector selector;
        private ByteBuffer temp = ByteBuffer.allocate(1024);

        public SelectorLoop() throws IOException {
            this.selector = Selector.open();
        }

        public Selector getSelector() {
            return this.selector;
        }

        @Override
        public void run() {
            while(true) {
                try {
                    // 阻塞,只有当至少一个注册的事件发生的时候才会继续.
                    this.selector.select();

                    Set<SelectionKey> selectKeys = this.selector.selectedKeys();
                    Iterator<SelectionKey> it = selectKeys.iterator();
                    while (it.hasNext()) {
                        SelectionKey key = it.next();
                        it.remove();
                        // 处理事件. 可以用多线程来处理.
                        this.dispatch(key);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void dispatch(SelectionKey key) throws IOException, InterruptedException {
            if (key.isAcceptable()) {
                // 这是一个connection accept事件, 并且这个事件是注册在serversocketchannel上的.
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                // 接受一个连接.
                SocketChannel sc = ssc.accept();

                // 对新的连接的channel注册read事件. 使用readBell闹钟.
                sc.configureBlocking(false);
                sc.register(readBell.getSelector(), SelectionKey.OP_READ);

                // 如果读取线程还没有启动,那就启动一个读取线程.
                synchronized(EchoServer.this) {
                    if (!EchoServer.this.isReadBellRunning) {
                        EchoServer.this.isReadBellRunning = true;
                        new Thread(readBell).start();
                    }
                }

            } else if (key.isReadable()) {
                // 这是一个read事件,并且这个事件是注册在socketchannel上的.
                SocketChannel sc = (SocketChannel) key.channel();
                // 写数据到buffer
                int count = sc.read(temp);
                if (count < 0) {
                    // 客户端已经断开连接.
                    key.cancel();
                    sc.close();
                    return;
                }
                // 切换buffer到读状态,内部指针归位.
                temp.flip();
                String msg = Charset.forName("UTF-8").decode(temp).toString();
                System.out.println("Server received ["+msg+"] from client address:" + sc.getRemoteAddress());

                Thread.sleep(1000);
                // echo back.
                sc.write(ByteBuffer.wrap(msg.getBytes(Charset.forName("UTF-8"))));

                // 清空buffer
                temp.clear();
            }
        }

    }
}
