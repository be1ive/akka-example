package com.example.akka.cluster

import akka.actor.Actor
import akka.cluster.Cluster
import akka.cluster.ClusterEvent._
import akka.event.Logging

import scala.collection.mutable

abstract class ClusterWatcher extends Actor{
  val log = Logging(context.system, this)

  val cluster = Cluster.get(context.system)

  val members : mutable.HashSet[String] = mutable.HashSet()
  val unreachable : mutable.HashSet[String] = mutable.HashSet()

  override def preStart(): Unit = {
    log.info("Cluster aware actor started, subscribing for cluster events")
    cluster.subscribe(self, initialStateMode = InitialStateAsEvents, classOf[MemberEvent], classOf[ReachabilityEvent])
  }

  override def postStop(): Unit = cluster.unsubscribe(self)

  override def receive = {
    case event: MemberExited => memberRemoved(event)
    case event: MemberRemoved => memberRemoved(event)
    case event: MemberLeft => memberRemoved(event)
    case event: MemberJoined => memberJoined(event)
    case event: MemberUp => memberJoined(event)
    case event: ReachableMember => reachableMember(event)
    case event: UnreachableMember => unreachableMember(event)

    case message => logState(message)
  }

  def memberRemoved(event: MemberEvent): Unit = {
    val memberAddress = event.member.address.toString
    members -= memberAddress
    unreachable -= memberAddress
    logState(event)
  }

  def memberJoined(event: MemberEvent): Unit = {
    val memberAddress = event.member.address.toString
    members += memberAddress
    unreachable -= memberAddress
    logState(event)
  }

  def reachableMember(reachableMember: ReachableMember): Unit = {
    val memberAddress = reachableMember.member.address.toString
    unreachable -= memberAddress
    logState(reachableMember)
  }

  def unreachableMember(unreachableMember: UnreachableMember): Unit = {
    val memberAddress = unreachableMember.member.address.toString
    unreachable += memberAddress
    logState(unreachableMember)
  }

  def logState(message: Any): Unit = {
    System.out.println(message)
    log.info(message.toString)
    log.info("================================CLUSTER STATE================================")
    for (member <- members) {
      if (unreachable contains member) {
        log.info("| [UNREACHABLE] " + member)
      }
      else {
        log.info("|               " + member)
      }
    }
    log.info("=============================================================================")
  }
}
