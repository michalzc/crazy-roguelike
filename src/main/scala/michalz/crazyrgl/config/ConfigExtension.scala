package michalz.crazyrgl.config

import akka.actor.{ExtendedActorSystem, Extension, ExtensionId, ExtensionIdProvider}
import com.typesafe.config.Config

class ConfigExtensionImpl(config: Config) extends Extension {
  val crazyRglCfg = new CrazyRglCfg(config.getConfig("crazy-rgl"))
}

class CrazyRglCfg(config: Config) {
  val guiConfig = new GuiConfig(config.getConfig("gui"))
}

class GuiConfig(config: Config) {
  val windowWidth = config.getInt("window.width")
  val windowHeight = config.getInt("window.height")
}

object ConfigExtension extends ExtensionId[ConfigExtensionImpl] with ExtensionIdProvider {
  override def createExtension(system: ExtendedActorSystem): ConfigExtensionImpl = new ConfigExtensionImpl(system.settings.config)

  override def lookup(): ExtensionId[_ <: Extension] = ConfigExtension
}
