package com.challenge

import cats.effect.{IO, IOApp}
import fs2.text
import fs2.io.file.{Files, Path}
import EmailFormatter.breakEmailTextIntoLines

object FileReader extends IOApp.Simple {

  private val fileStream = Files[IO].readUtf8(Path("src/main/resources/email.txt"))
    .map(text => breakEmailTextIntoLines(text, 40).mkString("\n"))
    .through(text.utf8.encode)
    .through(Files[IO].writeAll(Path("src/main/resources/emailFormatted.txt")))

  override def run: IO[Unit] = fileStream.compile.drain
}