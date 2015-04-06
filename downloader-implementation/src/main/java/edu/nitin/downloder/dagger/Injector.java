package edu.nitin.downloder.dagger;

import dagger.ObjectGraph;

/**
 * Created by nitin.verma on 03/04/15.
 */
public class Injector {

    final static ObjectGraph graph = ObjectGraph.create(Module.class);

    public static void inject(final Object object) {
        graph.inject(object);
    }

}
