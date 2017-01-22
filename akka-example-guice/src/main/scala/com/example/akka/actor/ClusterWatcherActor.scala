package com.example.akka.actor

import akka.cluster.ClusterEvent._
import com.example.akka.cluster.ClusterWatcher
import kamon.Kamon
import kamon.metric.instrument.Gauge

import scala.concurrent.duration._
import scala.language.postfixOps

class ClusterWatcherActor extends ClusterWatcher {
  Kamon.metrics.registerGauge(name = "cluster-members", valueCollector =
    Gauge.functionZeroAsCurrentValueCollector(() => members.size),
    refreshInterval = Some(1 second))

  Kamon.metrics.registerGauge(name = "cluster-members-unreachable", valueCollector =
    Gauge.functionZeroAsCurrentValueCollector(() => unreachable.size),
    refreshInterval = Some(1 second))

  override def memberRemoved(event: MemberEvent): Unit = {
    super.memberRemoved(event)
  }

  def restart(): Unit = {
    Kamon.metrics.removeGauge("cluster-members")
    Kamon.metrics.removeGauge("cluster-members-unreachable")
  }

}
