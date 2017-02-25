package michalz.crazyrgl.config

import akka.actor.ActorSystem
import org.scalatest.{FlatSpec, Matchers}

class ConfigExtensionSpec extends FlatSpec with Matchers {
  val system = ActorSystem("test-system")

  behavior of "ConfigExtension"

  it should "be available as akka extension" in {
    val cfg = ConfigExtension(system)
    cfg shouldBe a[ConfigExtensionImpl]
  }

  it should "contains proper gui config" in {
    val cfg = ConfigExtension(system)
    val guiCfg = cfg.crazyRglCfg.guiConfig

    guiCfg shouldBe a[GuiConfig]
    guiCfg.windowWidth shouldBe 800
    guiCfg.windowHeight shouldBe 600
  }
}
