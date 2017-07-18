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

package com.github.padrig64.minibus.base.bus;

import com.github.padrig64.minibus.api.Channel;
import com.github.padrig64.minibus.api.Event;
import com.github.padrig64.minibus.api.EventFilter;
import com.github.padrig64.minibus.api.EventHandler;
import com.github.padrig64.minibus.api.Topic;
import com.github.padrig64.minibus.base.channel.SimpleChannel;
import com.github.padrig64.minibus.base.dispatcher.CurrentThreadDispatcher;
import com.github.padrig64.minibus.base.topic.SimpleTopic;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SimpleEventBusTest {

    private static final SimpleEventBus BUS = new SimpleEventBus(new CurrentThreadDispatcher(CurrentThreadDispatcher
            .NestedDispatchStrategy.PROCESS_IMMEDIATELY));

    private static final Channel PARENT_CHANNEL = new SimpleChannel(SpecificChannels.values());

    private static String string(Event<?> event) {
        String str;
        if (event.getContent() == null) {
            str = "null";
        } else {
            str = event.getContent().getClass().getSimpleName();
        }
        return str;
    }

    @Before
    public void setUp() {
//        addUndeliveredEventHandler(e -> assertFalse("Unhandled event: " + e, true));
    }


    private class ToString implements EventHandler<Object> {

        @Override
        public void handleEvent(Event<Object> event) {
            System.out.println(event.getContent().toString());
        }
    }

    @Test
    public void compileAndRunWithTopicAndHandler() {
        BUS.subscribe(OtherTopics.OBJECT, new ObjectEventHandler());
//      BUS.subscribe(OtherTopics.OBJECT, new NumberEventHandler());
//      BUS.subscribe(OtherTopics.OBJECT, new IntegerEventHandler());
//      BUS.subscribe(OtherTopics.OBJECT, new VoidEventHandler());

        BUS.subscribe(OtherTopics.NUMBER, new ObjectEventHandler());
        BUS.subscribe(OtherTopics.NUMBER, new NumberEventHandler());
//      BUS.subscribe(OtherTopics.NUMBER, new IntegerEventHandler());
//      BUS.subscribe(OtherTopics.NUMBER, new VoidEventHandler());

        BUS.subscribe(OtherTopics.INTEGER, new ObjectEventHandler());
        BUS.subscribe(OtherTopics.INTEGER, new NumberEventHandler());
        BUS.subscribe(OtherTopics.INTEGER, new IntegerEventHandler());
//      BUS.subscribe(OtherTopics.INTEGER, new VoidEventHandler());

        BUS.subscribe(OtherTopics.VOID, new ObjectEventHandler());
//      BUS.subscribe(OtherTopics.VOID, new NumberEventHandler());
//      BUS.subscribe(OtherTopics.VOID, new IntegerEventHandler());
        BUS.subscribe(OtherTopics.VOID, new VoidEventHandler());

        BUS.subscribe(OtherTopics.EXTENDS_OBJECT, new ObjectEventHandler());
//      BUS.subscribe(OtherTopics.EXTENDS_OBJECT, new NumberEventHandler());
//      BUS.subscribe(OtherTopics.EXTENDS_OBJECT, new IntegerEventHandler());
//      BUS.subscribe(OtherTopics.EXTENDS_OBJECT, new VoidEventHandler());

        BUS.subscribe(OtherTopics.EXTENDS_NUMBER, new ObjectEventHandler());
        BUS.subscribe(OtherTopics.EXTENDS_NUMBER, new NumberEventHandler());
//      BUS.subscribe(OtherTopics.EXTENDS_NUMBER, new IntegerEventHandler());
//      BUS.subscribe(OtherTopics.EXTENDS_NUMBER, new VoidEventHandler());

        BUS.subscribe(OtherTopics.EXTENDS_INTEGER, new ObjectEventHandler());
        BUS.subscribe(OtherTopics.EXTENDS_INTEGER, new NumberEventHandler());
        BUS.subscribe(OtherTopics.EXTENDS_INTEGER, new IntegerEventHandler());
//      BUS.subscribe(OtherTopics.EXTENDS_INTEGER, new VoidEventHandler());

        BUS.subscribe(OtherTopics.EXTENDS_VOID, new ObjectEventHandler());
//      BUS.subscribe(OtherTopics.EXTENDS_VOID, new NumberEventHandler());
//      BUS.subscribe(OtherTopics.EXTENDS_VOID, new IntegerEventHandler());
        BUS.subscribe(OtherTopics.EXTENDS_VOID, new VoidEventHandler());

//      BUS.subscribe(OtherTopics.SUPER_OBJECT, new ObjectEventHandler());
//      BUS.subscribe(OtherTopics.SUPER_OBJECT, new NumberEventHandler());
//      BUS.subscribe(OtherTopics.SUPER_OBJECT, new IntegerEventHandler());
//      BUS.subscribe(OtherTopics.SUPER_OBJECT, new VoidEventHandler());

//      BUS.subscribe(OtherTopics.SUPER_NUMBER, new ObjectEventHandler());
//      BUS.subscribe(OtherTopics.SUPER_NUMBER, new NumberEventHandler());
//      BUS.subscribe(OtherTopics.SUPER_NUMBER, new IntegerEventHandler());
//      BUS.subscribe(OtherTopics.SUPER_NUMBER, new VoidEventHandler());

//      BUS.subscribe(OtherTopics.SUPER_INTEGER, new ObjectEventHandler());
//      BUS.subscribe(OtherTopics.SUPER_INTEGER, new NumberEventHandler());
//      BUS.subscribe(OtherTopics.SUPER_INTEGER, new IntegerEventHandler());
//      BUS.subscribe(OtherTopics.SUPER_INTEGER, new VoidEventHandler());

//      subscribe(OtherTopics.SUPER_VOID, new ObjectEventHandler());
//      subscribe(OtherTopics.SUPER_VOID, new NumberEventHandler());
//      subscribe(OtherTopics.SUPER_VOID, new IntegerEventHandler());
//      subscribe(OtherTopics.SUPER_VOID, new VoidEventHandler());

        BUS.publish(OtherTopics.OBJECT, new Object());
        BUS.publish(OtherTopics.NUMBER, 1.0);
        BUS.publish(OtherTopics.INTEGER, 2);
        BUS.publish(OtherTopics.VOID, null);

//      BUS.publish(OtherTopics.EXTENDS_OBJECT, new Object());
//      BUS.publish(OtherTopics.EXTENDS_NUMBER, 1.0);
//      BUS.publish(OtherTopics.EXTENDS_INTEGER, 2);
        BUS.publish(OtherTopics.EXTENDS_VOID, null);
    }

//    @Test
//    public void compileAndRunWithFilterAndHandler() {
//        BUS.subscribe(new ObjectEventFilter(), new ObjectEventHandler());
////      BUS.subscribe(new ObjectEventFilter(), new NumberEventHandler());
////      BUS.subscribe(new ObjectEventFilter(), new IntegerEventHandler());
////      BUS.subscribe(new ObjectEventFilter(), new VoidEventHandler());
//
//        BUS.subscribe(new NumberEventFilter(), new ObjectEventHandler());
//        BUS.subscribe(new NumberEventFilter(), new NumberEventHandler());
////      BUS.subscribe(new NumberEventFilter(), new IntegerEventHandler());
////      BUS.subscribe(new NumberEventFilter(), new VoidEventHandler());
//
//        BUS.subscribe(new IntegerEventFilter(), new ObjectEventHandler());
//        BUS.subscribe(new IntegerEventFilter(), new NumberEventHandler());
//        BUS.subscribe(new IntegerEventFilter(), new IntegerEventHandler());
////      BUS.subscribe(new IntegerEventFilter(), new VoidEventHandler());
//
//        BUS.subscribe(new VoidEventFilter(), new ObjectEventHandler());
////      BUS.subscribe(new VoidEventFilter(), new NumberEventHandler());
////      BUS.subscribe(new VoidEventFilter(), new IntegerEventHandler());
//        BUS.subscribe(new VoidEventFilter(), new VoidEventHandler());
//
//        BUS.dispatch(OtherTopics.OBJECT, new Object());
//        BUS.dispatch(OtherTopics.NUMBER, 1.0);
//        BUS.dispatch(OtherTopics.INTEGER, 2);
//        BUS.dispatch(OtherTopics.VOID, null);
//
//        // TODO
////        BUS.dispatch(OtherTopics.EXTENDS_OBJECT, new Object());
////        BUS.dispatch(OtherTopics.EXTENDS_NUMBER, 1.0);
////        BUS.dispatch(OtherTopics.EXTENDS_INTEGER, 2);
////        BUS.dispatch(OtherTopics.EXTENDS_VOID, null);
//    }

//    @Test
//    public void sendWithTopic() {
//        EventHandler<String> serverAddressHandler = mock(EventHandler.class);
//        EventHandler<Void> doConnectionHandler = mock(EventHandler.class);
//
//        BUS.subscribe(ConnectionTopics.SERVER_ADDRESS, serverAddressHandler);
//        BUS.subscribe(ConnectionTopics.DO_CONNECT, doConnectionHandler);
//        BUS.subscribe(ConnectionTopics.SERVER_ADDRESS, System.out::println);
//
//        BUS.publish(ConnectionTopics.SERVER_ADDRESS, "mmserver");
//        BUS.publish(ConnectionTopics.DO_CONNECT, null);
//
//        verify(serverAddressHandler).handleEvent(new Event<>(BroadcastChannel.getInstance(), ConnectionTopics
//                .SERVER_ADDRESS, "mmserver"));
//        verify(doConnectionHandler).handleEvent(new Event<>(BroadcastChannel.getInstance(), ConnectionTopics
//                .DO_CONNECT, null));
//    }

//    @Test
//    public void registerAnyReceiveBroadcast() {
//        EventHandler<String> broadcastHandler = mock(EventHandler.class);
//        EventHandler<Void> specificHandler = mock(EventHandler.class);
//
//        BUS.subscribe(BroadcastChannel.getInstance(), ConnectionTopics.SERVER_ADDRESS, broadcastHandler);
//        BUS.subscribe(SpecificChannels.CONNECTION, ConnectionTopics.DO_CONNECT, specificHandler);
//
//        BUS.publish(BroadcastChannel.getInstance(), ConnectionTopics.SERVER_ADDRESS, "mmserver");
//        BUS.publish(BroadcastChannel.getInstance(), ConnectionTopics.DO_CONNECT, null);
//
//        verify(broadcastHandler).handleEvent(new Event<>(BroadcastChannel.getInstance(), ConnectionTopics
//                .SERVER_ADDRESS, "mmserver"));
//        verify(specificHandler).handleEvent(new Event<>(BroadcastChannel.getInstance(), ConnectionTopics.DO_CONNECT,
//                null));
//    }

//    @Test
//    public void registerBroadcastDoNotReceiveSpecific() {
//        EventHandler<String> broadcastHandler = mock(EventHandler.class);
//
//        BUS.subscribe(PARENT_CHANNEL, ConnectionTopics.SERVER_ADDRESS, e -> assertFalse("Should not receive this " +
//                "event: " + e, true));
//        BUS.publish(SpecificChannels.CONNECTION, ConnectionTopics.SERVER_ADDRESS, "mmserver");
//
//        verifyZeroInteractions(broadcastHandler);
//    }

    private enum SpecificChannels implements Channel {
        CONNECTION,
        LOGIN
    }

    private enum EnumTopic implements Topic<Object> {
        TEST,
        TOTO
    }

    private static final class ConnectionTopics {
        public static final Topic<Object> ANY = new SimpleTopic<>("any_connection");
        public static final Topic<String> SERVER_ADDRESS = new SimpleTopic<>("server_address", ANY);
        public static final Topic<Void> DO_CONNECT = new SimpleTopic<>("do_connect", ANY);
    }

    private static final class LoginTopics {
        public static final Topic<Object> ANY = new SimpleTopic<>("any_login");
        public static final Topic<String> USERNAME = new SimpleTopic<>("username", ANY);
        public static final Topic<String> PASSWORD = new SimpleTopic<>("password", ANY);
        public static final Topic<Void> DO_LOGIN = new SimpleTopic<>("do_login", ANY);
    }

    private static final class OtherTopics {
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

    private static class ObjectEventFilter implements EventFilter<Object> {

        @Override
        public boolean accept(Event<Object> event) {
            System.out.println("ObjectEventFilter.accept: " + string(event));
            return true;
        }
    }

    private static class NumberEventFilter implements EventFilter<Number> {

        @Override
        public boolean accept(Event<Number> event) {
            event.getContent().byteValue();
            System.out.println("NumberEventFilter.accept: " + string(event));
            return true;
        }
    }

    private static class IntegerEventFilter implements EventFilter<Integer> {

        @Override
        public boolean accept(Event<Integer> event) {
            int val = event.getContent();
            System.out.println("IntegerEventFilter.accept: " + string(event) + " " + val);
            return true;
        }
    }

    private static class VoidEventFilter implements EventFilter<Void> {

        @Override
        public boolean accept(Event<Void> event) {
            System.out.println("VoidEventFilter.accept: " + string(event));
            return true;
        }
    }

    private static class ObjectEventHandler implements EventHandler<Object> {

        @Override
        public void handleEvent(Event<Object> event) {
            System.out.println("ObjectEventHandler.handleEvent: " + string(event));
        }
    }

    private static class NumberEventHandler implements EventHandler<Number> {

        @Override
        public void handleEvent(Event<Number> event) {
            event.getContent().byteValue();
            System.out.println("NumberEventHandler.handleEvent: " + string(event));
        }
    }

    private static class IntegerEventHandler implements EventHandler<Integer> {

        @Override
        public void handleEvent(Event<Integer> event) {
            int val = event.getContent();
            System.out.println("IntegerEventHandler.handleEvent: " + string(event) + " " + val);
        }
    }

    private static class VoidEventHandler implements EventHandler<Void> {

        @Override
        public void handleEvent(Event<Void> event) {
            System.out.println("VoidEventHandler.handleEvent: " + string(event));
        }
    }
}
