package com.roytrack.vertx.ws;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class WsClientVerticle extends AbstractVerticle {

  @Getter
  @Setter
  private String host;
  @Getter
  @Setter
  private int port;

  public WsClientVerticle(String host, int port) {
    this.host = host;
    this.port = port;
  }

  @Override
  public void start() {

    try {
      HttpClient httpClient = vertx.createHttpClient(
              new HttpClientOptions().setSsl(false)
//                      .setTryUseCompression(true)
                      .setLogActivity(true));
      httpClient.websocket(port, host, "/test", websocket -> {
        websocket.textMessageHandler(h -> {
          log.info("receive data is {}", h);
        });
        websocket.writePing(Buffer.buffer("666".getBytes()));
        websocket.exceptionHandler(e -> {
          log.error("websocket client exception is :", e);
        });

//        websocket.writeTextMessage("43434");
      }, fail -> {
        log.error("httpclient failed :", fail);
      });
    } catch (Throwable e) {
      log.error("client error:", e);
    }

  }
}
