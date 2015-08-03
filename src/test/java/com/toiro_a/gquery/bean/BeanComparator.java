package com.toiro_a.gquery.bean;

import java.util.Arrays;
import java.util.List;

import static com.toiro_a.gquery.bean.TestGQueryBean.*;

/**
 * Created by higuchiakira on 2015/08/03.
 */
public class BeanComparator {
    public static boolean equals(TestGQueryBean gquery, TestGSonBean gson) {
        boolean equal = true;
        equal = equal & gquery.name.equals(gson.results.sample.name);
        equal = equal & gquery.mobileImgs.get(L).equals(gson.results.sample.img.mobile.l);
        equal = equal & gquery.mobileImgs.get(S).equals(gson.results.sample.img.mobile.s);
        equal = equal & gquery.pcImgs.get(L).equals(gson.results.sample.img.pc.l);
        equal = equal & gquery.pcImgs.get(M).equals(gson.results.sample.img.pc.m);
        equal = equal & gquery.pcImgs.get(S).equals(gson.results.sample.img.pc.s);
        equal = equal & gquery.a == gson.results.sample.a;
        equal = equal & Arrays.equals(gquery.b[0], gson.results.sample.b[0]);
        equal = equal & Arrays.equals(gquery.b[1], gson.results.sample.b[1]);
        equal = equal & gquery.c.get(0).get("foo") == gson.results.sample.c.get(0).foo;
        equal = equal & gquery.c.get(0).get("bar") == gson.results.sample.c.get(0).bar;
        equal = equal & gquery.c.get(1).get("foo") == gson.results.sample.c.get(1).foo;
        equal = equal & gquery.c.get(1).get("bar") == gson.results.sample.c.get(1).bar;
        equal = equal & gquery.dBar.get(0) == gson.results.sample.d.get(0).bar;
        equal = equal & gquery.dBar.get(1) == gson.results.sample.d.get(1).bar;
        return equal;
    }

    public static boolean equals(List<TestGQueryBean> gqueries, TestGSonBean2 gsons) {
        boolean equal = true;
        for(int i = 0; i < gqueries.size(); i++) {
            TestGQueryBean gquery = gqueries.get(i);
            TestGSonBean2.Result.Sample gson = gsons.results.sample.get(i);
            equal = equal & gquery.name.equals(gson.name);
            equal = equal & gquery.mobileImgs.get(L).equals(gson.img.mobile.l);
            equal = equal & gquery.mobileImgs.get(S).equals(gson.img.mobile.s);
            equal = equal & gquery.pcImgs.get(L).equals(gson.img.pc.l);
            equal = equal & gquery.pcImgs.get(M).equals(gson.img.pc.m);
            equal = equal & gquery.pcImgs.get(S).equals(gson.img.pc.s);
            equal = equal & gquery.a == gson.a;
            equal = equal & Arrays.equals(gquery.b[0], gson.b[0]);
            equal = equal & Arrays.equals(gquery.b[1], gson.b[1]);
            equal = equal & gquery.c.get(0).get("foo") == gson.c.get(0).foo;
            equal = equal & gquery.c.get(0).get("bar") == gson.c.get(0).bar;
            equal = equal & gquery.c.get(1).get("foo") == gson.c.get(1).foo;
            equal = equal & gquery.c.get(1).get("bar") == gson.c.get(1).bar;
            equal = equal & gquery.dBar.get(0) == gson.d.get(0).bar;
            equal = equal & gquery.dBar.get(1) == gson.d.get(1).bar;
        }
        return equal;
    }
}
