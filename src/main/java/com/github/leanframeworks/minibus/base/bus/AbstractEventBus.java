/*
 * Copyright (c) 2017, LeanFrameworks
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
import com.github.leanframeworks.minibus.api.Event;
import com.github.leanframeworks.minibus.api.EventBus;
import com.github.leanframeworks.minibus.api.EventFilter;
import com.github.leanframeworks.minibus.api.EventHandler;
import com.github.leanframeworks.minibus.api.ExceptionHandler;
import com.github.leanframeworks.minibus.api.Topic;
import com.github.leanframeworks.minibus.base.channel.BroadcastChannel;
import com.github.leanframeworks.minibus.base.channel.SimpleChannel;
import com.github.leanframeworks.minibus.base.filter.ChannelFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static java.text.MessageFormat.format;

/**
 * Abstract implementation of an event bus.
 * <p>
 * This class implements the addition/removal of the various kinds of handlers and filters.
 */
public abstract class AbstractEventBus implements EventBus {

    /**
     * Logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEventBus.class);

    /**
     * Registered unchecked exception handlers.
     */
    protected final List<ExceptionHandler> exceptionHandlers = new ArrayList<>();

    /**
     * Registerd handlers for undelivered events.
     */
    protected final List<EventHandler<Object>> undeliveredEventHandlers = new ArrayList<>();

    /**
     * Mapping between subscriptions and topics.
     * <p>
     * This mapping can be used to retrieve the event handlers interested in the topic used for the event being
     * dispatched.
     */
    protected final Map<Topic<?>, Collection<SubscriptionEntry<?>>> topicToSubscription = new HashMap<>();

    /**
     * Mapping between subscription IDs and subscriptions.
     * <p>
     * This mapping can be used to unsubscribe event handlers.
     */
    protected final Map<Integer, SubscriptionEntry<?>> idToSubscription = new HashMap<>();

    /**
     * Last generated subscription ID.
     */
    private int lastId = -1;

    /**
     * @see EventBus#addExceptionHandler(ExceptionHandler)
     */
    @Override
    public void addExceptionHandler(ExceptionHandler handler) {
        exceptionHandlers.add(handler);
    }

    /**
     * @see EventBus#removeExceptionHandler(ExceptionHandler)
     */
    @Override
    public void removeExceptionHandler(ExceptionHandler handler) {
        exceptionHandlers.remove(handler);
    }

    /**
     * @see EventBus#addUndeliveredEventHandler(EventHandler)
     */
    @Override
    public void addUndeliveredEventHandler(EventHandler<Object> handler) {
        undeliveredEventHandlers.add(handler);
    }

    /**
     * @see EventBus#removeUndeliveredEventHandler(EventHandler)
     */
    @Override
    public void removeUndeliveredEventHandler(EventHandler<Object> handler) {
        undeliveredEventHandlers.remove(handler);
    }

    /**
     * Creates a default, dedicated channel for the specified event handler.
     *
     * @param handler Event handler to create a dedicated channel for.
     * @return Dedicated channel.
     */
    protected Channel createDefaultChannelForHandler(EventHandler<?> handler) {
        return new SimpleChannel("Channel:" + handler.getClass().getCanonicalName() + "@" +
                Integer.toHexString(System.identityHashCode(handler)));
    }

    /**
     * Registers a subscriber for the specified topic by adding an event handler.
     * <p>
     * A default, dedicated channel will be used for this event handler.
     *
     * @param topic   Topic to subscribe for.
     * @param handler Event handler that will process the received events.
     * @param <C>     Type of content to subscribe for.
     * @return Unique identifier of the subscription that can be used to {@link #unsubscribe(int)}.
     * @see #unsubscribe(int)
     */
    public final <C> int subscribe(Topic<C> topic, EventHandler<? super C> handler) {
        return subscribe(topic, null, null, handler);
    }

    /**
     * Registers a subscriber for the specified topic on the specified channel by adding an event handler.
     * <p>
     * A default, dedicated channel will be used for this event handler.
     *
     * @param topic   Topic to subscribe for.
     * @param channel Channel on which the events should be received.
     * @param handler Event handler that will process the received events.
     * @param <C>     Type of content to subscribe for.
     * @return Unique identifier of the subscription that can be used to {@link #unsubscribe(int)}.
     */
    public final <C> int subscribe(Topic<C> topic, Channel channel, EventHandler<? super C> handler) {
        return subscribe(topic, channel, null, handler);
    }

    /**
     * Registers a subscriber for the specified topic with the specified filter by adding an event handler.
     * <p>
     * A default, dedicated channel will be used for this event handler.
     *
     * @param topic   Topic to subscribe for.
     * @param filter  Additional filter that should be applied before receiving the events.
     * @param handler Event handler that will process the received events.
     * @param <C>     Type of content to subscribe for.
     * @return Unique identifier of the subscription that can be used to {@link #unsubscribe(int)}.
     */
    public final <C> int subscribe(Topic<C> topic, EventFilter<? super C> filter, EventHandler<? super C> handler) {
        return subscribe(topic, null, filter, handler);
    }

