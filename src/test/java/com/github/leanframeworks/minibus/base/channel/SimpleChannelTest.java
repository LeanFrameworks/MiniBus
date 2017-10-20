package com.github.leanframeworks.minibus.base.channel;

import com.github.leanframeworks.minibus.api.Channel;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SimpleChannelTest {

    @Test
    public void childChannels() {
        Channel subChannel1 = new SimpleChannel("sub1");
        Channel subChannel2 = new SimpleChannel("sub2");
        Channel mainChannel = new SimpleChannel("main", subChannel1, subChannel2);
        Channel otherChannel = new SimpleChannel("other");

        assertTrue(mainChannel.flowsInto(subChannel1));
        assertTrue(mainChannel.flowsInto(subChannel2));
        assertTrue(mainChannel.flowsInto(mainChannel));
        assertFalse(mainChannel.flowsInto(otherChannel));

        assertTrue(subChannel1.flowsInto(subChannel1));
        assertFalse(subChannel1.flowsInto(subChannel2));
        assertFalse(subChannel1.flowsInto(mainChannel));
        assertFalse(subChannel1.flowsInto(otherChannel));

        assertFalse(subChannel2.flowsInto(subChannel1));
        assertTrue(subChannel2.flowsInto(subChannel2));
        assertFalse(subChannel2.flowsInto(mainChannel));
        assertFalse(subChannel2.flowsInto(otherChannel));

        assertFalse(otherChannel.flowsInto(subChannel1));
        assertFalse(otherChannel.flowsInto(subChannel2));
        assertFalse(otherChannel.flowsInto(mainChannel));
        assertTrue(otherChannel.flowsInto(otherChannel));
    }
}
