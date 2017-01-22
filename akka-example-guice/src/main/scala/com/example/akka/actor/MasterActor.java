package com.example.akka.actor;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.example.service.SuperService;

import javax.inject.Inject;

import java.util.UUID;

import static com.example.akka.AkkaUtils.createChildActor;

/**
 * Created by nik.denisenko on 22/01/2017.
 */
public class MasterActor extends UntypedActor {

    public static final String MASTER_ACTOR_NAME = "masterActor";

    @Inject
    private SuperService superService;

    @Override
    public void onReceive(Object message) throws Throwable {
        superService.superMethod(MASTER_ACTOR_NAME);

        createTaskActor(UUID.randomUUID().toString())
                .tell(message, getSelf());
    }

    private ActorRef createTaskActor(String id) {
        return createChildActor(getContext(), WorkerActor.class, id);
    }

}
