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

package com.github.padrig64.minibus.base.filter;

import com.github.padrig64.minibus.api.Channel;
import com.github.padrig64.minibus.api.Event;
import com.github.padrig64.minibus.api.EventFilter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ChannelFilter<C> implements EventFilter<C> {

    private final boolean accept;

    private final Set<Channel> channels;

    public ChannelFilter(Channel channel) {
        this(Collections.singleton(channel));
    }

    public ChannelFilter(Channel... channels) {
        this(Arrays.asList(channels));
    }

    public ChannelFilter(Collection<? extends Channel> channels) {
        this(true, channels);
    }

    public ChannelFilter(boolean accept, Channel... channels) {
        this(accept, Arrays.asList(channels));
    }

    public ChannelFilter(boolean accept, Collection<? extends Channel> channels) {
        this.accept = accept;
        this.channels = new HashSet<>(channels);
    }

    public void addChannel(Channel channel) {
        channels.add(channel);
    }

    public void removeChannel(Channel channel) {
        channels.remove(channel);
    }

    @Override
    public boolean accept(Event<C> event) {
        boolean result;

        if (accept) {
            result = false;
            for (Channel channel : channels) {
                if (event.getChannel().flowsInto(channel)) {
                    result = true;
                    break;
                }
            }
        } else {
            result = true;
            for (Channel channel : channels) {
                if (!event.getChannel().flowsInto(channel)) {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }
}
