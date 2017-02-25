package michalz.crazyrgl.actors

import akka.actor.{Actor, ActorLogging, Props}

/**
  * Created by michal on 24.02.17.
  */
class CrazyRglActor extends Actor with ActorLogging {


  override def preStart() = {
    log.info("Starting")
  }

  override def postStop() = {
    log.info("Stopped")
  }

  override def receive: Receive = PartialFunction.empty
}

object CrazyRglActor {
  def props: Props = Props(classOf[CrazyRglActor])
}