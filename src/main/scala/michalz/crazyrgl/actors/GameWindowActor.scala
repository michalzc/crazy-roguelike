package michalz.crazyrgl.actors

import akka.actor.{Actor, ActorLogging, Cancellable, Props}
import michalz.crazyrgl.actors.GameWindowActor.Tick
import michalz.crazyrgl.config.ConfigExtension

import scala.concurrent.duration.{Duration, DurationInt}

class GameWindowActor extends Actor with ActorLogging {

  var state: Option[WindowState] = None

  def receive: Receive = {
    case Tick =>
      log.debug("Tick received")
  }



  override def preStart = {
    val cfg = ConfigExtension(context.system)
    val scheduler = context.system.scheduler.schedule(Duration.Zero, 50.milliseconds, self, Tick)(context.dispatcher)

    state = Some(WindowState(scheduler))
  }

  override def postStop = {
    state.foreach(_.cleanUp)
  }

}

object GameWindowActor {
  def props: Props = Props(classOf[GameWindowActor])

  case object Tick
}

case class WindowState(
  scheduler: Cancellable
) {
  def cleanUp: Unit = {
    scheduler.cancel()
  }
}