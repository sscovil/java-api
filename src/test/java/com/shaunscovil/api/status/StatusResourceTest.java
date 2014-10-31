package com.shaunscovil.api.status;

import com.shaunscovil.api.Main;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import static org.junit.Assert.assertEquals;

public class StatusResourceTest {

    private HttpServer server;

    private Client client;

    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        this.server = Main.startServer();
        this.client = ClientBuilder.newClient();
        this.target = client.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        client.close();
        server.shutdownNow();
    }

    @Test
    public void checkStatusSucceeds() {
        String response = target.path("status")
                .request(MediaType.TEXT_PLAIN)
                .get(String.class);

        assertEquals(StatusResource.STATUS_OK_MESSAGE, response);
    }

}
