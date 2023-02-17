package com.challenge

import cats.effect.IO
import fs2.io.file.{Files, Path}
import fs2.{Stream, text}

object FileWriter {

  def saveTextIntoFile(email: Stream[IO, String]):  Stream[IO, Unit] = {
    email
      .through(text.utf8.encode)
      .through(Files[IO].writeAll(Path("src/main/resources/emailFormatted.txt")))
  }

  def saveTextIntoFile(email: String): Stream[IO, Unit] = {
    saveTextIntoFile(Stream.emit(email))
  }

}
