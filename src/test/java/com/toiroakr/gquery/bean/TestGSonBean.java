package com.toiroakr.gquery.bean;

import java.util.List;

public class TestGSonBean {
    public Result results;

    public class Result {
        public Sample sample;

        public class Sample {
            public String name;
            public Image img;
            public boolean a;
            public int[][] b;
            public List<C> c;
            public List<D> d;

            public class Image {
                public ImgUrls mobile;
                public ImgUrls pc;

                public class ImgUrls {
                    public String s;
                    public String m;
                    public String l;
                }
            }

            public class C {
                public int foo;
                public int bar;
            }

            public class D {
                public String foo;
                public Object bar;
            }
        }
    }
}
