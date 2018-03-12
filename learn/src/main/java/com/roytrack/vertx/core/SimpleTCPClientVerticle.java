package com.roytrack.vertx.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;

/***
 *
 *   Created by roytrack on 2018-03-12  15:40
 */
public class SimpleTCPClientVerticle extends AbstractVerticle {
    public void start(){
        NetClientOptions options=new NetClientOptions().setConnectTimeout(10000);
        options.setReconnectInterval(500).setReconnectAttempts(10).setLogActivity(true);
        NetClient client=vertx.createNetClient(options);
        client.connect(1234,"localhost",res ->{
           if(res.succeeded()){
               System.out.println("Connected!");
               NetSocket socket=res.result();
                socket.write("1122").handler(res1->{
                    System.out.println(res1.toString());
                });
           }else{
               System.out.println("Failed to connect :"+res.cause().getMessage());
           }
        });
    }
}
