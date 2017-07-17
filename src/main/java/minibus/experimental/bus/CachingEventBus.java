/*
 * %%Ignore-License
 *
 * Copyright (c) 2017, MiniBus Authors
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package minibus.experimental.bus;

import minibus.api.Dispatcher;
import minibus.api.Event;
import minibus.api.EventFilter;
import minibus.api.EventHandler;
import minibus.api.Topic;
import minibus.base.bus.AbstractEventBus;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// TODO
public class CachingEventBus extends AbstractEventBus {

    private final Dispatcher dispatcher;

    private final Map<Topic<?>, Event<?>> cache = new HashMap<>();

    public CachingEventBus(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

//    @Override
//    public <C> int subscribe(Topic<C> topic, EventFilter<? super C> filter, EventHandler<? super C> handler) {
//        return super.subscribe(topic, filter, handler);
//    }

    @Override
    public <C> void publish(Event<C> event) {
//        if (event.getTopic().getValidity() > 0) {
//            cache.put(event.getTopic(), event);
//        }

        dispatcher.dispatch((Event<Object>) event, getEventHandlersAndFilters(event.getTopic()),
                undeliveredEventHandlers, exceptionHandlers);
    }

    private Map<EventHandler<Object>, EventFilter<Object>> getEventHandlersAndFilters(Topic<?> topic) {
        Map<EventHandler<Object>, EventFilter<Object>> handlersAndFilters = new HashMap<>();

        Collection<SubscriptionEntry<?>> entries = topicToSubscription.get(topic);
        if (entries != null) {
            for (SubscriptionEntry<?> entry : entries) {
                handlersAndFilters.put((EventHandler<Object>) entry.getHandler(),
                        (EventFilter<Object>) entry.getFilter());
            }
        }

        return handlersAndFilters;
    }

    @Override
    public void dispose() {
        dispatcher.dispose();
        super.dispose();
    }
}
