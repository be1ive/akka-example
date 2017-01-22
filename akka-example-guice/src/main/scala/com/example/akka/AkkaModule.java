package com.example.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.example.akka.actor.MasterActor;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.name.Names;

import static com.example.akka.guice.GuiceExtension.instance;


public class AkkaModule extends AbstractModule {

    public static final String AKKA_SYSTEM_NAME = "actorSystem";

    //here we need to get or create actor system
    //in case of play 2.5 we can inject it
    //and initialize guice extension with actor system
    static class AkkaSystemProvider implements Provider<ActorSystem> {

        @Inject ActorSystem actorSystem;
        @Inject Injector injector;

        @Override
        public ActorSystem get() {
            instance.get(actorSystem).initialize(injector);
            return actorSystem;
        }
    }

    static class MasterActorProvider implements Provider<ActorRef> {

        @Inject ActorSystem actorSystem;

        @Override
        public ActorRef get() {
            return actorSystem.actorOf(instance.get(actorSystem).props(MasterActor.class),
                    MasterActor.MASTER_ACTOR_NAME);
        }
    }

    @Override
    protected void configure() {
        bind(ActorSystem.class).annotatedWith(Names.named(AkkaModule.AKKA_SYSTEM_NAME))
                .toProvider(AkkaSystemProvider.class).asEagerSingleton();

        bind(ActorRef.class).annotatedWith(Names.named(MasterActor.MASTER_ACTOR_NAME))
                .toProvider(MasterActorProvider.class).asEagerSingleton();
    }

}
