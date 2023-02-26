package com.challenge.model

case class Email(id: Int, email: String, formattedEmail: String) {
  def this(email: String, formattedEmail: String) = this(0, email, formattedEmail)
}
