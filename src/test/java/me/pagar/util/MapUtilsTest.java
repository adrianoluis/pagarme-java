package me.pagar.util;

import me.pagar.Balance;
import me.pagar.BaseTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapUtilsTest extends BaseTest {

    private String query = "keyWithValue=123&keyWithoutValue=";

    private String queryEscaped = "keyWith%5Bvalue%5D=123&keyWithout%5Bvalue%5D=";

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testMapToQuery() throws Exception {
        final Map<String, Object> values = new LinkedHashMap<String, Object>();
        values.put("keyWithValue", "123");
        values.put("keyWithoutValue", "");

        Assert.assertEquals(query, MapUtils.mapToQuery(values));
    }

    @Test
    public void testMapToQueryEscaped() throws Exception {
        final Map<String, Object> values = new LinkedHashMap<String, Object>();
        values.put("keyWith[value]", "123");
        values.put("keyWithout[value]", null);

        Assert.assertEquals(queryEscaped, MapUtils.mapToQuery(values));
    }

    @Test
    public void testQueryToMap() throws Exception {
        final Map<String, String> values = MapUtils.queryToMap(query);

        Assert.assertEquals("123", values.get("keyWithValue"));
        Assert.assertNull(values.get("keyWithoutValue"));
    }

    @Test
    public void testQueryToMapEscaped() throws Exception {
        final Map<String, String> values = MapUtils.queryToMap(queryEscaped);

        Assert.assertEquals("123", values.get("keyWith[value]"));
        Assert.assertNull(values.get("keyWithout[value]"));
    }

    @Test
    public void testObjectToMap() throws Exception {
        final Balance balance = new Balance();

        final Map<String, Object> expected = new LinkedHashMap<String, Object>();
        expected.put("available", null);
        expected.put("createdAt", null);
        expected.put("id", null);
        expected.put("transferred", null);
        expected.put("waitingFunds", null);

        Assert.assertEquals(expected, MapUtils.objectToMap(balance));
    }

}
