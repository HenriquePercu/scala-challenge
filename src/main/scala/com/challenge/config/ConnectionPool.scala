package com.challenge.config

import cats.effect._
import com.challenge.config.Configuration.DatabaseConfig
import doobie.hikari.HikariTransactor

import scala.concurrent.ExecutionContext

object ConnectionPool {

  def transactor(config: DatabaseConfig, executionContext: ExecutionContext): Resource[IO, HikariTransactor[IO]] = {
    HikariTransactor.newHikariTransactor[IO](
      config.driver,
      config.url,
      config.user,
      config.password,
      executionContext
    )
  }

}
