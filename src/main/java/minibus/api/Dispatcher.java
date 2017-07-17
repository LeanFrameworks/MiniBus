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

import java.util.Collection;
import java.util.Map;

/**
 * Interface to be implemented by dispatchers.
 * <p>
 * A dispatcher is basically the internal logic of the event bus as its implementation governs the delivery of the
 * events to the registered event handlers.
 * <p>
 * Different implementations may choose different dispatching strategies (for example, by scheduling to other threads,
 * queuing events, etc.).
 */
public interface Dispatcher {

    /**
     * Dispatches the specified event to the specified event filters and handlers.
     *
     * @param event                    Event to be dispatched.
     * @param eventHandlers            Event handlers that are candidates for receiving the event.
     * @param undeliveredEventHandlers Evant handlers that should process undelivered events.
     * @param exceptionHandlers        Exception handlers to be used in case an exception is thrown during the
     *                                 dispatching or processing of the event.
     */
    void dispatch(Event<Object> event,
                  Map<EventHandler<Object>, EventFilter<Object>> eventHandlers,
                  Collection<EventHandler<Object>> undeliveredEventHandlers,
                  Collection<ExceptionHandler> exceptionHandlers);

    /**
     * Disposes all resources (for example, threads) used by this dispatcher.
     * <p>
     * After calling this method, the dispatcher may no longer be used.
     */
    void dispose();
}
