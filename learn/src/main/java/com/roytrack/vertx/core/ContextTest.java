package com.roytrack.vertx.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;

/***
 *
 *   Created by roytrack on 2018-03-07  11:39
 */
public class ContextTest extends AbstractVerticle {
    public void start(){
        Context context=vertx.getOrCreateContext();
        if(context.isEventLoopContext()){
            System.out.println("This context is an eventLoopContext");
        }else if( context.isWorkerContext()){
            System.out.println("This context is a workerContext");
        }else if(context.isMultiThreadedWorkerContext()){
            System.out.println("This context is a multiThreadedWorkerContext");
        }else if (!Context.isOnVertxThread()){
            System.out.println("Context not attached to a thread managed by vert.x");
        }

        context.runOnContext(v ->{
            System.out.println("This will be executed asynchronously in the same context");
        });

        context.put("data","hello");
        context.runOnContext(v ->{
            String hello=context.get("data");
            System.out.println("get data prop from context is @"+hello+"@");
        });


    }
}
