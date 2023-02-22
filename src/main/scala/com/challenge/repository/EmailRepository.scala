package com.challenge.repository

import cats.effect.IO
import com.challenge.model.EmailDTO
import doobie._
import doobie.implicits._
import fs2.Stream

class EmailRepository(transactor: Transactor[IO]) {
  def findAllEmails: Stream[IO, EmailDTO] = {
    val findAllQuery: Query0[EmailDTO] = sql"select * from email".query[EmailDTO]
    findAllQuery.stream.transact(transactor)
  }

  def saveFormattedEmail(emailDTO: EmailDTO): IO[EmailDTO] = {
    val saveQuery = sql"INSERT INTO email (email, formatted_email) VALUES (${emailDTO.email} , ${emailDTO.formattedEmail})"
      .update.withUniqueGeneratedKeys[Int]("id")

    saveQuery.transact(transactor).map(generatedId => emailDTO.copy(id = generatedId))
  }
}
