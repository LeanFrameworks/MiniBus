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

package minibus.base.channel;

import minibus.api.Channel;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * Simple implementation of a channel.
 * <p>
 * The other channels it can flow into can be specified as sub-channels.
 */
public class SimpleChannel implements Channel {

    private final String name;

    private final Collection<Channel> subChannels;

    public SimpleChannel() {
        this(null, (Collection<Channel>) null);
    }

    public SimpleChannel(Channel... subChannels) {
        this(null, Arrays.asList(subChannels));
    }

    public SimpleChannel(Collection<Channel> subChannels) {
        this(null, subChannels);
    }

    public SimpleChannel(String name) {
        this(name, (Collection<Channel>) null);
    }

    public SimpleChannel(String name, Channel... subChannels) {
        this(name, Arrays.asList(subChannels));
    }

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

    @Override
    public String getName() {
        return name;
    }

    public Collection<Channel> getSubChannels() {
        return subChannels;
    }

    public void addSubChannel(Channel channel) {
        subChannels.add(channel);
    }

    public void removeSubChannel(Channel channel) {
        subChannels.remove(channel);
    }

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

    @Generated("intellij")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleChannel that = (SimpleChannel) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return !(subChannels != null ? !subChannels.equals(that.subChannels) : that.subChannels != null);
    }

    @Generated("intellij")
    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (subChannels != null ? subChannels.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(System.identityHashCode(this)) +
                "[" + name + ']';
    }
}
