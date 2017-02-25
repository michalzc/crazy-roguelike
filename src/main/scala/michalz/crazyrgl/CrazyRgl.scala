package michalz.crazyrgl

import akka.actor.ActorSystem
import com.typesafe.scalalogging.LazyLogging
import michalz.crazyrgl.actors.CrazyRglActor

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

/**
  * Created by michal on 24.02.17.
  */
object CrazyRgl extends App with LazyLogging {

  import scala.concurrent.ExecutionContext.Implicits.global

  logger.info("Starting CrazyRgl")

  val system = ActorSystem("CrazyRgl")
  val crazyRglActor = system.actorOf(CrazyRglActor.props, "crazy-rgl")


  sys.addShutdownHook {
    logger.info("In shutdown hook")
    Await.ready(system.terminate(), Duration.Inf)
  }

  system.whenTerminated.onComplete {
    case Success(termination) =>
      logger.info(s"Terminating application: ${termination}")

    case Failure(ex) =>
      logger.error("Application terminated", ex)
  }
}
