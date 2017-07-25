/*
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

package com.github.padrig64.minibus.base.bus;

import com.github.padrig64.minibus.api.Dispatcher;
import com.github.padrig64.minibus.api.Event;
import com.github.padrig64.minibus.api.EventFilter;
import com.github.padrig64.minibus.api.EventHandler;
import com.github.padrig64.minibus.api.Topic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple, concrete implementation of an event bus that can cover most use cases.
 */
public class SimpleEventBus extends AbstractEventBus {

    /**
     * Dispatcher strategy to be used by this event bus.
     */
    private final Dispatcher dispatcher;

    /**
     * Constructor.
     *
     * @param dispatcher Dispatcher strategy to be used by this event bus.
     */
    public SimpleEventBus(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    /**
     * @see AbstractEventBus#publish(Event)
     */
    @Override
    public <C> void publish(Event<C> event) {
        // Safe cast
        dispatcher.dispatch((Event<Object>) event, getEventHandlersAndFilters(event.getTopic()),
                undeliveredEventHandlers, exceptionHandlers);
    }

    /**
     * Retrieves the event handlers and event filters that should be used for the dispatching an event on the specified
     * topic.
     *
     * @param topic Topic of the event to be dispatched.
     * @return Mapping between event handlers and event filters for the specified topic.
     */
    private Map<EventHandler<Object>, EventFilter<Object>> getEventHandlersAndFilters(Topic<?> topic) {
        Map<EventHandler<Object>, EventFilter<Object>> handlersAndFilters = new HashMap<>();

        Collection<SubscriptionEntry<?>> entries = topicToSubscription.get(topic);
        if (entries != null) {
            for (SubscriptionEntry<?> entry : entries) {
                // Safe casts
                handlersAndFilters.put((EventHandler<Object>) entry.getHandler(),
                        (EventFilter<Object>) entry.getFilter());
            }
        }

        return handlersAndFilters;
    }

    /**
     * @see AbstractEventBus#dispose()
     */
    @Override
    public void dispose() {
        dispatcher.dispose();
        super.dispose();
    }
}
