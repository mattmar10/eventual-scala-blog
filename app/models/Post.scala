package models

import play.api.libs.json.Json
import reactivemongo.bson.{BSONDocumentReader, BSONDocument, BSONDocumentWriter, BSONObjectID}


case class Post(id: Option[BSONObjectID], title: String, author: User, contents: String)

/**
 * Created by matt on 10/11/14.
 */
object Post {
  /** serialize/Deserialize a Celebrity into/from JSON value */
  implicit val postFormat = Json.format[Post]

  /** serialize a Celebrity into a BSON */
  implicit object CelebrityBSONWriter extends BSONDocumentWriter[Celebrity] {
    def write(celebrity: Celebrity): BSONDocument =
      BSONDocument(
        "_id" -> celebrity.id.getOrElse(BSONObjectID.generate),
        "name" -> celebrity.name,
        "website" -> celebrity.website,
        "bio" -> celebrity.bio)
  }

  /** deserialize a Celebrity from a BSON */
  implicit object CelebrityBSONReader extends BSONDocumentReader[Celebrity] {
    def read(doc: BSONDocument): Celebrity =
      Celebrity(
        doc.getAs[BSONObjectID]("_id"),
        doc.getAs[Name]("name").get,
        doc.getAs[String]("website").get,
        doc.getAs[String]("bio").get)
  }
}
