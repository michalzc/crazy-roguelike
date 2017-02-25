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

  it should "contains proper window config" in {
    val cfg = ConfigExtension(system)
    val windowCfg = cfg.crazyRglCfg.guiConfig.windowConfig

    windowCfg shouldBe a[WindowConfig]
    windowCfg.width shouldBe 800
    windowCfg.height shouldBe 600
    windowCfg.title shouldBe "Crazy Roguelike"
  }
}
