package com.doudou;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class NioClient {
    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        socketChannel.connect(new InetSocketAddress("127.0.0.1",8888));
        Thread writeThread = new Thread(()->{
            try {
                while ( !socketChannel.isConnected()){
                    //  如果连接还没完成,则完成连接
                    socketChannel.finishConnect();
                }
                for(int i = 1;i<=30;i++){
                    socketChannel.write(ByteBuffer.wrap(("hello"+i).getBytes()));
                }
                socketChannel.write(ByteBuffer.wrap("quit".getBytes()));
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        writeThread.start();

        while(true){
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();
            while(it.hasNext()){
                SelectionKey key = it.next();
                it.remove();
                if (key.isConnectable()){
                    if (! socketChannel.isConnected()){
                        //  如果连接还没完成,则完成连接
                        socketChannel.finishConnect();
                    }
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }
                if (key.isReadable()){
                    ByteBuffer buffer = ByteBuffer.allocate(20);
                    socketChannel.read(buffer);
                    buffer.flip();
                    String str = Charset.defaultCharset().decode(buffer).toString();
                    if ("quit".equals(str)){
                        socketChannel.close();
                        selector.close();
                        return;
                    }
                    System.out.println(str);
                }
            }
        }
    }
}
