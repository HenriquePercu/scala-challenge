package com.challenge

import org.scalatest.funsuite.AnyFunSuite
import com.challenge.EmailFormatter.breakEmailTextIntoLines

class EmailFormatterTest extends AnyFunSuite {

  test("Default long text") {
    assert(breakEmailTextIntoLines(

      "In 1991, while studying computer science at University of Helsinki, Linus Torvalds began a project that later became the Linux kernel. He wrote the program specifically for the hardware he was using and independent of an operating system because he wanted to use the functions of his new PC with an 80386 processor. Development was done on MINIX using the GNU C Compiler."
      , 40) == List(
      "In 1991, while studying computer science",
      "at University of Helsinki, Linus",
      "Torvalds began a project that later",
      "became the Linux kernel. He wrote the",
      "program specifically for the hardware he",
      "was using and independent of an",
      "operating system because he wanted to",
      "use the functions of his new PC with an",
      "80386 processor. Development was done on",
      "MINIX using the GNU C Compiler.")
    )
  }

  test("Text without escape line") {
    assert(breakEmailTextIntoLines("Short text"
      , 50) == List("Short text"))
  }

  test("Text with one scape line") {
    assert(breakEmailTextIntoLines("Short text"
      , 3) == List("Short", "text"))
  }

  test("Text with two scape lines") {
    assert(breakEmailTextIntoLines("Short text ahead"
      , 3) == List("Short", "text", "ahead"))
  }

  test("Blank Text") {
    assert(breakEmailTextIntoLines(""
      , 3) == List(""))
  }

  test("One word with limit less than word length") {
    assert(breakEmailTextIntoLines("Word"
      , 3) == List("Word"))
  }

  test("Two words with limit more than word length") {
    assert(breakEmailTextIntoLines("Nice Word"
      , 7) == List("Nice", "Word"))
  }

}
