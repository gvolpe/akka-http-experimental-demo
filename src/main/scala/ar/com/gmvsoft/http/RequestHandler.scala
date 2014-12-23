package ar.com.gmvsoft.http

import akka.http.model._
import akka.http.model.HttpMethods._

object RequestHandler {

  def apply() = new RequestHandler

}

class RequestHandler {

  val resourcesHtml = "<ul><li><b>/ping</b> --> Pong Response</li>" +
    "<li><b>/crash</b> --> Http 500 Response</li>" +
    "<li><b>/*</b> --> Http 404 Response</li></ul>"
  val homeHtml = "<html><body><h1>Akka Http Server Demo</h1><h2>Resources</h2></body>" + resourcesHtml + "</html>"

  val handler: HttpRequest => HttpResponse = {
    case HttpRequest(GET, Uri.Path("/"), _, _, _) =>
      HttpResponse(entity = HttpEntity(MediaTypes.`text/html`, homeHtml))
    case HttpRequest(GET, Uri.Path("/ping"), _, _, _) => HttpResponse(entity = "PONG!")
    case HttpRequest(GET, Uri.Path("/crash"), _, _, _) => sys.error("BOOM!")
    case _: HttpRequest => HttpResponse(404, entity = "Unknown resource!")
    //case _: HttpRequest => HttpResponse(NotFound)
  }

}
