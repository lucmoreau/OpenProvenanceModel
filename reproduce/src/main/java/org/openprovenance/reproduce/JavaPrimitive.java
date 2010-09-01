package org.openprovenance.reproduce;

import java.util.HashMap;
import java.io.File;
import java.net.URL;

public class JavaPrimitive {

    public void invoke(String primitive,
                       HashMap<String,Object> arguments,
                       HashMap<String,Object> results) {
        try {
            Object thisResult=null;
            if (primitive.equals("http://openprovenance.org/reproducibility/java#sum")) {
                Integer arg1=(Integer) arguments.get("summand1");
                Integer arg2=(Integer) arguments.get("summand2");
                thisResult=arg1+arg2;
                results.put("out", thisResult);
                return;
            }

            if (primitive.equals("http://openprovenance.org/reproducibility/java#multiplication")) {
                Integer arg1=(Integer) arguments.get("factor1");
                Integer arg2=(Integer) arguments.get("factor2");
                thisResult=arg1*arg2;
                results.put("product", thisResult);
                return;
            }

            if (primitive.equals("http://openprovenance.org/reproducibility/java#division")) {
                Integer arg1=(Integer) arguments.get("dividend");
                Integer arg2=(Integer) arguments.get("divisor");
                thisResult=arg1/arg2;
                results.put("quotient", thisResult);
                return;
            }

            if (primitive.equals("http://openprovenance.org/reproducibility/java#align_warp")) {
                URL arg1=(URL) arguments.get("img");
                URL arg2=(URL) arguments.get("imgRef");

                if (arg1.toString().endsWith("anatomy1.img")) {
                    thisResult=new URL("http://www.ipaw.info/challenge/warp1.warp");
                } else if  (arg1.toString().endsWith("anatomy2.img")) {
                    thisResult=new URL("http://www.ipaw.info/challenge/warp2.warp");
                } else if  (arg1.toString().endsWith("anatomy3.img")) {
                    thisResult=new URL("http://www.ipaw.info/challenge/warp3.warp");
                } else if  (arg1.toString().endsWith("anatomy4.img")) {
                    thisResult=new URL("http://www.ipaw.info/challenge/warp4.warp");
                }

                results.put("out", thisResult);
                return;
            }
            if (primitive.equals("http://openprovenance.org/reproducibility/java#reslice")) {
                URL arg1=(URL) arguments.get("in");

                if (arg1.toString().endsWith("warp1.warp")) {
                    results.put("img", new URL("http://www.ipaw.info/challenge/resliced1.img"));
                    results.put("hdr", new URL("http://www.ipaw.info/challenge/resliced1.hdr"));
                } else if (arg1.toString().endsWith("warp2.warp")) {
                    results.put("img", new URL("http://www.ipaw.info/challenge/resliced2.img"));
                    results.put("hdr", new URL("http://www.ipaw.info/challenge/resliced2.hdr"));
                } else if (arg1.toString().endsWith("warp3.warp")) {
                    results.put("img", new URL("http://www.ipaw.info/challenge/resliced3.img"));
                    results.put("hdr", new URL("http://www.ipaw.info/challenge/resliced3.hdr"));
                } else if (arg1.toString().endsWith("warp4.warp")) {
                    results.put("img", new URL("http://www.ipaw.info/challenge/resliced4.img"));
                    results.put("hdr", new URL("http://www.ipaw.info/challenge/resliced4.hdr"));
                }
                return;
            }

            if (primitive.equals("http://openprovenance.org/reproducibility/java#softmean")) {
                URL arg1=(URL) arguments.get("i1");
                URL arg2=(URL) arguments.get("h1");
                URL arg3=(URL) arguments.get("i2");
                URL arg4=(URL) arguments.get("h2");
                URL arg5=(URL) arguments.get("i3");
                URL arg6=(URL) arguments.get("h3");
                URL arg7=(URL) arguments.get("i4");
                URL arg8=(URL) arguments.get("h4");


                results.put("img", new URL("http://www.ipaw.info/challenge/atlas.img"));
                results.put("hdr", new URL("http://www.ipaw.info/challenge/atlas.hdr"));
                return;
            }

            if (primitive.equals("http://openprovenance.org/reproducibility/java#slicer")) {
                URL arg1=(URL) arguments.get("img");
                URL arg2=(URL) arguments.get("hdr");
                String arg3=(String) arguments.get("param");

                if (arg3.startsWith("-x")) {
                    results.put("out", new URL("http://www.ipaw.info/challenge/atlas-x.pgm"));
                } else if (arg3.startsWith("-y")) {
                    results.put("out", new URL("http://www.ipaw.info/challenge/atlas-y.pgm"));
                } else if (arg3.startsWith("-z")) {
                    results.put("out", new URL("http://www.ipaw.info/challenge/atlas-z.pgm"));
                }
                return;
            }


            if (primitive.equals("http://openprovenance.org/reproducibility/java#convert")) {
                URL arg1=(URL) arguments.get("in");

                if (arg1.toString().endsWith("-x.pgm")) {
                    results.put("out", new URL("http://www.ipaw.info/challenge/atlas-x.gif"));
                } else if (arg1.toString().endsWith("-y.pgm")) {
                    results.put("out", new URL("http://www.ipaw.info/challenge/atlas-y.gif"));
                } else if (arg1.toString().endsWith("-z.pgm")) {
                    results.put("out", new URL("http://www.ipaw.info/challenge/atlas-z.gif"));
                }
                return;
            }
        } catch (java.net.MalformedURLException e) {
            e.printStackTrace();
        }

        System.out.println("**** Not found a primitive or bad url");

        throw new UnsupportedOperationException(primitive);
    }
}
