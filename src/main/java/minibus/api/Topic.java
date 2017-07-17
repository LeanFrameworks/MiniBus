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
 * Interface to be implemented by topics.
 * <p>
 * A topic defines the type of content transported by the events.
 *
 * @param <C> Type of content that can be sent and received on this topic.
 */
public interface Topic<C> {

    /**
     * Gets the name of the topic (or topic instance), mostly useful for logging/debugging purposes.
     * <p>
     * By default, the class's simple name followed by the instance's hash code.
     *
     * @return Topic name.
     */
    default String getName() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(System.identityHashCode(this));
    }

    /**
     * Gets the parent topic if any.
     * <p>
     * A topic knows its parent, but a parent topic may or may not know their child topics depending on the
     * implementation.
     *
     * @return Parent topic or null if this topic is a top-level topic.
     */
    default Topic<? super C> getParentTopic() {
        return null;
    }

    /**
     * States whether this topic covers the specified topics.
     * <p>
     * A parent topic covers its child topics. Entities subscribing to a parent topic will receive messages for the
     * parent topic and any of its child topics. For example, if an entity subscribes to event about the "Geography"
     * topic, it will receive messages about "Geography" in general, but also about "France", about "The Netherlands",
     * etc. However, an entity subscribing to events about the "France" topic, it will not receive messages about
     * "Geography" in general or about "The Netherlands".
     * <p>
     * By default, a topic is covered by its parent topic; and a null topic is covered by nothing.
     * <p>
     * Note that the implementation should be consistent with {@link #getParentTopic()}.
     *
     * @param topic Topic to be checked (whether it is covered by this topic or not).
     * @return True if this topic covers the specified topic, false otherwise.
     * @see #getParentTopic()
     */
    default boolean covers(Topic<?> topic) {
        return (topic != null) && (equals(topic) || covers(topic.getParentTopic()));
    }
}
