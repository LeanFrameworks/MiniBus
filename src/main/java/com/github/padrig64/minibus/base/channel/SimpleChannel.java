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

package com.github.padrig64.minibus.base.channel;

import com.github.padrig64.minibus.api.Channel;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * Simple implementation of a channel.
 * <p>
 * The other channels it can flow into can be specified as sub-channels.
 * <p>
 * Note that two instances having the same contents will not be considered equal.
 */
public class SimpleChannel implements Channel {

    /**
     * Name that can be used, for example, for logging or debugging purposes.
     */
    private final String name;

    /**
     * Sub-channels into which this channel can flow.
     */
    private final Collection<Channel> subChannels;

    /**
     * Constructor.
     * <p>
     * A default, unique name will be used.
     */
    public SimpleChannel() {
        this(null, (Collection<Channel>) null);
    }

    /**
     * Constructor.
     * <p>
     * A default, unique name will be used.
     *
     * @param subChannels Sub-channels into which this channel can flow.
     */
    public SimpleChannel(Channel... subChannels) {
        this(null, Arrays.asList(subChannels));
    }

    /**
     * Constructor.
     * <p>
     * A default, unique name will be used.
     *
     * @param subChannels Sub-channels into which this channel can flow.
     */
    public SimpleChannel(Collection<Channel> subChannels) {
        this(null, subChannels);
    }

    /**
     * Constructor.
     * <p>
     * If no name is given, a default, unique name will be used.
     *
     * @param name Name that can be used, for example, for logging or debugging purposes.
     */
    public SimpleChannel(String name) {
        this(name, (Collection<Channel>) null);
    }

    /**
     * Constructor.
     * <p>
     * If no name is given, a default, unique name will be used.
     *
     * @param name        Name that can be used, for example, for logging or debugging purposes.
     * @param subChannels Sub-channels into which this channel can flow.
     */
    public SimpleChannel(String name, Channel... subChannels) {
        this(name, Arrays.asList(subChannels));
    }

    /**
     * Constructor.
     * <p>
     * If no name is given, a default, unique name will be used.
     *
     * @param name        Name that can be used, for example, for logging or debugging purposes.
     * @param subChannels Sub-channels into which this channel can flow.
     */
    public SimpleChannel(String name, Collection<Channel> subChannels) {
        if (name == null) {
            this.name = Channel.super.getName();
        } else {
            this.name = name;
        }
        if (subChannels == null) {
            this.subChannels = Collections.emptySet();
        } else {
            this.subChannels = new HashSet<>(subChannels);
        }
    }

    /**
     * @see Channel#getName()
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Gets the sub-channels into which this channel can flow.
     *
     * @return Sub-channels.
     */
    public Collection<Channel> getSubChannels() {
        return subChannels;
    }

    /**
     * Adds the specified sub-channel into which this channel can flow.
     *
     * @param channel Sub-channel to be added.
     */
    public void addSubChannel(Channel channel) {
        subChannels.add(channel);
    }

    /**
     * Removes the specified sub-channel into which this channel can no longer flow.
     *
     * @param channel Sub-channel to be removed.
     */
    public void removeSubChannel(Channel channel) {
        subChannels.remove(channel);
    }

    /**
     * @see Channel#flowsInto(Channel)
     */
    @Override
    public boolean flowsInto(Channel channel) {
        boolean result = false;

        if (equals(channel)) {
            result = true;
        } else {
            for (Channel subChannel : subChannels) {
                if (subChannel.equals(channel) || subChannel.flowsInto(channel)) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(System.identityHashCode(this)) +
                "[" + name + ']';
    }
}
