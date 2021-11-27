package net.kinetc.fastfloat;

import java.time.LocalTime;

/**
 * Hello world!
 *
 */
public class App {
    final static String[] floats = { "1245.6322e-2", "12.5", "-23561.4324e5", "0.235E4", "123535.35226783411112E-4",
            "-1465678684576765.4353e1", "0.00001", "-0.000000000000000000000000045346", "1451.6743435e-5",
            "34524.62436", "-763457.2435", "00000000000232323231231232323232323232323232323.232323e11",
            "00000000000000000000000000000000000000001312e+1", "-00000.00000000113e5",
            "0.00000000003333333333333333333333333333333333333333", "33333333333333.333e1",
            "111111.111111111111111111111111111e2", "111111111111111111111111111111111111111111",
            "111111111111111111111111111111111111111111.1111111", "11111.111111111111111111111111111111",
            "11111111.111111111111111111111111111e-2",
            "111111111111111111111111111111111111.11111111111111111111111111e-2",
            "000000000000.00000000000000000000000000000000", "-1.0e3", "0.0", "0.0E3", "345264.6835e3" };

    public static void main(String[] args) {
        int countEqual = 0;
        // result of built in string to double parser
        System.out.println("-------Built In parse to double-------");

        for (int i = 0; i < floats.length; i++) {
            if (Double.parseDouble(floats[i]) != EiselLemire.parseDouble(floats[i])) {
                System.out.println(floats[i] + " wrong:" + Double.parseDouble(floats[i]) + " / "
                        + EiselLemire.parseDouble(floats[i]));
            }
        }

        // number of results matched out of total test case
        System.out.println(countEqual + " out of " + floats.length + " succeed");
    }
}
