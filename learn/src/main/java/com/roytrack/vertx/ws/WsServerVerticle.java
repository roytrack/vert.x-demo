package com.roytrack.vertx.ws;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServerOptions;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class WsServerVerticle extends AbstractVerticle {

  @Getter
  @Setter
  private String host;
  @Getter
  @Setter
  private int port;

  public WsServerVerticle(String host, int port) {
    this.host = host;
    this.port = port;
  }

  @Override
  public void start() {
    try {
      HttpServerOptions opts = new HttpServerOptions()
              .setTcpFastOpen(true)
              .setTcpKeepAlive(true)
              .setLogActivity(true)
              .setHost(host)
              .setPort(port)
              //支持可压缩
              //.setCompressionSupported(true)
              ;

      vertx.createHttpServer(opts)
              .websocketHandler(conn -> {
                conn.textMessageHandler(h -> {
                  log.info("receive text message :{}", h);
                });
//                vertx.setPeriodic(1000, lh -> {
//                  conn.writePing(Buffer.buffer("777".getBytes()));
//                });
              })
              .listen();
    } catch (Throwable e) {
      log.error("server error:", e);
    }

  }

  public static void main(String[] args) {
    System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.SLF4JLogDelegateFactory");
    String theHost="localhost";
    int thePort = 10617;
    WsServerVerticle serverVerticle = new WsServerVerticle(theHost,thePort);
    WsClientVerticle clientVerticle = new WsClientVerticle(theHost,thePort);

    VertxOptions options=new VertxOptions().setBlockedThreadCheckInterval(10_000L).setWarningExceptionTime(9_000L)
            .setMaxEventLoopExecuteTime(30L * 1000 * 1000000);
    Vertx vertx = Vertx.vertx(options);

    vertx.deployVerticle(serverVerticle, v -> {
      if (v.succeeded()) {
        vertx.deployVerticle(clientVerticle);
      }
    });
    vertx.exceptionHandler(t -> {
      log.error("vertx error :", t);
    });

  }
}
