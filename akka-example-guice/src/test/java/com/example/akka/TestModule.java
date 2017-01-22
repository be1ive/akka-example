package com.example.akka;

import akka.actor.ActorSystem;
import com.google.inject.AbstractModule;


/**
 * Created by nik.denisenko on 22/01/2017.
 */
public class TestModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ActorSystem.class).toInstance(ActorSystem.create("actorSystem"));
    }
}
