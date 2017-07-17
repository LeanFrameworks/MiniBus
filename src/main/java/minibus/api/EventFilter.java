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
 * Interface to be implemented by event filters.
 * <p>
 * Filter implementations can be based on event class, content class, topic, channel, or anything you like.
 *
 * @param <C> Type of content in the event.
 */
@FunctionalInterface
public interface EventFilter<C> {

    /**
     * States whether the event should be passed to the event handler that was registered with it.
     *
     * @param event Event to be filtered in or out.
     * @return True if the event can be passed to the interested event handler, false otherwise.
     */
    boolean accept(Event<C> event);

    /**
     * Composes a new event filter by combining this event filter and the specified event filter with an AND operation.
     *
     * @param other Other event filter to be combined.
     * @return Newly composed event filter.
     */
    default EventFilter<C> and(EventFilter<C> other) {
        return c -> accept(c) && other.accept(c);
    }

    /**
     * Composes a new event filter by combining this event filter and the specified event filter with an OR operation.
     *
     * @param other Other event filter to be combined.
     * @return Newly composed event filter.
     */
    default EventFilter<C> or(EventFilter<C> other) {
        return c -> accept(c) || other.accept(c);
    }

    /**
     * Creates a new event filter negating this event filter.
     *
     * @return Newly composed event filter.
     */
    default EventFilter<C> not() {
        return c -> !accept(c);
    }
}
