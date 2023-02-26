package com.challenge

import cats.effect.{ExitCode, IO, IOApp, Resource}
import com.challenge.config.Configuration.Config
import com.challenge.config.ConnectionPool
import com.challenge.repository.EmailRepository
import doobie.hikari.HikariTransactor
import doobie.util.ExecutionContexts
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder

import scala.concurrent.ExecutionContext.global

object ServerApp extends IOApp{

  def run(args: List[String]): IO[ExitCode] = {
    createServer()
  }

  def createServer(configFile: String = "application.conf"): IO[ExitCode] = {
    resources(configFile).use(createServer)
  }

  private def resources(configFile: String): Resource[IO, Resources] = {
    for {
      config <- Config.load(configFile)
      ec <- ExecutionContexts.fixedThreadPool[IO](config.database.threadPoolSize)
      transactor <- ConnectionPool.transactor(config.database, ec)
    } yield Resources(transactor, config)
  }

  private def createServer(resources: Resources): IO[ExitCode] = {
    for {
      exitCode <- BlazeServerBuilder[IO](global)
        .bindHttp(resources.config.server.port, resources.config.server.host)
        .withHttpApp(new EmailController(new EmailRepository(resources.transactor)).routes.orNotFound).serve.compile.lastOrError
    } yield exitCode
  }

  case class Resources(transactor: HikariTransactor[IO], config: Config)

}
