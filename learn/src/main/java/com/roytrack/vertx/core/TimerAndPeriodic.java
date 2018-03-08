package com.roytrack.vertx.core;

import io.vertx.core.AbstractVerticle;

/***
 *
 *   Created by roytrack on 2018-03-07  12:03
 */
public class TimerAndPeriodic extends AbstractVerticle {
    static int i=0;
    public void start(){
        Long timerId=vertx.setTimer(2000,id -> System.out.println("Two second later this is printed"));
        System.out.println("this line print first, the timerID is "+timerId);

       Long periodicId=vertx.setPeriodic(2000, id -> {
            System.out.println("This line printed every two second and i is"+i +"  and the id is "+id);
            i++;
            if(i>5){
                vertx.cancelTimer(id);
                System.out.println("Try to cancel timer,let's see if it works");
            }
        });

        //not work


        System.out.println("thie line after periodic but show before it ,periodicId is "+periodicId);



    }
}
