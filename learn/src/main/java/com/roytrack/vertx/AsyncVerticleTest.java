package com.roytrack.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

import java.util.Random;

/***
 *
 *   Created by roytrack on 2018-03-06  17:12
 */
public class AsyncVerticleTest extends AbstractVerticle {

    public void start(Future<Void> startFuture){
        vertx.deployVerticle("com.roytrack.vertx.MyFirstVerticle",res ->{
            if(res.succeeded()){
                startFuture.complete();
            }else{
                startFuture.fail(res.cause());
            }
        });
    }

    public void stop(Future<Void> stopFuture){
        Random r=new Random();
        int rResult=r.nextInt(10);
        if((rResult&1)==0){
            stopFuture.succeeded();
        }else{
            stopFuture.fail("just fail");
        }
    }
}
