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

package minibus.experimental.dispatcher;

import minibus.api.Event;
import minibus.api.EventFilter;
import minibus.api.EventHandler;
import minibus.api.ExceptionHandler;
import minibus.base.dispatcher.AbstractDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadDispatcher extends AbstractDispatcher {

    /**
     * Logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MultiThreadDispatcher.class);

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private final NestedDispatchStrategy nestedDispatchStrategy;

    public MultiThreadDispatcher(NestedDispatchStrategy nestedDispatchStrategy) {
        this.nestedDispatchStrategy = nestedDispatchStrategy;
    }

    public NestedDispatchStrategy getNestedDispatchStrategy() {
        return nestedDispatchStrategy;
    }

    @Override
    public void dispatch(Event<Object> event, Map<EventHandler<Object>, EventFilter<Object>> eventHandlers,
                         Collection<EventHandler<Object>> undeliveredEventHandlers,
                         Collection<ExceptionHandler> exceptionHandlers) {
        // TODO
    }

    @Override
    public void dispose() {
        executorService.shutdownNow();
    }

    public enum NestedDispatchStrategy {
        PROCESS_IMMEDIATELY,
        QUEUE,
        RESCHEDULE
    }
}
