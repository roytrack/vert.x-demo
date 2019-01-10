package com.roytrack.vertx.jdbc.client;

import java.util.List;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;

public class JDBCHandleDemo {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    JsonObject mySQLClientConfig = new JsonObject()
        .put("provider_class", "io.vertx.ext.jdbc.spi.impl.HikariCPDataSourceProvider")
        //.put("dataSourceClassName","")
        .put("jdbcUrl", "jdbc:mysql://127.0.0.1:3306/wp?useSSL=false")
        .put("username", "root")
        .put("password", "123456")
        .put("driverClassName", "com.mysql.jdbc.Driver")
        .put("maximumPoolSize", 20)
        .put("maxConnectionRetries", -1);
    SQLClient client = JDBCClient.createShared(vertx, mySQLClientConfig);
    client.getConnection(v -> {
      SQLConnection connection = v.result();
      connection.query("select * from t_order limit 10", event -> {
        System.out.println("-----------------------------------");
        List<JsonArray> result = event.result().getResults();
        result.forEach(System.out::println);
      });
    });
  }
}
