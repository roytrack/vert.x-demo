package com.roytrack.vertx.core;

import io.vertx.core.buffer.Buffer;

/**
 * Created by roytrack on 2018/3/9 23:02.
 */
public class BufferDemo {
    public static void main(String[] args) {
        Buffer buffer=Buffer.buffer("a buffer","UTF-8");
        buffer.appendByte((byte)23).appendInt(5).appendDouble(2.333d).appendString(" one day");
        System.out.println(buffer.length());
        Buffer buffer2=buffer.copy();
        Buffer buffer3=buffer.slice(0,10);
        System.out.println(buffer);
        System.out.println(buffer2);
        System.out.println(buffer3);

    }
}
