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

package com.github.leanframeworks.minibus.base.channel;

import com.github.leanframeworks.minibus.api.Channel;

/**
 * Broadcast channel implemented as a singleton.
 * <p>
 * The broadcast channel can flow into any other channel.
 */
public final class BroadcastChannel implements Channel {

    /**
     * Singleton instance.
     */
    private static final BroadcastChannel SINGLETON = new BroadcastChannel();

    /**
     * Private constructor for singleton.
     */
    private BroadcastChannel() {
        // Nothing to be done
    }

    /**
     * Gets the single instance of the broadcast channel.
     *
     * @return Broadcast channel instance.
     */
    public static Channel getInstance() {
        return SINGLETON;
    }

    /**
     * @see Channel#flowsInto(Channel)
     */
    @Override
    public boolean flowsInto(Channel channel) {
        return true;
    }

    @Override
    public String toString() {
        return "BROADCAST";
    }
}
