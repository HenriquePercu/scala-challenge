package com.challenge.config

import cats.effect.{IO, Resource}
import com.typesafe.config.ConfigFactory
import pureconfig.ConfigSource
import pureconfig.generic.auto._
import pureconfig.module.catseffect.syntax._

package object Configuration {
  case class ServerConfig(host: String, port: Int)

  case class DatabaseConfig(driver: String, url: String, user: String, password: String, threadPoolSize: Int)

  case class Config(server: ServerConfig, database: DatabaseConfig)

  object Config {
    def load(configFile: String = "application.conf"): Resource[IO, Config] = {
      Resource.eval(ConfigSource.fromConfig(ConfigFactory.load(configFile)).loadF[IO, Config]())
    }
  }

}
