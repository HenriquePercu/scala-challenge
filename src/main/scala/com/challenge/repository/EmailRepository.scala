package com.challenge.repository

import cats.effect.IO
import com.challenge.model.Email
import doobie._
import doobie.implicits._
import fs2.Stream

class EmailRepository(transactor: Transactor[IO]) {
  def findAllEmails: Stream[IO, Email] = {
    val findAllQuery: Query0[Email] = sql"select * from email".query[Email]
    findAllQuery.stream.transact(transactor)
  }

  def saveFormattedEmail(emailDTO: Email): IO[Email] = {
    val saveQuery = sql"INSERT INTO email (email, formatted_email) VALUES (${emailDTO.email} , ${emailDTO.formattedEmail})"
      .update.withUniqueGeneratedKeys[Int]("id")

    saveQuery.transact(transactor).map(generatedId => emailDTO.copy(id = generatedId))
  }
}
