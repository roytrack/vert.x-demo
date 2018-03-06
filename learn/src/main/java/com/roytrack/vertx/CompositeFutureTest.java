package com.roytrack.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.net.NetServer;

/***
 *
 *   Created by roytrack on 2018-03-06  16:37
 */
public class CompositeFutureTest extends AbstractVerticle {
    public void start(){
        Future<HttpServer> httpServerFuture= Future.future();
        HttpServer httpServer=vertx.createHttpServer().requestHandler(req ->{
            req.response()
                    .putHeader("content-type","text/plain")
                    .end("Hello world!");
        });
        httpServer.listen(httpServerFuture.completer());
        Future<NetServer> netServerFuture=Future.future();
        NetServer netServer=vertx.createNetServer().connectHandler(handler ->{
            handler.close();
        });
        netServer.listen(netServerFuture.completer());
        CompositeFuture.all(httpServerFuture,netServerFuture).setHandler(var -> {
            if(var.succeeded()){
                System.out.println("double server started!");
            }else {
                System.out.println("something error occured "+var.cause());

            }
        });
    }
}
