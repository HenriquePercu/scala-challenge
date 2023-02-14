package com.challenge

import cats.effect.{IO, IOApp}
import com.challenge.EmailFormatter.breakEmailTextIntoLines
import com.challenge.FileWriter.saveTextIntoFile
import fs2.io.file.{Files, Path}

object FileReader extends IOApp.Simple {

  private val fileStream = Files[IO].readUtf8(Path("src/main/resources/email.txt"))
    .map(text => breakEmailTextIntoLines(text, 40).mkString("\n"))
    .through(saveTextIntoFile)

  override def run: IO[Unit] = fileStream.compile.drain
}