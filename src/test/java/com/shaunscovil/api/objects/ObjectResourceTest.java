package com.shaunscovil.api.objects;

import com.shaunscovil.api.common.JsonUtility;
import com.shaunscovil.api.common.ResourceUrl;
import com.shaunscovil.api.exception.ApiException;
import org.bson.types.ObjectId;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRule;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class ObjectResourceTest {

    @Rule
    public MockitoJUnitRule mockitoJUnitRule = new MockitoJUnitRule(this);

    @Mock
    public ObjectService serviceMock;

    @Mock
    public Map<String, Object> modelMock;

    @Mock
    public ObjectId objectIdMock;

    @Test
    public void createSucceeds() {
        ObjectResource resource = new ObjectResource(serviceMock);
        when(serviceMock.create(anyMapOf(String.class, Object.class))).thenReturn(modelMock);
        String jsonPayload = "{ \"foo\": \"bar\" }";
        String actualResponse = resource.create(jsonPayload);
        String expectedResponse = JsonUtility.serializeJson(modelMock);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test(expected = ApiException.class)
    public void createFailsInvalidJson() {
        ObjectResource resource = new ObjectResource(serviceMock);
        when(serviceMock.create(anyMapOf(String.class, Object.class))).thenReturn(modelMock);
        String malformedJsonPayload = "{ foo: bar }";
        resource.create(malformedJsonPayload);
    }

    @Test
    public void updateSucceeds() {
        ObjectResource resource = new ObjectResource(serviceMock);
        when(serviceMock.update(anyString(), anyMapOf(String.class, Object.class))).thenReturn(modelMock);
        String uid = objectIdMock.toString();
        String jsonPayload = "{ \"foo\": \"bar\" }";
        String actualResponse = resource.update(uid, jsonPayload);
        String expectedResponse = JsonUtility.serializeJson(modelMock);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test(expected = ApiException.class)
    public void updateFailsInvalidJson() {
        ObjectResource resource = new ObjectResource(serviceMock);
        when(serviceMock.update(anyString(), anyMapOf(String.class, Object.class))).thenReturn(modelMock);
        String uid = objectIdMock.toString();
        String malformedJsonPayload = "{ foo: bar }";
        resource.update(uid, malformedJsonPayload);
    }

    @Test
    public void readSucceeds() {
        ObjectResource resource = new ObjectResource(serviceMock);
        when(serviceMock.read(anyString())).thenReturn(modelMock);
        String uid = objectIdMock.toString();
        String actualResponse = resource.read(uid);
        String expectedResponse = JsonUtility.serializeJson(modelMock);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void readResourceUrlsSucceeds() {
        ObjectResource resource = new ObjectResource(serviceMock);
        List<ResourceUrl> resourceUrlsMock = Arrays.asList(new ResourceUrl("UID", "PATH"));
        when(serviceMock.readResourceUrls(anyString())).thenReturn(resourceUrlsMock);
        String actualResponse = resource.readResourceUrls();
        String expectedResponse = JsonUtility.serializeJson(resourceUrlsMock);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void deleteSucceeds() {
        ObjectResource resource = new ObjectResource(serviceMock);
        doNothing().when(serviceMock).delete(anyString());
        String uid = objectIdMock.toString();
        resource.delete(uid);
    }

}
