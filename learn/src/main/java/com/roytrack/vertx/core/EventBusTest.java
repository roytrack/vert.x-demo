package com.roytrack.vertx.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;

import java.util.Iterator;
import java.util.Map;

/***
 *
 *   Created by roytrack on 2018-03-08  15:06
 */
public class EventBusTest extends AbstractVerticle {
    public void start(){
        EventBus eventBus=vertx.eventBus();
        DeliveryOptions options=new DeliveryOptions();
        options.addHeader("some-header","some-value");
        MessageConsumer<String> consumer=eventBus.consumer("news.uk.sport", message->{
            System.out.println("I have received a message: "+message.body()+"| message header |");
            MultiMap headers=message.headers();
            if(null!=headers){
                Iterator<Map.Entry<String, String>> itr=  headers.iterator();
                while (itr.hasNext()){
                    Map.Entry<String, String> entry=itr.next();
                    System.out.println("key:"+entry.getKey()+"  value:"+entry.getValue());
                }
            }
            message.reply("this msg received hey hey hey");
        });
        consumer.completionHandler(res ->{
            if(res.succeeded()){
                System.out.println("The handler registration has reached all nodes");
            }else{
                System.out.println("Registration failed!");
            }
        });

        eventBus.publish("news.uk.sport","Hey! This is a message published!");
        eventBus.send("news.uk.sport","Hey! send message!",options,ar->{
            if(ar.succeeded()){
                System.out.println("message reply is "+ar.result().body());
            }
        });

        //consumer unregister
//        consumer.unregister(res ->{
//            if(res.succeeded()){
//                System.out.println("The handler un-registration has reached all nodes");
//            }else{
//                System.out.println("Un-registration failed!");
//            }
//        });


    }

}
