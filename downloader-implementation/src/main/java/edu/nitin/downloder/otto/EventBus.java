package edu.nitin.downloder.otto;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by nitin.verma on 03/04/15.
 */
public class EventBus {
    final Bus bus = new Bus(ThreadEnforcer.ANY, EventBus.class.getName());

    public void register(final Object o) {
        bus.register(o);
    }

    public void unregister(final Object o) {
        bus.unregister(o);
    }

    public void post(final Object e) {
        bus.post(e);
    }
}
