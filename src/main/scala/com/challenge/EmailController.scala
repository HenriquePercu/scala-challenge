package com.challenge

import cats.effect._
import com.challenge.model.{EmailDTO, EmailFormatRequest}
import com.challenge.repository.EmailRepository
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl._

class EmailController(emailRepository: EmailRepository) extends Http4sDsl[IO] {
  private implicit val emailDecoder: EntityDecoder[IO, EmailFormatRequest] = jsonOf[IO, EmailFormatRequest]

  val routes = HttpRoutes.of[IO] {
    case GET -> Root / "email" =>
      Ok(emailRepository.findAllEmails.map(_.asJson))

    case req@POST -> Root / "email" / "format" =>
      for {
        emailRequest <- req.as[EmailFormatRequest]
        createdEmailDTO <- emailRepository.saveFormattedEmail(
          new EmailDTO(emailRequest.email, EmailFormatter.breakEmailTextIntoLines(emailRequest.email, emailRequest.lineSize).mkString("\n")))
        response <- Created(createdEmailDTO.asJson)
      } yield response

  }

}