    /**
     * @see EventBus#subscribe(Topic, Channel, EventFilter, EventHandler)
     */
    @Override
    public final <C> int subscribe(Topic<C> topic,
                                   Channel channel,
                                   EventFilter<? super C> filter,
                                   EventHandler<? super C> handler) {
        int resultId;
        if ((topic == null) || (handler == null)) {
            LOGGER.error("Cannot subscribe with no topic ({}) or no handler ({})", topic, handler);
            resultId = -1;
        } else {
            resultId = ++lastId;

            Collection<SubscriptionEntry<?>> registry = topicToSubscription.computeIfAbsent(topic, k -> new HashSet<>
                    ());

            Channel effectiveChannel;
            if (channel == null) {
                effectiveChannel = createDefaultChannelForHandler(handler);
            } else {
                effectiveChannel = channel;
            }
            EventFilter<? super C> effectiveFilter;
            if (filter == null) {
                effectiveFilter = new ChannelFilter<>(effectiveChannel);
            } else {
                effectiveFilter = filter.and(new ChannelFilter<>(effectiveChannel));
            }
            SubscriptionEntry<C> entry = new SubscriptionEntry<>(resultId, topic, effectiveFilter, handler);
            registry.add(entry);
            idToSubscription.put(resultId, entry);
        }

        return resultId;
    }

    /**
     * @see EventBus#unsubscribe(int)
     */
    @Override
    public void unsubscribe(int id) {
        assert idToSubscription.containsKey(id) : format("Unknown registry entry for ID {0}", id);
        idToSubscription.remove(id);

        // TODO
    }

    /**
     * Sends null for the specified topic on the broadcast channel.
     * <p>
     * The content will be wrapped in an {@link Event} that will be dispatched to the registered event handlers.
     *
     * @param topic Topic for which the content must be dispatched.
     * @see #publish(Topic, Channel, Object)
     * @see #publish(Event)
     */
    public final void publish(Topic<Void> topic) {
        publish(topic, null);
    }

    /**
     * Sends the specified content for the specified topic on the broadcast channel.
     * <p>
     * The content will be wrapped in an {@link Event} that will be dispatched to the registered event handlers.
     *
     * @param topic   Topic for which the content must be dispatched.
     * @param content Content to be dispatched.
     * @param <C>     Type of content to be dispatched.
     * @see #publish(Topic, Channel, Object)
     * @see #publish(Event)
     */
    public final <C> void publish(Topic<C> topic, C content) {
        publish(topic, BroadcastChannel.getInstance(), content);
    }

    /**
     * @see EventBus#publish(Topic, Channel, Object)
     * @see #publish(Event)
     */
    @Override
    public final <C> void publish(Topic<C> topic, Channel channel, C content) {
        publish(new Event<>(topic, channel, content));
    }

    /**
     * Publishes the specified event on the event bus.
     * <p>
     * This method is called from the other publish methods.
     *
     * @param event Event to be dispatched.
     * @param <C>   Type of event content.
     */
    protected abstract <C> void publish(Event<C> event);

    /**
     * @see EventBus#dispose()
     */
    @Override
    public void dispose() {
        exceptionHandlers.clear();
        undeliveredEventHandlers.clear();
        topicToSubscription.clear();
        idToSubscription.clear();
        lastId = -1;
    }

    /**
     * Entity gathering all information regarding a subscription.
     *
     * @param <C> Type of event content.
     */
    protected class SubscriptionEntry<C> {

        /**
         * Identifier that can be used to unsubscribe.
         */
        private final int id;

        /**
         * Topic for which the subscription applies.
         */
        private final Topic<C> topic;

        /**
         * Filter associated with the subscription.
         */
        private final EventFilter<? super C> filter;

        /**
         * Subscriber.
         */
        private final EventHandler<? super C> handler;

        /**
         * Constructor.
         *
         * @param id      Identifier that can be used to unsubscribe.
         * @param topic   Topic for which the subscription applies.
         * @param filter  Filter registered with the subscription.
         * @param handler Subscriber.
         */
        public SubscriptionEntry(int id, Topic<C> topic, EventFilter<? super C> filter, EventHandler<? super C>
                handler) {
            this.id = id;
            this.topic = topic;
            this.filter = filter;
            this.handler = handler;
        }

        /**
         * Gets the identifier that can be used to unsubscribe.
         *
         * @return Subscription identifier.
         */
        public int getId() {
            return id;
        }

        /**
         * Gets the for which the subscription applies.
         *
         * @return Topic.
         */
        public Topic<C> getTopic() {
            return topic;
        }

        /**
         * Gets the filter registered with the subscription
         *
         * @return Event filter.
         */
        public EventFilter<? super C> getFilter() {
            return filter;
        }

        /**
         * Gets the subscribers.
         *
         * @return Event handler.
         */
        public EventHandler<? super C> getHandler() {
            return handler;
        }
    }
}
