package com.dataProviders;

import com.fasterxml.jackson.databind.JsonNode;
import org.testng.annotations.DataProvider;
import utils.Utils;

import java.util.Iterator;
import java.util.Map;

public class DataProviders {







    //here it is much better and more dynamic, i am getting the node i want and create an iterator all over it,
    // and then store the values in a 2D array.
    @DataProvider(name = "emptyCheckoutData")
    public Object[] emptyCheckoutData() {

        JsonNode root = Utils.readAsJsonResource("testData/comTestData.json");
        JsonNode emptyCheckoutNode = root.get("checkout").get("emptyCheckout");

        return extract2DArrayData(emptyCheckoutNode);
    }


    public Object[] extract2DArrayData(JsonNode node) {
        //here i create an empty array of length of the node that has test data combination
        Object[] data = new Object[node.size()];

        Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
        //looping across it and getting each entry of the
        int i = 0;
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            data[i++] = entry.getValue();
        }

        return data;
    }
}
