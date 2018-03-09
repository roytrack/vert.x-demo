package com.roytrack.vertx.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.EventBusOptions;
import io.vertx.core.http.ClientAuth;
import io.vertx.core.net.JksOptions;

/***
 *
 *   Created by roytrack on 2018-03-09  15:41
 */
public class EventBusClusterTest extends AbstractVerticle {
    public void start(){
        VertxOptions vertxOptions=new VertxOptions();
        vertxOptions.setEventBusOptions(new EventBusOptions()
       .setClusterPublicHost("roytrack")
        .setClusterPublicPort(1234)
                //this 1235 opened with hazelcast  and default 5701 opened too
        .setPort(1235));
        Vertx.clusteredVertx(vertxOptions, res ->{
            if(res.succeeded()){
                Vertx vertx=res.result();
                EventBus eventBus1=vertx.eventBus();
                System.out.println("We now have a clustered event bus "+eventBus1);
            }else {
                System.out.println("Failed : "+res.cause());
            }
        });

    }
}
