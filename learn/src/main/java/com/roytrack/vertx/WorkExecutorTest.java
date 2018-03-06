package com.roytrack.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.WorkerExecutor;

import java.util.UUID;

/***
 *
 *   Created by roytrack on 2018-03-06  16:17
 */
public class WorkExecutorTest extends AbstractVerticle {
    public void start(){
       WorkerExecutor executor=vertx.createSharedWorkerExecutor("my-worker-pool");
       executor.executeBlocking(future ->{
           String result= UUID.randomUUID().toString();
           future.complete(result);
       },res -> System.out.println("The result is "+res.result())
       );
       executor.close();
    }
}
