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

package com.github.leanframeworks.minibus.base.exceptionhandler;

import com.github.leanframeworks.minibus.api.Event;
import com.github.leanframeworks.minibus.api.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception handler to will log the exception as errors.
 */
public class LogErrorExceptionHandler implements ExceptionHandler {

    /**
     * Logger to be used to log to be exception.
     */
    private final Logger logger;

    /**
     * Constructor.
     * <p>
     * A logger dedicated to this class will be used.
     */
    public LogErrorExceptionHandler() {
        this(null);
    }

    /**
     * Constructor.
     * <p>
     * If no logger is given, a logger dedicated to this class will be used.
     *
     * @param logger Logger to be used to log to be exception.
     */
    public LogErrorExceptionHandler(Logger logger) {
        if (logger == null) {
            this.logger = LoggerFactory.getLogger(ExceptionHandler.class);
        } else {
            this.logger = logger;
        }
    }

    /**
     * @see ExceptionHandler#handleException(Throwable, Event)
     */
    @Override
    public void handleException(Throwable throwable, Event<?> event) {
        logger.error("Exception while dispatching event '" + event + "'", throwable);
    }
}
