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
import java.util.Arrays;
import java.util.List;

import static com.toiro_a.gquery.bean.TestGQueryBean.L;
import static com.toiro_a.gquery.bean.TestGQueryBean.M;
import static com.toiro_a.gquery.bean.TestGQueryBean.S;

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


        assertEquals(gsonBean.results.sample.name, gQueryBean.name);
        assertEquals(gsonBean.results.sample.img.mobile.l, gQueryBean.mobileImgs.get(L));
        assertEquals(gsonBean.results.sample.img.mobile.s, gQueryBean.mobileImgs.get(S));
        assertEquals(gsonBean.results.sample.img.pc.l, gQueryBean.pcImgs.get(L));
        assertEquals(gsonBean.results.sample.img.pc.m, gQueryBean.pcImgs.get(M));
        assertEquals(gsonBean.results.sample.img.pc.s, gQueryBean.pcImgs.get(S));
        assertEquals(gsonBean.results.sample.a, gQueryBean.a);
        assertEquals(gsonBean.results.sample.b[0][0], gQueryBean.b[0][0]);
        assertEquals(gsonBean.results.sample.b[0][1], gQueryBean.b[0][1]);
        assertEquals(gsonBean.results.sample.b[0][2], gQueryBean.b[0][2]);
        assertEquals(gsonBean.results.sample.b[1][0], gQueryBean.b[1][0]);
        assertEquals(gsonBean.results.sample.b[1][1], gQueryBean.b[1][1]);
        assertEquals(gsonBean.results.sample.b[1][2], gQueryBean.b[1][2]);
        assertEquals(gsonBean.results.sample.c.get(0).foo, (int)gQueryBean.c.get(0).get("foo"));
        assertEquals(gsonBean.results.sample.c.get(0).bar, (int)gQueryBean.c.get(0).get("bar"));
        assertEquals(gsonBean.results.sample.c.get(1).foo, (int)gQueryBean.c.get(1).get("foo"));
        assertEquals(gsonBean.results.sample.c.get(1).bar, (int)gQueryBean.c.get(1).get("bar"));
        assertEquals(gsonBean.results.sample.d.get(0).bar, gQueryBean.dBar.get(0));
        assertEquals(gsonBean.results.sample.d.get(1).bar, gQueryBean.dBar.get(1));
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

        TestGSonBean2 gsonBeans = gson.fromJson(json, TestGSonBean2.class);
        System.out.println(gson.toJson(gsonBeans));

        for(int i = 0; i < gQueryBeans.size(); i++) {
            TestGQueryBean gQueryBean = gQueryBeans.get(i);
            TestGSonBean2.Result.Sample gsonBean = gsonBeans.results.sample.get(i);
            assertEquals(gsonBean.name, gQueryBean.name);
            assertEquals(gsonBean.img.mobile.l, gQueryBean.mobileImgs.get(L));
            assertEquals(gsonBean.img.mobile.s, gQueryBean.mobileImgs.get(S));
            assertEquals(gsonBean.img.pc.l, gQueryBean.pcImgs.get(L));
            assertEquals(gsonBean.img.pc.m, gQueryBean.pcImgs.get(M));
            assertEquals(gsonBean.img.pc.s, gQueryBean.pcImgs.get(S));
            assertEquals(gsonBean.a, gQueryBean.a);
            assertEquals(gsonBean.b[0][0], gQueryBean.b[0][0]);
            assertEquals(gsonBean.b[0][1], gQueryBean.b[0][1]);
            assertEquals(gsonBean.b[0][2], gQueryBean.b[0][2]);
            assertEquals(gsonBean.b[1][0], gQueryBean.b[1][0]);
            assertEquals(gsonBean.b[1][1], gQueryBean.b[1][1]);
            assertEquals(gsonBean.b[1][2], gQueryBean.b[1][2]);
            assertEquals(gsonBean.c.get(0).foo, (int)gQueryBean.c.get(0).get("foo"));
            assertEquals(gsonBean.c.get(0).bar, (int)gQueryBean.c.get(0).get("bar"));
            assertEquals(gsonBean.c.get(1).foo, (int)gQueryBean.c.get(1).get("foo"));
            assertEquals(gsonBean.c.get(1).bar, (int)gQueryBean.c.get(1).get("bar"));
            assertEquals(gsonBean.d.get(0).bar, gQueryBean.dBar.get(0));
            assertEquals(gsonBean.d.get(1).bar, gQueryBean.dBar.get(1));
        }
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
