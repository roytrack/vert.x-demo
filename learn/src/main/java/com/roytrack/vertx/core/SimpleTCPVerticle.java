package com.roytrack.vertx.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.net.NetServer;

/**
 * Created by roytrack on 2018/3/10 10:13.
 */
public class SimpleTCPVerticle extends AbstractVerticle {
    public void start(){
        NetServer server=vertx.createNetServer();
        server.connectHandler(netSocket -> {
            netSocket.handler(buffer -> {
                String tmp=buffer.toString();
                System.out.println("Received message :" + tmp);
                System.out.println("localAddress host is " + netSocket.localAddress().host() + "  localAddress port is " + netSocket.localAddress().port()
                        + " localAddress path is " + netSocket.localAddress().path());
                System.out.println("remoteAddress host is "+netSocket.remoteAddress().host()+"  remoteAddress port is "+netSocket.remoteAddress().port()
                        +" remoteAddress path is "+netSocket.remoteAddress().path());
                netSocket.write(tmp);
            })
                    .closeHandler(v -> {
                        System.out.println("The socket has been closed");
                    })
                    .exceptionHandler(throwable -> {
                        System.out.println("Error occured : " + throwable.getMessage());
                    });
        })
                .listen(0, "localhost", netServerAsyncResult -> {
                    if (netServerAsyncResult.succeeded()) {
                        System.out.println("Server is now listening!");
                        System.out.println("listening port is " + server.actualPort());
                    } else {
                        System.out.println("Failed to bind!");
                    }
                });


    }
}
