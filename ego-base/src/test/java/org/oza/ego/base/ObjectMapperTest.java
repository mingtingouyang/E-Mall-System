package org.oza.ego.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.oza.ego.base.vo.EgoResult;

import java.io.IOException;

public class ObjectMapperTest {
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void test() throws IOException {
        String json = "{\"status\":200,\"msg\":\"Test Object\",\"data\":{\"message\": \"Welcome to Awesome JSON Viewer.\",\"status_code\": 200}}";
        JsonNode jsonNode = mapper.readTree(json);
        EgoResult egoResult = EgoResult.formatToEgoResult(json, TestObject.class);
        System.out.println(egoResult);
    }

    @Test
    public void ArrayTest() {
        String json = "{\"status\":200,\"msg\":\"Test Object\",\"data\":[{\"message\": \"Welcome to Awesome JSON Viewer.\",\"status_code\": 200},{\"message\": \"test array\",\"status_code\": 500}]}";
        EgoResult egoResult = EgoResult.formatToEgoResult(json, TestObject.class);
        System.out.println(egoResult);
    }


}
