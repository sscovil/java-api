package com.shaunscovil.api.objects;

import com.shaunscovil.api.common.ResourceUrl;
import com.shaunscovil.api.data.MongoDAO;
import org.bson.types.ObjectId;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class ObjectServiceTest {

    @Rule
    public MockitoJUnitRule mockitoJUnitRule = new MockitoJUnitRule(this);

    @Mock
    public MongoDAO daoMock;

    @Mock
    public Map<String, Object> modelMock;

    @Mock
    public List<ResourceUrl> resourceUrlsMock;

    @Mock
    public ObjectId objectIdMock;

    @Test
    public void createSucceeds() {
        ObjectService service = new ObjectService(daoMock);
        Map<String, Object> model = new HashMap<>();
        model.put("uid", objectIdMock.toString());
        model.put("foo", "bar");
        when(daoMock.create(anyMapOf(String.class, Object.class))).thenReturn(model);
        Map<String, Object> response = service.create(model);
        assertNull(response.get("uid"));
        assertEquals("bar", response.get("foo"));
    }

    @Test
    public void updateSucceeds() {
        ObjectService service = new ObjectService(daoMock);
        String uid = objectIdMock.toString();
        Map<String, Object> model = new HashMap<>();
        model.put("uid", uid);
        model.put("foo", "bar");
        when(daoMock.update(anyMapOf(String.class, Object.class))).thenReturn(model);
        Map<String, Object> response = service.update(uid, model);
        assertEquals(uid, response.get("uid"));
        assertEquals("bar", response.get("foo"));
    }

    @Test
    public void readSucceeds() {
        ObjectService service = new ObjectService(daoMock);
        when(daoMock.read(anyString())).thenReturn(modelMock);
        String uid = objectIdMock.toString();
        Map<String, Object> response = service.read(uid);
        assertEquals(modelMock, response);
    }

    @Test
    public void readResourceUrlsSucceeds() {
        ObjectService service = new ObjectService(daoMock);
        when(daoMock.readResourceUrls(anyString())).thenReturn(resourceUrlsMock);
        List<ResourceUrl> response = service.readResourceUrls("PATH");
        assertEquals(resourceUrlsMock, response);
    }

    @Test
    public void delete() {
        ObjectService service = new ObjectService(daoMock);
        doNothing().when(daoMock).delete(anyString());
        String uid = objectIdMock.toString();
        service.delete(uid);
    }

}
