package michalz.crazyrgl.gui

import java.awt.{Color, Graphics, Graphics2D}
import java.util.concurrent.atomic.AtomicReference
import javax.swing.JPanel

import com.typesafe.scalalogging.LazyLogging
import michalz.crazyrgl.gui.utils.TimeDelta

class Surface(stateRef: AtomicReference[Option[GraphicState]]) extends JPanel with LazyLogging {

  setDoubleBuffered(true)

  val timeDelta = new TimeDelta

  private def drawContent(g: Graphics2D) = {
    val state: Option[GraphicState] = stateRef.get

    val size = getSize()


    g.setBackground(Color.BLACK)
    g.clearRect(0, 0, size.width, size.height)

    g.setColor(Color.WHITE)
    val x = size.width / 2 - 50
    val y = size.height / 2 - 20
    g.drawString("Hello Crazy Rgl", x, y)

    drawFps(g)
  }

  override def paintComponent(g: Graphics): Unit = {
    timeDelta.update()
    super.paintComponent(g)
    drawContent(g.asInstanceOf[Graphics2D])
  }

  private def drawFps(g: Graphics2D): Unit = {
    g.setColor(Color.WHITE)
    g.drawString(s"FPS: ${timeDelta.fps}", 0, 20)
  }
}

