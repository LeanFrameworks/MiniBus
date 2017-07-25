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

package com.github.padrig64.minibus.api;

/**
 * Interface to be implemented by channels.
 */
public interface Channel {

    /**
     * Gets the name of the channel, mostly useful for logging/debugging purposes.
     * <p>
     * By default, the class's simple name followed by the instance's hash code.
     *
     * @return Channel name.
     */
    default String getName() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(System.identityHashCode(this));
    }

    /**
     * States whether this channel flows into the specified channel.
     * <p>
     * By default, a channel only flows into itself.
     *
     * @param channel Channel to be checked (whether it accepts events from this channel).
     * @return True if the specified channel accepts events from this channel, false otherwise.
     */
    default boolean flowsInto(Channel channel) {
        return equals(channel);
    }
}
