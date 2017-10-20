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

package com.github.leanframeworks.minibus.base.bus;

import com.github.leanframeworks.minibus.api.Channel;
import com.github.leanframeworks.minibus.api.Dispatcher;
import com.github.leanframeworks.minibus.api.Event;
import com.github.leanframeworks.minibus.api.EventFilter;
import com.github.leanframeworks.minibus.api.EventHandler;
import com.github.leanframeworks.minibus.api.Topic;
import com.github.leanframeworks.minibus.base.dispatcher.SingleThreadDispatcher;

import static com.github.leanframeworks.minibus.base.dispatcher.SingleThreadDispatcher.NestedDispatchStrategy.QUEUE;

/**
 * Static version of a {@link SimpleEventBus} that can be used when a single instance is needed and must be accessible
 * from everywhere (avoiding repetitive code like "StaticEventBus.getInstance()").
 */
public final class StaticEventBus {

    /**
     * Single instance of the event bus.
     */
    private static SimpleEventBus eventBus = new SimpleEventBus(new SingleThreadDispatcher(QUEUE));

    /**
     * Private constructor utility class.
     */
    private StaticEventBus() {
        // Nothing to be done
    }

    /**
     * Re-initializes the event bus with the specified dispatch strategy.
     *
     * @param dispatcher Dispatch strategy to be used.
     */
    public static void init(Dispatcher dispatcher) {
        eventBus = new SimpleEventBus(dispatcher);
    }

    /**
     * @see SimpleEventBus#addUndeliveredEventHandler(EventHandler)
     */
    public static void addUndeliveredEventHandler(EventHandler<Object> handler) {
        eventBus.addUndeliveredEventHandler(handler);
    }

    /**
     * @see SimpleEventBus#removeUndeliveredEventHandler(EventHandler)
     */
    public static void removeUndeliveredEventHandler(EventHandler<Object> handler) {
        eventBus.removeUndeliveredEventHandler(handler);
    }

    /**
     * @see SimpleEventBus#subscribe(Topic, EventHandler)
     */
    public static <C> int subscribe(Topic<C> topic, EventHandler<? super C> handler) {
        return eventBus.subscribe(topic, handler);
    }

    /**
     * @see SimpleEventBus#subscribe(Topic, EventFilter, EventHandler)
     */
    public static <C> int subscribe(Topic<C> topic, EventFilter<? super C> filter, EventHandler<? super C> handler) {
        return eventBus.subscribe(topic, filter, handler);
    }

    /**
     * @see SimpleEventBus#subscribe(Topic, Channel, EventHandler)
     */
    public static <C> int subscribe(Topic<C> topic, Channel channel, EventHandler<? super C> handler) {
        return eventBus.subscribe(topic, channel, handler);
    }

    /**
     * @see SimpleEventBus#subscribe(Topic, Channel, EventFilter, EventHandler)
     */
    public static <C> int subscribe(Topic<C> topic, Channel channel, EventFilter<? super C> filter, EventHandler<?
            super C> handler) {
        return eventBus.subscribe(topic, channel, filter, handler);
    }

    /**
     * @see SimpleEventBus#unsubscribe(int)
     */
    public static void unsubscribe(int id) {
        eventBus.unsubscribe(id);
    }

    /**
     * Unsubscribes the specified event handler from all topics.
     *
     * @param handler Event handler to be unsubscribed from all topics.
     */
    public static void unsubscribe(EventHandler<?> handler) {
        // TODO
    }

    /**
     * Unsubscribes all event handlers for the specified topic.
     *
     * @param topic Topic for which all event handlers must be unsubscribed.
     */
    public static void unsubscribe(Topic<?> topic) {
        // TODO
    }

    /**
     * @see SimpleEventBus#publish(Topic)
     */
    public static void publish(Topic<Void> topic) {
        eventBus.publish(topic);
    }

    /**
     * @see SimpleEventBus#publish(Topic, Object)
     */
    public static <C> void publish(Topic<C> topic, C content) {
        eventBus.publish(topic, content);
    }

    /**
     * @see SimpleEventBus#publish(Topic, Channel, Object)
     */
    public static <C> void publish(Topic<C> topic, Channel channel, C content) {
        eventBus.publish(topic, channel, content);
    }

    /**
     * @see SimpleEventBus#publish(Event)
     */
    public static void publish(Event<?> event) {
        eventBus.publish(event);
    }

    /**
     * @see SimpleEventBus#dispose()
     */
    public static void dispose() {
        eventBus.dispose();
    }
}
