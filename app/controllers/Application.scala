package controllers

import play.api._
import play.api.mvc._
import play.api.libs.iteratee.{Enumerator, Iteratee}
import play.api.libs.json.{Json, JsValue}

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def line = WebSocket.using[JsValue] { request =>
    val in = Iteratee.foreach[JsValue]{ event =>
      println((event \ "message").as[String])
    }.mapDone { _ =>
      println("Disconnected")
    }

    val out = Enumerator[JsValue](Json.toJson(
      Map(
        "response" -> Json.toJson("Hello")
      )
    ))

    (in, out)
  }
}