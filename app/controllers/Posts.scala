package controllers

import controllers.Celebrities._
import models.Celebrity
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import reactivemongo.bson.BSONDocument

/**
 * Created by matt on 10/11/14.
 */
object Posts extends Controller{

  /** list all posts */
  def index = Action { implicit request =>
    Async {
      //val cursor = collection.find(
      //  BSONDocument(), BSONDocument()).cursor[Celebrity] // get all the fields of all the celebrities
      //val futureList = cursor.toList // convert it to a list of Celebrity
      //futureList.map { celebrities => Ok(Json.toJson(celebrities)) } // convert it to a JSON and return

      val futureInt = scala.concurrent.Future { 7*7 }
      futureInt.map(i => Ok("Got result: " + i))
    }
  }
}
