package com.doudou;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class NioServer {
    public static void main(String[] args) throws Exception{
        //  打开服务连接通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //  监听8888端口
        serverSocketChannel.bind(new InetSocketAddress(8888));
        //  设置非阻塞
        serverSocketChannel.configureBlocking(false);
        //  开启一个选择器
        Selector selector = Selector.open();
        //  将通道注册到选择器上
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true){
            //  查询key
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //  遍历key集合
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                //  当有客户端连接时的响应
                if (selectionKey.isAcceptable()){
                    acceptHandle(selectionKey);
                }
                //  当有读取数据操作时
                if (selectionKey.isReadable()){
                    readHandle(selectionKey);
                }
            }
        }
    }

    //  接收连接处理器
    static void acceptHandle(SelectionKey key) throws IOException {
        //  获取连接通道
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
        //  获取传输数据通道
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        //  将传输数据通道注册到选择器上
        socketChannel.register(key.selector(),SelectionKey.OP_CONNECT);
    }

    //  读操作处理器
    static void readHandle(SelectionKey key) throws IOException{
        //  初始化缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(20);
        //  获取传输数据通道
        SocketChannel socketChannel = (SocketChannel)key.channel();
        //  读取数据
        socketChannel.read(buffer);
        //  缓冲区由写操作转为读操作
        buffer.flip();
        //  将缓冲区的数据由字节转为字符串
        String bufferData = Charset.defaultCharset().decode(buffer).toString();
        System.out.println(bufferData);
        //  获取缓冲区的数据，有退出标志，就给客户端发一个退出标志，并关闭通道
        if ("quit".equals(bufferData)){
            socketChannel.write(ByteBuffer.wrap("quit".getBytes()));
            socketChannel.close();
        }
        //  没有退出标志，就书写业务逻辑
        String upperCaseBufferData = bufferData.toUpperCase();
        socketChannel.write(ByteBuffer.wrap(upperCaseBufferData.getBytes()));
    }
}
