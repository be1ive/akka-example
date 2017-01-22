package com.example.akka;

import akka.actor.ActorRef;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Named;

import javax.inject.Inject;

/**
 * Created by nik.denisenko on 22/01/2017.
 */
public class Test {

    @Inject
    @Named("masterActor")
    public ActorRef masterActor;

    public static void main(String[] args) {
        //
        //in play we just need provide AkkaModule to the settings
        // play.modules.enabled += "com.example.akka.AkkaModule"
        // no need of TestModule as play provide actor system
        Injector injector = Guice.createInjector(new TestModule(), new AkkaModule());
        Test test = injector.getInstance(Test.class);
        test.masterActor.tell(new Object(), ActorRef.noSender());
    }
}
