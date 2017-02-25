package michalz.crazyrgl.actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props, Terminated}
import michalz.crazyrgl.actors.CrazyRglActor.StopRequest

/**
  * Created by michal on 24.02.17.
  */
class CrazyRglActor extends Actor with ActorLogging {

  var children: Map[Symbol, ActorRef] = Map.empty

  override def preStart(): Unit = {
    log.info("Starting")
    children = Map(
      'windowActor -> context.watch(context.actorOf(GameWindowActor.props, "window"))
    )
  }

  override def postStop(): Unit = {
    log.info("Stopped")
    context.system.terminate()
  }

  override def receive: Receive = {
    case StopRequest =>
      log.debug("Close request from {}", sender())
      log.info("Closing children")
      cleanStop()

    case Terminated(childRef) =>
      log.error("Child {} died unexpectedly", childRef)
      cleanStop()
  }

  def shuttingDown: Receive = {
    case Terminated(child) =>
      log.info("Child {} terminated", child)
      children = children.filter(_._2 != child)

      if(children.isEmpty) {
        log.info("All children terminated, shutting down")
        context.stop(self)
      }
  }

  private def cleanStop() = {
    children.values.foreach(context.stop)
    context.become(shuttingDown)
  }
}

object CrazyRglActor {
  def props: Props = Props(classOf[CrazyRglActor])

  case object StopRequest
}