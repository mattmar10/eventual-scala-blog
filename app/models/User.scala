package models

import play.api.libs.json.Json
import play.api.libs.functional.syntax.functionalCanBuildApplicative
import play.api.libs.functional.syntax.toFunctionalBuilderOps
import reactivemongo.bson._
import reactivemongo.bson.Producer.nameValue2Producer
import play.modules.reactivemongo.json.BSONFormats.BSONObjectIDFormat

/*
 * Author: Sari Haj Hussein
 */

case class User(id: Option[BSONObjectID], firstName: String, lastName: String, email: String)

object User {
  /** serialize/deserialize a Name into/from JSON value */
  implicit val userFormat = Json.format[User]

  /** serialize a User into a BSON */
  implicit object UserBSONWriter extends BSONDocumentWriter[User] {
    def write(user: User): BSONDocument =
      BSONDocument(
        "_id" -> user.id.getOrElse(BSONObjectID.generate),
        "firstName" -> user.firstName,
        "lastName" -> user.lastName,
        "email" -> user.email
      )
  }

  /** deserialize a User from a BSON */
  implicit object UserBSONReader extends BSONDocumentReader[User] {
    def read(doc: BSONDocument): User =
      User(
        doc.getAs[BSONObjectID]("_id"),
        doc.getAs[String]("firstName").get,
        doc.getAs[String]("lastName").get,
        doc.getAs[String]("email").get
      )
  }
}