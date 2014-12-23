package ar.com.gmvsoft.http

import akka.actor.ActorSystem
import akka.http.Http
import akka.stream.FlowMaterializer

object Server extends App {

  val host = "localhost"
  val port = 8080

  implicit val system = ActorSystem("akka-http-demo-system")
  implicit val fm = FlowMaterializer()

  println("Starting akka-http server at " + host + ":" + port)

  val serverBinding = Http(system).bind(interface = host, port = port)

  serverBinding.connections.foreach { connection =>
    println("Accepted new connection from: " + connection.remoteAddress)

    connection handleWithSyncHandler RequestHandler().handler
  }

}
