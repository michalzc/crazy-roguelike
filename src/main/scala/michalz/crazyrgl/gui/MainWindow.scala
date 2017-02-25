package michalz.crazyrgl.gui

import java.awt.event.{WindowAdapter, WindowEvent}
import java.util.concurrent.atomic.AtomicReference
import javax.swing.{JFrame, SwingUtilities}

import akka.actor.ActorRef
import michalz.crazyrgl.actors.GameWindowActor.WindowClosed
import michalz.crazyrgl.config.GuiConfig

class MainWindow(config: GuiConfig, parentActor: ActorRef) {


  val graphicState = new AtomicReference[Option[GraphicState]](None)
  val frame = new JFrame(config.windowConfig.title)
  val surface = new Surface(graphicState)
  val timer = new javax.swing.Timer(16, _ => surface.repaint())

  private def closeHandler(): Unit = {
    closeWindow()
    parentActor ! WindowClosed
  }

  def init(): Unit = {
    SwingUtilities.invokeLater { () =>
      initFrame(frame, surface)
      timer.start()
    }
  }

  def updateState(gs: Option[GraphicState]): Unit = {
      graphicState.set(gs)
  }

  def closeWindow(): Unit = {
    SwingUtilities.invokeLater { () =>
      timer.stop()
      frame.setVisible(false)
      frame.dispose()
    }
  }

  private def initFrame(frame: JFrame, surface: Surface): Unit = {
    val winCfg = config.windowConfig

    frame.setSize(winCfg.width, winCfg.height)

    frame.add(surface)
    frame.setVisible(true)

    frame.addWindowListener(new WindowAdapter {
      override def windowClosing(e: WindowEvent): Unit = closeHandler()
    })
  }
}

