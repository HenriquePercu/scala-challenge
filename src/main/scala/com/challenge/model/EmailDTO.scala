package com.challenge.model

case class EmailDTO(id: Int, email: String, formattedEmail: String) {
  def this(email: String, formattedEmail: String) = this(0, email, formattedEmail)
}
