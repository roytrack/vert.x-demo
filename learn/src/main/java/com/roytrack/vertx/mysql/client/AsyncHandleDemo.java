package com.roytrack.vertx.mysql.client;

import java.util.List;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.asyncsql.MySQLClient;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;

public class AsyncHandleDemo {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    JsonObject mySQLClientConfig = new JsonObject()
        .put("host", "127.0.0.1")
        .put("port", 3306)
        .put("username", "root")
        .put("password", "123456")
        .put("database", "wp")
        .put("maxConnectionRetries", -1);
    SQLClient mySQLClient = MySQLClient.createShared(vertx, mySQLClientConfig);
    mySQLClient.getConnection(v -> {
      SQLConnection connection = v.result();
      connection.query("select * from t_order limit 10", event -> {
        System.out.println("-----------------------------------");
        List<JsonArray> result = event.result().getResults();
        result.forEach(System.out::println);
      });
    });
  }
}
