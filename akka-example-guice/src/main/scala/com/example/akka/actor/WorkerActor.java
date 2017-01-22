package com.example.akka.actor;

import com.example.service.SuperService;

import javax.inject.Inject;

/**
 * Created by nik.denisenko on 22/01/2017.
 */
public class WorkerActor extends JavaActor {

    @Inject
    private SuperService superService;

    @Override
    public void onReceive(Object message) throws Throwable {
        superService.superMethod(id());
    }

}
