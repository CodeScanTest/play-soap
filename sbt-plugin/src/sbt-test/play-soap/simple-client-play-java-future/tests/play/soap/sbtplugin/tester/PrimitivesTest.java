/*
 * Copyright (C) 2015-2016 Lightbend Inc. <https://www.lightbend.com>
 */
package play.soap.sbtplugin.tester;

import java.lang.RuntimeException;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.function.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.*;
import org.apache.cxf.jaxws.EndpointImpl;
import org.junit.*;
import play.soap.testservice.client.*;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.F;

import static org.junit.Assert.*;
import static play.test.Helpers.*;

/*
 * THIS FILE IS AUTO GENERATED. DO NOT EDIT THIS FILE MANUALLY.
 *
 * Run 'generate-primitives.py' to regenerate it.
 */

public class PrimitivesTest {


    @Test
    public void booleanOp() {
        withClient(client ->
            assertEquals((java.lang.Boolean) true, await(client.booleanOp(true)))
        );
    }
    @Test
    public void booleanSequence() {
        withClient(client ->
            assertEquals(Arrays.asList(true, true, true), await(client.booleanSequence(Arrays.asList(true, true))))
        );
    }
    @Test
    public void byteOp() {
        withClient(client ->
            assertEquals((java.lang.Byte) (byte) 1, await(client.byteOp((byte) 1)))
        );
    }
    @Test
    public void byteSequence() {
        withClient(client ->
            assertEquals(Arrays.asList((byte) 1, (byte) 1, (byte) 1), await(client.byteSequence(Arrays.asList((byte) 1, (byte) 1))))
        );
    }
    @Test
    public void doubleOp() {
        withClient(client ->
            assertEquals((java.lang.Double) 1.0d, await(client.doubleOp(1.0d)))
        );
    }
    @Test
    public void doubleSequence() {
        withClient(client ->
            assertEquals(Arrays.asList(1.0d, 1.0d, 1.0d), await(client.doubleSequence(Arrays.asList(1.0d, 1.0d))))
        );
    }
    @Test
    public void floatOp() {
        withClient(client ->
            assertEquals((java.lang.Float) 1.0f, await(client.floatOp(1.0f)))
        );
    }
    @Test
    public void floatSequence() {
        withClient(client ->
            assertEquals(Arrays.asList(1.0f, 1.0f, 1.0f), await(client.floatSequence(Arrays.asList(1.0f, 1.0f))))
        );
    }
    @Test
    public void intOp() {
        withClient(client ->
            assertEquals((java.lang.Integer) 1, await(client.intOp(1)))
        );
    }
    @Test
    public void intSequence() {
        withClient(client ->
            assertEquals(Arrays.asList(1, 1, 1), await(client.intSequence(Arrays.asList(1, 1))))
        );
    }
    @Test
    public void longOp() {
        withClient(client ->
            assertEquals((java.lang.Long) 1L, await(client.longOp(1L)))
        );
    }
    @Test
    public void longSequence() {
        withClient(client ->
            assertEquals(Arrays.asList(1L, 1L, 1L), await(client.longSequence(Arrays.asList(1L, 1L))))
        );
    }
    @Test
    public void shortOp() {
        withClient(client ->
            assertEquals((java.lang.Short) (short) 1, await(client.shortOp((short) 1)))
        );
    }
    @Test
    public void shortSequence() {
        withClient(client ->
            assertEquals(Arrays.asList((short) 1, (short) 1, (short) 1), await(client.shortSequence(Arrays.asList((short) 1, (short) 1))))
        );
    }
    private static <T> T await(F.Promise<T> promise) {
        return promise.get(10000); // 10 seconds
    }

    private static void withClient(Consumer<Primitives> block) {
        withApp(app -> {
            Primitives client = app.injector().instanceOf(PrimitivesService.class).getPrimitives();
            block.accept(client);
        });
    }

    private static void withApp(Consumer<Application> block) {
        withService(port -> {
            GuiceApplicationBuilder builder = new GuiceApplicationBuilder()
                    .configure("play.soap.address", "http://localhost:"+port+"/primitives")
                    .configure("play.soap.debugLog", true);
            Application app = builder.build();
            running(app, () -> block.accept(app));
        });
    }

    private static void withService(Consumer<Integer> block) {
        final int port = findAvailablePort();
        final Endpoint endpoint = Endpoint.publish(
                "http://localhost:"+port+"/primitives",
                new play.soap.testservice.PrimitivesImpl());
        try {
            block.accept(port);
        } finally {
            endpoint.stop();
            // Need to shutdown whole engine.  Note, Jetty's shutdown doesn't seem to happen synchronously, have to wait
            // a few seconds for the port to be released. This is why we use a different port each time.
            ((EndpointImpl) endpoint).getBus().shutdown(true);
        }
    }

    private static int findAvailablePort() {
        try {
            final ServerSocket socket = new ServerSocket(0);
            try {
                return socket.getLocalPort();
            } finally {
                socket.close();
            }
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

}