package models

import org.joda.time.DateTime
import play.api.libs.json.Json
import reactivemongo.bson._
import play.modules.reactivemongo.json.BSONFormats.BSONObjectIDFormat


case class Post(
  id: Option[BSONObjectID],
  title: String,
  author: User,
  created: Option[DateTime],
  updated: Option[DateTime],
  contents: String)

/**
 * Created by matt on 10/11/14.
 */
object Post {
  /** serialize/Deserialize a Post into/from JSON value */
  implicit val postFormat = Json.format[Post]

  /** serialize a Post into a BSON */
  implicit object PostBSONWriter extends BSONDocumentWriter[Post] {
    def write(post: Post): BSONDocument = {
      val bson = BSONDocument(
        "_id" -> post.id.getOrElse(BSONObjectID.generate),
        "title" -> post.title,
        "author" -> post.author,
        "contents" -> post.contents,
        "updated" -> BSONDateTime(new DateTime().getMillis)
      )
      if(post.created.isDefined)
         bson ++ ("created" -> BSONDateTime(post.created.get.getMillis))
      else
        bson ++ ("created" -> BSONDateTime(new DateTime().getMillis))

      bson
    }
  }

  /** deserialize a Post from a BSON */
  implicit object PostBSONReader extends BSONDocumentReader[Post] {
    def read(doc: BSONDocument): Post =
      Post(
        doc.getAs[BSONObjectID]("_id"),
        doc.getAs[String]("title").get,
        doc.getAs[User]("author").get,
        doc.getAs[BSONDateTime]("created").map( dt => new DateTime(dt.value)),
        doc.getAs[BSONDateTime]("updated").map( dt => new DateTime(dt.value)),
        doc.getAs[String]("contents").get)
  }


}
