package com.roytrack.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;
import io.vertx.core.http.HttpServer;
import io.vertx.core.impl.VertxImpl;
import io.vertx.core.impl.VertxInternal;
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
        //都成功才成功 任一失败即返回结果
        CompositeFuture.all(httpServerFuture,netServerFuture).setHandler(var -> {
            if(var.succeeded()){
                System.out.println("double server started!");
            }else {
                System.out.println("something error occured "+var.cause());

            }
        });

        //任意一个成功即成功
        CompositeFuture.any(httpServerFuture,netServerFuture).setHandler(var -> {
            if(var.succeeded()){
                System.out.println("double server started!");
            }else {
                System.out.println("something error occured "+var.cause());

            }
        });

        //都成功才成功 如果有一个失败即失败 等都执行完毕后统一返回结果
        CompositeFuture.join(httpServerFuture,netServerFuture).setHandler(var -> {
            if(var.succeeded()){
                System.out.println("double server started!");
            }else {
                System.out.println("something error occured "+var.cause());

            }
        });

        FileSystem fs=vertx.fileSystem();
        Future<Void> startFuture=Future.future();

        Future<Void> fut1=Future.future();
        fs.createFile("/foo",fut1.completer());
        fut1.compose(v ->{
           Future<Void> fut2=Future.future();
           fs.writeFile("/foo", Buffer.buffer(),fut2.completer());
           return fut2;
        }).compose(v ->{
            fs.move("/foo","/bar",startFuture.completer());
        },startFuture);
        vertx.fileSystem().props("/foo",v ->{
            System.out.println(v.result());

        });


    }
}
