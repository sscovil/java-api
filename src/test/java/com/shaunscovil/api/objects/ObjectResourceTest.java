package com.shaunscovil.api.objects;

import com.shaunscovil.api.common.JsonUtility;
import com.shaunscovil.api.common.ResourceUrl;
import com.shaunscovil.api.exception.ApiException;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRule;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class ObjectResourceTest {

    public static final String JSON_PAYLOAD = "{ \"foo\": \"bar\" }";

    public static final String MALFORMED_JSON_PAYLOAD = "{ foo: bar }";

    public ObjectResource resource;

    @Rule
    public MockitoJUnitRule mockitoJUnitRule = new MockitoJUnitRule(this);

    @Mock
    public ObjectService serviceMock;

    @Mock
    public ObjectId objectIdMock;

    @Mock
    public Map<String, Object> responseMock;

    @Mock
    public ArrayList<ResourceUrl> resourceUrlsMock;

    @Before
    public void setUp() throws Exception {
        this.resource = new ObjectResource(serviceMock);
        this.responseMock.put("uid", objectIdMock.toString());
        this.responseMock.put("foo", "bar");
        this.resourceUrlsMock.add(new ResourceUrl("UID", "PATH"));
    }

    @Test
    public void createSucceeds() {
        when(serviceMock.create(anyMap())).thenReturn(responseMock);
        String response = resource.create(JSON_PAYLOAD);
        String expected = JsonUtility.serializeJson(responseMock);
        assertEquals(expected, response);
    }

    @Test
    public void createFailsInvalidJson() {
        when(serviceMock.create(anyMap())).thenReturn(responseMock);
        try {
            resource.create(MALFORMED_JSON_PAYLOAD);
            fail("ApiException not thrown");
        }
        catch (ApiException e) {
            assertEquals(JsonUtility.DESERIALIZATION_ERROR_MESSAGE, e.getMessage());
            assertEquals(Response.Status.BAD_REQUEST, e.getStatus());
        }
    }

    @Test
    public void updateSucceeds() {
        when(serviceMock.update(anyString(), anyMap())).thenReturn(responseMock);
        String response = resource.update(objectIdMock.toString(), JSON_PAYLOAD);
        String expected = JsonUtility.serializeJson(responseMock);
        assertEquals(expected, response);
    }

    @Test
    public void updateFailsInvalidJson() {
        when(serviceMock.update(anyString(), anyMap())).thenReturn(responseMock);
        try {
            resource.update(objectIdMock.toString(), MALFORMED_JSON_PAYLOAD);
            fail("ApiException not thrown");
        }
        catch (ApiException e) {
            assertEquals(JsonUtility.DESERIALIZATION_ERROR_MESSAGE, e.getMessage());
            assertEquals(Response.Status.BAD_REQUEST, e.getStatus());
        }
    }

    @Test
    public void readSucceeds() {
        when(serviceMock.read(anyString())).thenReturn(responseMock);
        String response = resource.read(objectIdMock.toString());
        String expected = JsonUtility.serializeJson(responseMock);
        assertEquals(expected, response);
    }

    @Test
    public void readResourceUrlsSucceeds() {
        when(serviceMock.readResourceUrls(anyString())).thenReturn(resourceUrlsMock);
        String response = resource.readResourceUrls();
        String expected = JsonUtility.serializeJson(resourceUrlsMock);
        assertEquals(expected, response);
    }

    @Test
    public void deleteSucceeds() {
        doNothing().when(serviceMock).delete(anyString());
        resource.delete(objectIdMock.toString());
    }

}
