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

package com.github.leanframeworks.minibus.base.dispatcher;

import com.github.leanframeworks.minibus.api.Dispatcher;
import com.github.leanframeworks.minibus.api.Event;
import com.github.leanframeworks.minibus.api.EventFilter;
import com.github.leanframeworks.minibus.api.EventHandler;
import com.github.leanframeworks.minibus.api.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * Abstract implementation of a dispatcher.
 */
public abstract class AbstractDispatcher implements Dispatcher {

    /**
     * Logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDispatcher.class);

    /**
     * Passes the specified event to the specified event handler if the specified event filter accepts it.
     * <p>
     * If an exception is thrown during the processing of the event, the specified exception handlers will be used.
     * <p>
     * In theory, this method can be called by any concrete implementation of this class, since it can be considered as
     * a basic building block of any dispatch logic.
     *
     * @param event             Event to be processed.
     * @param filter            Event filter to be tested before passing the event to the event handler.
     * @param handler           Event handler to be used to process the event.
     * @param exceptionHandlers Exception handlers to be used in case an unchecked exception is thrown.
     * @return True if the event was processed by the event handler, false otherwise.
     */
    protected final boolean processEventThroughFilterAndHandler(Event<Object> event, EventFilter<Object> filter,
                                                                EventHandler<Object> handler,
                                                                Collection<ExceptionHandler> exceptionHandlers) {
        boolean delivered = false;

        try {
            if ((filter == null) || filter.accept(event)) {
                LOGGER.debug("Handling event '{}' with handler '{}'", event, handler);
                handler.handleEvent(event);
                delivered = true;
            }
        } catch (Throwable t) {
            processUncheckedException(t, exceptionHandlers, event);
        }

        return delivered;
    }

    /**
     * Passes the specified event to the specified undelivered event handlers.
     * <p>
     * If an exception is thrown during the processing of the event, the specified exception handlers will be used.
     * <p>
     * In theory, this method can be called by any concrete implementation of this class, since it can be considered as
     * a basic building block of any dispatch logic.
     *
     * @param event                    Event to be processed.
     * @param undeliveredEventHandlers Event handlers to be used to process the event.
     * @param exceptionHandlers        Exception handlers to be used in case an unchecked exception is thrown.
     */
    protected final void processUndeliveredEvent(Event<Object> event,
                                                 Collection<EventHandler<Object>> undeliveredEventHandlers,
                                                 Collection<ExceptionHandler> exceptionHandlers) {
        for (EventHandler<Object> handler : undeliveredEventHandlers) {
            try {
                LOGGER.debug("Processing undelivered event '{}' with undelivered event handler '{}'", event, handler);
                handler.handleEvent(event);
            } catch (Throwable t) {
                processUncheckedException(t, exceptionHandlers, event);
            }
        }
    }

    /**
     * Processes the specified exception with the specified exception handlers.
     *
     * @param t                 Exception to be processed.
     * @param exceptionHandlers Handlers to be used to process the exception.
     * @param event             Dispatched event for which the exception was thrown.
     */
    protected final void processUncheckedException(Throwable t,
                                                   Collection<ExceptionHandler> exceptionHandlers,
                                                   Event<?> event) {
        if ((exceptionHandlers == null) || exceptionHandlers.isEmpty()) {
            if (t instanceof Error) {
                throw (Error) t;
            } else if (t instanceof RuntimeException) {
                throw (RuntimeException) t;
            } else {
                throw new RuntimeException(t);
            }
        } else {
            exceptionHandlers.forEach(h -> h.handleException(t, event));
        }
    }
}
