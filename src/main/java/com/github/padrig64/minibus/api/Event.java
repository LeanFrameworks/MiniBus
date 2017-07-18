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

package com.github.padrig64.minibus.api;

import javax.annotation.Generated;

/**
 * Entity holding the content that is published by publishers and received by event handlers.
 *
 * @param <C> Type of content in the event.
 */
public class Event<C> {

    /**
     * Topic for which the content was published.
     */
    private final Topic<? extends C> topic;

    /**
     * Channel on which the content was sent.
     */
    private final Channel channel;

    /**
     * Content being dispatched.
     */
    private final C content;

    /**
     * Constructor specifying only the topic.
     * <p>
     * The content is null and sent on no particular channel.
     *
     * @param topic Topic for which the event is sent.
     */
    public Event(Topic<? extends C> topic) {
        this(topic, null);
    }

    /**
     * Constructor specifying the topic and content of the event.
     * <p>
     * The content is sent on no particular channel.
     *
     * @param topic   Topic for which the event is sent.
     * @param content Content being sent.
     */
    public Event(Topic<? extends C> topic, C content) {
        this(topic, null, content);
    }

    /**
     * Constructor specifying the topic, channel and content of the event.
     *
     * @param topic   Topic for which the event is sent.
     * @param channel Channel on which the event is sent.
     * @param content Content being sent.
     */
    public Event(Topic<? extends C> topic, Channel channel, C content) {
        this.topic = topic;
        this.channel = channel;
        this.content = content;
    }

    /**
     * Gets the topic of the event.
     *
     * @return Event topic.
     */
    public Topic<? extends C> getTopic() {
        return topic;
    }

    /**
     * Gets the channel on which the event is being dispatched.
     *
     * @return Channel on which the event is being dispatched.
     */
    public Channel getChannel() {
        return channel;
    }

    /**
     * Gets the content of the event.
     *
     * @return Event content.
     */
    public C getContent() {
        return content;
    }

    @Generated("intellij")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event<?> event = (Event<?>) o;

        if (topic != null ? !topic.equals(event.topic) : event.topic != null) return false;
        if (channel != null ? !channel.equals(event.channel) : event.channel != null) return false;
        return !(content != null ? !content.equals(event.content) : event.content != null);
    }

    @Generated("intellij")
    @Override
    public int hashCode() {
        int result = topic != null ? topic.hashCode() : 0;
        result = 31 * result + (channel != null ? channel.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Event@");
        sb.append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append(topic).append('/').append(channel);
        if (content != null) {
            sb.append(':').append(content);
        }
        sb.append(']');
        return sb.toString();
    }
}
