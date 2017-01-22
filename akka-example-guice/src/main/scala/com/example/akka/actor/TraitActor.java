package com.example.akka.actor;

import akka.actor.Actor;

import java.util.List;

/**
 * Created by ndenisenko on 06.09.2016.
 */
public interface TraitActor extends Actor {

    void init(List<Object> args);

}
