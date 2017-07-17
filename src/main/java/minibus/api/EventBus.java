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

package minibus.api;

/**
 * Interface to be implemented by event buses.
 * <p>
 * An event bus is the central place where publishers (posting the messages) and the subscribers (event handlers) are
 * registered.
 */
public interface EventBus {

    /**
     * Adds the specified exception handler that will be notified whenever and exception is thrown when an event is
     * being dispatched or processed by an event handler.
     *
     * @param handler Exception handler to be added.
     */
    void addExceptionHandler(ExceptionHandler handler);

    /**
     * Removes the specified exception handler
     *
     * @param handler Exception handler to be removed.
     */
    void removeExceptionHandler(ExceptionHandler handler);

    /**
     * Adds the specified event handler that will process events that have not been processed by any event handler
     * registered with {@link #subscribe(Topic, Channel, EventFilter, EventHandler)}.
     *
     * @param handler Undelivered event handler to be added.
     */
    void addUndeliveredEventHandler(EventHandler<Object> handler);

    /**
     * Removes the specified undelievered event handler.
     *
     * @param handler Undelivered event handler to be removed.
     */
    void removeUndeliveredEventHandler(EventHandler<Object> handler);

    /**
     * Registers a subscriber for the specified topic on the specified channel with the specified filter by adding an
     * event handler.
     *
     * @param topic   Topic to subscribe for.
     * @param channel Channel on which the events should be received.
     * @param filter  Additional filter that should be applied before receiving the events.
     * @param handler Event handler that will process the received events.
     * @param <C>     Type of content to subscribe for.
     * @return Unique identifier of the subscription that can be used to {@link #unsubscribe(int)}.
     * @see #unsubscribe(int)
     */
    <C> int subscribe(Topic<C> topic, Channel channel, EventFilter<? super C> filter, EventHandler<? super C> handler);

    /**
     * Unregisters a subscriber that was previously registered.
     *
     * @param id Identifier of a previous subscription.
     * @see #subscribe(Topic, Channel, EventFilter, EventHandler)
     */
    void unsubscribe(int id);

    /**
     * Sends the specified content for the specified topic on the specified channel.
     * <p>
     * The content will be wrapped in an {@link Event} that will be dispatched to the registered event handlers.
     *
     * @param topic   Topic for which the content must be dispatched.
     * @param channel Channel on which the event must be dispatched.
     * @param content Content to be dispatched.
     * @param <C>     Type of content to be dispatched.
     */
    <C> void publish(Topic<C> topic, Channel channel, C content);

    /**
     * Disposes the event bus by removing all event handlers, undelivered event handlers, stopping the dispatching
     * threads, etc.
     * <p>
     * After calling this method, the event bus may no longer be used, depending on the implementation.
     */
    void dispose();
}
