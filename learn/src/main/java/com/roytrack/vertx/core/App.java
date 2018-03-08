package com.roytrack.vertx.core;

import io.vertx.core.Vertx;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Vertx vertx=Vertx.vertx();
        vertx.deployVerticle(CompositeFutureTest.class.getName());
    }
}
