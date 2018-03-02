package com.roytrack.vertx;

import io.vertx.core.AbstractVerticle;

/***
 *
 *   Created by roytrack on 2018-03-02  14:34
 */
public class MyFirstVerticle extends AbstractVerticle {

    public void start(){
        vertx.createHttpServer().requestHandler(req ->{
            req.response()
                    .putHeader("content-type","text/plain")
                    .end("Hello world!");

        }).listen(8080);
    }
}
