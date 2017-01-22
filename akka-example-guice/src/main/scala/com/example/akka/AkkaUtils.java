package com.example.akka;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;

import static com.example.akka.guice.GuiceExtension.instance;

public class AkkaUtils {

    private AkkaUtils() {}

    public static <T extends UntypedActor> ActorRef createChildActor(ActorContext context, Class<T> clazz, String id) {
        ActorRef actor = context.actorOf(instance.get(context.system()).props(clazz, id), id);
        context.watch(actor);
        return actor;
    }

}
