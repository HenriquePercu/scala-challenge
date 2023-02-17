package com.challenge

import cats.effect._
import cats.implicits._
import com.challenge.FileWriter.saveTextIntoFile
import com.challenge.model.{EmailFormatRequest, EmailFormatResponse}
import fs2.Stream
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl._
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder

object EmailController extends IOApp {

  private def formatRoutes[F[_] : Concurrent]: HttpRoutes[F] = {
    val dsl = Http4sDsl[F]
    import dsl._

    implicit val emailDecoder: EntityDecoder[F, EmailFormatRequest] = jsonOf[F, EmailFormatRequest]

    HttpRoutes.of[F] {
      case req@POST -> Root / "email" / "format" =>
        for {
          emailRequest <- req.as[EmailFormatRequest]
          response <- {
            val formattedEmail = EmailFormatter.breakEmailTextIntoLines(emailRequest.email, emailRequest.lineSize).mkString("\n")
            saveTextIntoFile(formattedEmail).compile.drain
            Ok(EmailFormatResponse(formattedEmail).asJson)
          }
        } yield response
    }
  }

  private def routes[F[_] : Concurrent]: HttpApp[F] = {
    formatRoutes[F].orNotFound
  }

  override def run(args: List[String]): IO[ExitCode] = {
    BlazeServerBuilder[IO](runtime.compute)
      .bindHttp(8080, "localhost")
      .withHttpApp(routes)
      .resource
      .use(_ => IO.never)
      .as(ExitCode.Success)
  }

}
