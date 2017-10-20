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

package com.github.leanframeworks.minibus.base.filter;

import com.github.leanframeworks.minibus.api.Event;
import com.github.leanframeworks.minibus.api.EventFilter;
import com.github.leanframeworks.minibus.api.Topic;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Event filter implementation based on topics.
 *
 * @param <C> Type of content in the event.
 */
public class TopicFilter<C> implements EventFilter<C> {

    /**
     * True if the filter should accept events for the given topics, false if it should reject events for the given
     * topics.
     *
     * @see #topics
     */
    private final boolean accept;

    /**
     * Topics for which the events are either accepted or rejected.
     *
     * @see #accept
     */
    private final Set<Topic<? extends C>> topics;

    /**
     * Constructor.
     *
     * @param topic Topic for which the events are either accepted.
     */
    public TopicFilter(Topic<? extends C> topic) {
        this(Collections.singleton(topic));
    }

    /**
     * Constructor.
     *
     * @param topics Topics for which the events are either accepted.
     */
    @SafeVarargs
    public TopicFilter(Topic<? extends C>... topics) {
        this(Arrays.asList(topics));
    }

    /**
     * Constructor.
     *
     * @param topics Topics for which the events are either accepted.
     */
    public TopicFilter(Collection<Topic<? extends C>> topics) {
        this(true, topics);
    }

    /**
     * Constructor.
     *
     * @param accept True if the filter should accept events for the given topics, false if it should reject events for
     *               the given topics.
     * @param topics Topics for which the events are either accepted or rejected.
     */
    @SafeVarargs
    public TopicFilter(boolean accept, Topic<? extends C>... topics) {
        this(accept, Arrays.asList(topics));
    }

    /**
     * Constructor.
     *
     * @param accept True if the filter should accept events for the given topics, false if it should reject events for
     *               the given topics.
     * @param topics Topics for which the events are either accepted or rejected.
     */
    public TopicFilter(boolean accept, Collection<Topic<? extends C>> topics) {
        this.accept = accept;
        this.topics = new HashSet<>(topics);
    }

    /**
     * Adds the specified topic for which the events should either be accepted or rejected.
     *
     * @param topic Event topic to be added.
     */
    public void addTopic(Topic<? extends C> topic) {
        topics.add(topic);
    }

    /**
     * Removes the specified topic for which the events should now be either rejected or accepted.
     *
     * @param topic Event topic to be removed.
     */
    public void removeTopic(Topic<? extends C> topic) {
        topics.remove(topic);
    }

    /**
     * @see EventFilter#accept(Event)
     */
    @Override
    public boolean accept(Event<C> event) {
        boolean result;

        if (accept) {
            // If no topic specified, reject event (not accepted for any topic)
            result = false;
            for (Topic<? extends C> topic : topics) {
                if (topic.covers(event.getTopic())) {
                    result = true;
                    break;
                }
            }
        } else {
            // If no topic specified, accept event (not rejected for any topic)
            result = true;
            for (Topic<? extends C> topic : topics) {
                if (topic.covers(event.getTopic())) {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }
}
