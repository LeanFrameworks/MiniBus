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

package minibus.base.dispatcher;

import minibus.api.Event;
import minibus.api.EventFilter;
import minibus.api.EventHandler;
import minibus.api.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public abstract class AbstractSerialDispatcher extends AbstractDispatcher {

    /**
     * Logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSerialDispatcher.class);

    private final Queue<QueueEntry> queuedEvents = new LinkedList<>();

    private int nestedDispatchCount = 0;

    protected final int getNestedDispatchCount() {
        return nestedDispatchCount;
    }

    protected final void processEvent(Event<Object> event, Map<EventHandler<Object>, EventFilter<Object>>
            eventHandlers, Collection<EventHandler<Object>> undeliveredEventHandlers,
                                      Collection<ExceptionHandler> exceptionHandlers) {
        LOGGER.debug("Dispatching event '{}'", event);

        nestedDispatchCount++;
        try {
            boolean delivered = false;

            for (Map.Entry<EventHandler<Object>, EventFilter<Object>> entry : eventHandlers.entrySet()) {
                delivered = processEventThroughFilterAndHandler(event, entry.getValue(), entry.getKey(),
                        exceptionHandlers);
            }

            if (!delivered) {
                processUndeliveredEvent(event, undeliveredEventHandlers, exceptionHandlers);
            }

        } finally {
            nestedDispatchCount--;
        }
    }

    protected final void queueEvent(Event<Object> event, Map<EventHandler<Object>, EventFilter<Object>>
            eventHandlers, Collection<EventHandler<Object>> undeliveredEventHandlers,
                                    Collection<ExceptionHandler> exceptionHandlers) {
        queuedEvents.offer(new QueueEntry(event, eventHandlers, undeliveredEventHandlers, exceptionHandlers));
    }

    protected final void processQueue() {
        while (!queuedEvents.isEmpty()) {
            QueueEntry entry = queuedEvents.poll();
            processEvent(entry.getEvent(), entry.getEventHandlers(), entry.getUndeliveredEventHandlers(), entry
                    .getExceptionHandlers());
        }
    }

    private static class QueueEntry {

        private final Event<Object> event;

        private final Map<EventHandler<Object>, EventFilter<Object>> eventHandlers;

        private final Collection<EventHandler<Object>> undeliveredEventHandlers;

        private final Collection<ExceptionHandler> exceptionHandlers;

        public QueueEntry(Event<Object> event, Map<EventHandler<Object>, EventFilter<Object>> eventHandlers,
                          Collection<EventHandler<Object>> undeliveredEventHandlers,
                          Collection<ExceptionHandler> exceptionHandlers) {

            this.event = event;
            this.eventHandlers = eventHandlers;
            this.undeliveredEventHandlers = undeliveredEventHandlers;
            this.exceptionHandlers = exceptionHandlers;
        }

        public Event<Object> getEvent() {
            return event;
        }

        public Map<EventHandler<Object>, EventFilter<Object>> getEventHandlers() {
            return eventHandlers;
        }

        public Collection<EventHandler<Object>> getUndeliveredEventHandlers() {
            return undeliveredEventHandlers;
        }

        public Collection<ExceptionHandler> getExceptionHandlers() {
            return exceptionHandlers;
        }
    }
}
