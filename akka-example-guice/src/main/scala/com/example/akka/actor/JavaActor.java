package com.example.akka.actor;

import akka.actor.UntypedActor;

import java.util.List;

/**
 * Created by Nikolay on 24.10.2016.
 */
public abstract class JavaActor extends UntypedActor implements TraitActor {

    private String id;

    public String id() {
        return id;
    }

    public void id(String id) {
        this.id = id;
    }

    @Override
    public void init(List<Object> args) {
        if (!args.isEmpty()) {
            if (args.get(0) instanceof String) {
                id((String) args.get(0));
            }
        }
    }
}
