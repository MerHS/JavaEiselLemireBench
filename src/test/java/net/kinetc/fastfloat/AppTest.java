package net.kinetc.fastfloat;

import org.apache.commons.io.IOUtils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    final static String[] rawFloats = {
            "33408",
            "1245.6333e-2", "12.5", "-23561.4324e5", "0.235E4", "123535.35226783411112E-4",
            "123535.3522678341111E-4", "123535.352267834111E-4", "-123535.3522678341111E-4",
            "-123535.35226783411111E-4", "-123535.352267834111111E-4", "-1465678684576765.4353e1", "0.00001",
            "-0.000000000000000000000000045346", "1451.6743435e-5", "34524.62436", "-763457.2435",
            "00000000000232323231231232323232323232323232323.232323e11",
            "00000000000000000000000000000000000000001312e+1", "-00000.00000000113e5",
            "0.00000000003333333333333333333333333333333333333333", "33333333333333.333e1",
            "111111.111111111111111111111111111e2", "111111111111111111111111111111111111111111",
            "111111111111111111111111111111111111111111.1111111", "11111.111111111111111111111111111111",
            "11111111.111111111111111111111111111e-2",
            "111111111111111111111111111111111111.11111111111111111111111111e-2",
            "000000000000.00000000000000000000000000000000", "-1.0e3", "0.0", "0.0E3", "345264.6835e3" };

    protected void testFloatStrings(String[] floats) {
        for (String fstr : floats) {
            double javaFloat = Double.parseDouble(fstr);
            double eisel = EiselLemire.parseDouble(fstr);

            assertTrue("parse error on " + (i + 1) + "th '" + fstr + "':\n  java: " + javaFloat + " != eisel: " + eisel,
                    javaFloat == eisel);
        }
    }

    protected void runTestFile(String path) throws IOException {
        InputStream stream = this.getClass().getResourceAsStream(path);
        assertFalse(path + " does not exist.", stream == null);

        String testFile = IOUtils.toString(stream, "UTF-8");
        String[] floats = testFile.split("\\r?\\n");

        testFloatStrings(floats);
    }

    @Test
    public void basicParseTest() {
        testFloatStrings(rawFloats);
    }

    @Test
    public void resourceTest() throws IOException {
        runTestFile("/canada.txt");
        runTestFile("/error.txt");
        runTestFile("/mesh.txt");
        runTestFile("/synthetic.txt");
    }
}
