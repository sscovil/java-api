package com.shaunscovil.api.objects;

import com.shaunscovil.api.common.ResourceUrl;
import com.shaunscovil.api.data.mongodb.MongoDAO;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRule;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class ObjectServiceTest {

    public ObjectService service;

    @Rule
    public MockitoJUnitRule mockitoJUnitRule = new MockitoJUnitRule(this);

    @Mock
    public MongoDAO<Map> daoMock;

    @Mock
    public ObjectId objectIdMock;

    @Mock
    public Map modelMock;

    @Mock
    public Map<String, Object> responseMock;

    @Mock
    public List<ResourceUrl> resourceUrlsMock;

    @Before
    public void setUp() throws Exception {
        this.service = new ObjectService(daoMock);
    }

    @Test
    public void createSucceeds() {
        when(daoMock.create(anyMap())).thenReturn(responseMock);
        Map<String, Object> response = service.create(modelMock);
        assertEquals(responseMock, response);
    }

    @Test
    public void updateSucceeds() {
        when(daoMock.update(anyString(), anyMap())).thenReturn(responseMock);
        Map<String, Object> response = service.update(objectIdMock.toString(), modelMock);
        assertEquals(responseMock, response);
    }

    @Test
    public void readSucceeds() {
        when(daoMock.read(anyString())).thenReturn(responseMock);
        Map<String, Object> response = service.read(objectIdMock.toString());
        assertEquals(responseMock, response);
    }

    @Test
    public void readResourceUrlsSucceeds() {
        when(daoMock.readResourceUrls(anyString())).thenReturn(resourceUrlsMock);
        List<ResourceUrl> response = service.readResourceUrls("PATH");
        assertEquals(resourceUrlsMock, response);
    }

    @Test
    public void delete() {
        doNothing().when(daoMock).delete(anyString());
        String uid = objectIdMock.toString();
        service.delete(uid);
    }

}
