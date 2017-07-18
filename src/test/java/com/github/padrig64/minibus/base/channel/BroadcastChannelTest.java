package com.github.padrig64.minibus.base.channel;

import com.github.padrig64.minibus.api.Channel;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BroadcastChannelTest {

    @Test
    public void broadcast() {
        Channel subChannel1 = new SimpleChannel("sub1");
        Channel subChannel2 = new SimpleChannel("sub2");
        Channel mainChannel = new SimpleChannel("main", subChannel1, subChannel2);
        Channel otherChannel = new SimpleChannel("other");
        Channel broadcastChannel = BroadcastChannel.getInstance();

        assertTrue(broadcastChannel.flowsInto(broadcastChannel));
        assertTrue(broadcastChannel.flowsInto(subChannel1));
        assertTrue(broadcastChannel.flowsInto(subChannel2));
        assertTrue(broadcastChannel.flowsInto(mainChannel));
        assertTrue(broadcastChannel.flowsInto(otherChannel));

        assertFalse(subChannel1.flowsInto(broadcastChannel));
        assertFalse(subChannel2.flowsInto(broadcastChannel));
        assertFalse(mainChannel.flowsInto(broadcastChannel));
        assertFalse(otherChannel.flowsInto(broadcastChannel));
    }
}
