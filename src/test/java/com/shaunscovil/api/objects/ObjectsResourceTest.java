package com.shaunscovil.api.objects;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import com.shaunscovil.api.Main;
import org.glassfish.grizzly.http.server.HttpServer;

import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ObjectsResourceTest {

    private HttpServer server;
    private Client client;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        server = Main.startServer();
        client = ClientBuilder.newClient();
        client.register(new MoxyJsonFeature());
        target = client.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        client.close();
        server.shutdownNow();
    }

    @Test
    public void testGetIt() {
        String responseMsg = target.path("objects/test").request().get(String.class);
        assertEquals("Got it!", responseMsg);
    }

}
