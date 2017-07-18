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

package com.github.padrig64.minibus.base.topic;

import com.github.padrig64.minibus.api.Topic;

import javax.annotation.Generated;

/**
 * Simple implementation of a topic that can be named (e.g. for logging and debugging purposes).
 *
 * @param <C> Type of content that can be sent on this topic.
 */
public class SimpleTopic<C> implements Topic<C> {

    /**
     * Topic name.
     */
    private final String name;

    /**
     * Parent topic covering this topic.
     */
    private final Topic<? super C> parent;

    /**
     * Constructor.
     * <p>
     * A unique name will be chosen.
     */
    public SimpleTopic() {
        this(null, null);
    }

    /**
     * Constructor.
     * <p>
     * The specified name should either be unique or null. If it is null, a unique name will be chosen (different for
     * each instance).
     *
     * @param name Unique name or null.
     */
    public SimpleTopic(String name) {
        this(name, null);
    }

    /**
     * Constructor.
     *
     * @param parent Parent topic covering this topic, or null.
     */
    public SimpleTopic(Topic<? super C> parent) {
        this(null, parent);
    }

    /**
     * Constructor.
     * <p>
     * The specified name should either be unique or null. If it is null, a unique name will be chosen (different for
     * each instance).
     *
     * @param name   Unique name or null.
     * @param parent Parent topic covering this topic, or null.
     */
    public SimpleTopic(String name, Topic<? super C> parent) {
        if (name == null) {
            this.name = Topic.super.getName();
        } else {
            this.name = name;
        }
        this.parent = parent;
    }

    /**
     * @see Topic#getName()
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @see Topic#getParentTopic()
     */
    @Override
    public Topic<? super C> getParentTopic() {
        return parent;
    }

    @Generated("intellij")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleTopic<?> that = (SimpleTopic<?>) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return !(parent != null ? !parent.equals(that.parent) : that.parent != null);
    }

    @Generated("intellij")
    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append('[').append(name);
        if (parent != null) {
            sb.append("; ").append(parent);
        }
        sb.append(']');
        return sb.toString();
    }
}
