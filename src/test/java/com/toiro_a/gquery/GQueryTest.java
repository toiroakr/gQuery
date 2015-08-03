package com.toiro_a.gquery;

import com.google.gson.Gson;
import com.toiro_a.gquery.bean.BeanComparator;
import com.toiro_a.gquery.bean.TestGQueryBean;
import com.toiro_a.gquery.bean.TestGSonBean;
import com.toiro_a.gquery.bean.TestGSonBean2;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * GQuery Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>8 3, 2015</pre>
 */
public class GQueryTest extends TestCase {
    Gson gson = new Gson();

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    private String getJson(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String json = "";
        String line = br.readLine();
        do {
            json += line;
            line = br.readLine();
        } while (line != null);
        br.close();
        return json;
    }

    /**
     * Method: select(String json, Class<Target> targetClass)
     */
    @Test
    public void testSelect() throws Exception {
        String json = getJson("src/test/resources/sample.json");
        TestGQueryBean gQueryBean = new GQuery().select(json, TestGQueryBean.class);
        System.out.println(gQueryBean);

        TestGSonBean gsonBean = gson.fromJson(json, TestGSonBean.class);
        System.out.println(gson.toJson(gsonBean));

        assertTrue(BeanComparator.equals(gQueryBean, gsonBean));
    }

    /**
     * Method: selectList(String json, Class<Target> targetClass)
     */
    @Test
    public void testSelectList() throws Exception {
        String json = getJson("src/test/resources/sample2.json");

        List<TestGQueryBean> gQueryBeans = new GQuery().selectList(json, TestGQueryBean.class);
        for (TestGQueryBean gQueryBean : gQueryBeans) {
            System.out.println(gQueryBean);
        }

        TestGSonBean2 gsonBean = gson.fromJson(json, TestGSonBean2.class);
        System.out.println(gson.toJson(gsonBean));

        assertTrue(BeanComparator.equals(gQueryBeans,gsonBean));
    }

    /**
     * Method: get(String json, String query, Class<Target> targetClass)
     */
    @Test
    public void testGet() throws Exception {
        String json = getJson("src/test/resources/sample.json");
        String get = new GQuery().get(json, "results sample img mobile l", String.class);

        assertEquals("http://sample.img/mobille_l.jpg", get);
    }
}
