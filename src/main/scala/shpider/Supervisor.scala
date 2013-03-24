package shpider

import akka.actor.Actor
import akka.actor.Props
import akka.actor.OneForOneStrategy
import akka.actor.ActorInitializationException

/**
 * This actor is used to override a default supervisorStrategy
 * for all it's child actors to prevent them from restarting as it
 * defined by default
 */
//class Supervisor extends Actor {
//  override val supervisorStrategy = OneForOneStrategy() {
//    case _: ActorInitializationException => Stop
//    case _: ActorKilledException => Stop
//    /* Resume execution of actor in case of ANY error */
//    case t: Throwable => { Logger.error("Supervisor: error in actor", t); Resume }
//  }
//  def receive = {
//    case p: Props => sender ! context.actorOf(p)
//  }
//}
//
//object Supervisor {
//  private val supervisor = Akka.system.actorOf(Props[Supervisor], "supervisor")
//
//  def createSupervisedActor(p: Props): ActorRef = {
//    implicit val timeout = Timeout(1 second)
//    val future = (Supervisor.supervisor ? p).mapTo[ActorRef]
//    Await.result(future, timeout.duration)
//  }
//}
