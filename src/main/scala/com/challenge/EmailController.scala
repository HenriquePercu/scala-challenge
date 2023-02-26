package com.challenge

import cats.effect._
import com.challenge.model.{Email, EmailFormatRequest}
import fs2.Stream
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl._

class EmailController(getAllEmails: Unit => Stream[IO, Email], saveEmail: Email => IO[Email]) extends Http4sDsl[IO] {

  val routes = HttpRoutes.of[IO] {
    case GET -> Root / "email" =>
      Ok(getAllEmails().map(_.asJson))

    case req@POST -> Root / "email" / "format" =>
      for {
        emailRequest <- req.as[EmailFormatRequest]
        createdEmailDTO <- saveEmail(
          new Email(emailRequest.email, EmailFormatter.breakEmailTextIntoLines(emailRequest.email, emailRequest.lineSize).mkString("\n")))
        response <- Created(createdEmailDTO.asJson)
      } yield response

  }

}
