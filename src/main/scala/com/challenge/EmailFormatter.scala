package com.challenge

object EmailFormatter {

  private val SpaceSizeConstant = 1

  def breakEmailTextIntoLines(emailText: String, quantityLetters: Int): List[String] = {

    emailText.split(" ").foldLeft(List[String](""))((lines, word) => {
      if (!lines.head.isBlank && word.length + lines.last.length + SpaceSizeConstant > quantityLetters) {
        lines.appended(word)
      } else {
        val composedWord = if (lines.last.isBlank) word else " " + word
        lines.updated(lines.length - 1, lines.last.appended(composedWord).mkString)
      }
    })
  }

}
