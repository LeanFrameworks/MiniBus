package com.github.leanframeworks.minibus.base.topic;

import com.github.leanframeworks.minibus.api.Topic;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SimpleTopicTest {

    @Test
    public void parentTopics() {
        assertTrue(Topics.OBJECT.covers(Topics.OBJECT));
        assertTrue(Topics.OBJECT.covers(Topics.NUMBER));
        assertTrue(Topics.OBJECT.covers(Topics.INTEGER));
        assertTrue(Topics.OBJECT.covers(Topics.VOID));

        assertFalse(Topics.NUMBER.covers(Topics.OBJECT));
        assertTrue(Topics.NUMBER.covers(Topics.NUMBER));
        assertTrue(Topics.NUMBER.covers(Topics.INTEGER));
        assertFalse(Topics.NUMBER.covers(Topics.VOID));

        assertFalse(Topics.INTEGER.covers(Topics.OBJECT));
        assertFalse(Topics.INTEGER.covers(Topics.NUMBER));
        assertTrue(Topics.INTEGER.covers(Topics.INTEGER));
        assertFalse(Topics.INTEGER.covers(Topics.VOID));

        assertFalse(Topics.VOID.covers(Topics.OBJECT));
        assertFalse(Topics.VOID.covers(Topics.NUMBER));
        assertFalse(Topics.VOID.covers(Topics.INTEGER));
        assertTrue(Topics.VOID.covers(Topics.VOID));
    }

    private static final class Topics {
        public static final Topic<Object> OBJECT = new SimpleTopic<>("object");
        public static final Topic<Number> NUMBER = new SimpleTopic<>("number", OBJECT);
        public static final Topic<Integer> INTEGER = new SimpleTopic<>("integer", NUMBER);
        public static final Topic<Void> VOID = new SimpleTopic<>("void", OBJECT);

        // Useless because it as the same effect as the above
        @Deprecated
        public static final Topic<?> EXTENDS_OBJECT = new SimpleTopic<>("? extends object", OBJECT);
        @Deprecated
        public static final Topic<? extends Number> EXTENDS_NUMBER = new SimpleTopic<>("? extends number", OBJECT);
        @Deprecated
        public static final Topic<? extends Integer> EXTENDS_INTEGER = new SimpleTopic<>("? extends integer", OBJECT);
        @Deprecated
        public static final Topic<? extends Void> EXTENDS_VOID = new SimpleTopic<>("? extends void", OBJECT);

        // Useless because the handler will not even compile
        @Deprecated
        public static final Topic<? super Object> SUPER_OBJECT = new SimpleTopic<>("? super object", OBJECT);
        @Deprecated
        public static final Topic<? super Number> SUPER_NUMBER = new SimpleTopic<>("? super number", OBJECT);
        @Deprecated
        public static final Topic<? super Integer> SUPER_INTEGER = new SimpleTopic<>("? super integer", OBJECT);
        @Deprecated
        public static final Topic<? super Void> SUPER_VOID = new SimpleTopic<>("? super void", OBJECT);
    }
}
