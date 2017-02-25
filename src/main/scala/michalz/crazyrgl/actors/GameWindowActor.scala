package michalz.crazyrgl.actors

import akka.actor.{Actor, ActorLogging, Props}
import michalz.crazyrgl.actors.CrazyRglActor.StopRequest
import michalz.crazyrgl.actors.GameWindowActor.WindowClosed
import michalz.crazyrgl.config.ConfigExtension
import michalz.crazyrgl.gui.{GraphicState, MainWindow}

class GameWindowActor extends Actor with ActorLogging {

  var state: Option[WindowState] = None

  def receive: Receive = {

    case WindowClosed =>
      log.debug("Window closed")
      context.parent ! StopRequest
  }

  override def preStart: Unit = {
    val cfg = ConfigExtension(context.system)
    val graphicState = new GraphicState
    val window = new MainWindow(cfg.crazyRglCfg.guiConfig, self)
    window.init()

    state = Some(WindowState(window, graphicState))
  }

  override def postStop: Unit = {
    log.info("Closing window")
    state.foreach(_.cleanUp())
    state = None
  }

}

object GameWindowActor {
  def props: Props = Props(classOf[GameWindowActor])

  case object WindowClosed
}

case class WindowState(
  window:    MainWindow,
  graphicState: GraphicState
) {
  def cleanUp(): Unit = {
    window.closeWindow()
  }
}