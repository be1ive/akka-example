package com.example.akka.guice;

import akka.actor.*;
import com.google.inject.Injector;

import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * An Akka Extension to provide access to Guice managed Actor classes.
 */
public class GuiceExtension extends AbstractExtensionId<GuiceExtension.GuiceExt> {

    /**
     * The identifier used to access the GuiceExtension.
     */
    public static GuiceExtension instance = new GuiceExtension();

    /**
     * Is used by Akka to instantiate the Extension identified by this
     * ExtensionId, internal use only.
     */
    @Override
    public GuiceExt createExtension(ExtendedActorSystem extendedActorSystem) {
        return new GuiceExt();
    }

    /**
     * The Extension implementation.
     */
    public class GuiceExt implements Extension {

        private volatile Injector injector;

        /**
         * Used to initialize the Guice Injector for the extension.
         *
         * @param injector
         */
        public void initialize(Injector injector) {
            this.injector = injector;
        }

        /**
         * Create a Props for the specified actorType using the GuiceActorProducer class.
         *
         * @param actorType The type of the actor to create Props for
         * @return a Props that will create the typed actor bean using Guice
         */
        public Props props(Type actorType, Object... params) {
            return Props.create(GuiceActorProducer.class,
                    injector, actorType, Arrays.asList(params));
        }
    }
}
