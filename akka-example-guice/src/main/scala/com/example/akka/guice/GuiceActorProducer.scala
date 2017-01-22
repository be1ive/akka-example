package com.example.akka.guice

import java.util

import akka.actor.{Actor, IndirectActorProducer}
import com.example.akka.actor.TraitActor
import com.google.inject.Injector

/**
 * Created by ndenisenko on 18/04/16.
 */
class GuiceActorProducer(var injector: Injector, var actorType: Class[_ <: Actor], var args: util.List[AnyRef]) extends IndirectActorProducer {

  // here we can depending on actor type some how pass args
  // for example use array args
  override def produce(): Actor = {
    val actor: Actor = injector getInstance actorType
    actor match {
      case traitActor: TraitActor =>
        traitActor.init(args)
        traitActor
      case _ => actor
    }
  }

  override def actorClass: Class[_ <: Actor] = actorType

}
