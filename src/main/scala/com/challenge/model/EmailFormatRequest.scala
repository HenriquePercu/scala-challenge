package com.challenge.model

import cats.effect.IO
import io.circe.generic.auto._
import org.http4s.EntityDecoder
import org.http4s.circe.jsonOf

case class EmailFormatRequest(email: String, lineSize: Int)

object EmailFormatRequest {
  implicit val emailDecoder: EntityDecoder[IO, EmailFormatRequest] = jsonOf[IO, EmailFormatRequest]
}
