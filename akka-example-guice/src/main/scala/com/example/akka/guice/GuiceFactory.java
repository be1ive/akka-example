package com.example.akka.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import java.util.List;


public class GuiceFactory {

    private static volatile Injector inj = Guice.createInjector();

    public static Injector getInjector() {
        return inj;
    }

    public static Injector createInjector(List<Module> mds) {
        inj = Guice.createInjector(mds);
        return inj;
    }

}
