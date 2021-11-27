package net.kinetc.fastfloat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("file is not detected.\n  USAGE: java -jar fastfloat.jar path/to/float_list.txt");
        }

        ArrayList<String> floats = new ArrayList<String>();
        long[] builtinTimes = new long[1000];
        long[] eiselTimes = new long[1000];

        try {
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                floats.add(line);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        System.out.println(String.format("Test %s (%d floats)\n", args[0], floats.size()));

        System.out.println("warm up 10 times...");

        for (int i = 0; i < 5; i++) {
            for (String fstr : floats) {
                double d = Double.parseDouble(fstr);
            }
        }

        for (int i = 0; i < 5; i++) {
            for (String fstr : floats) {
                double d = EiselLemire.parseDouble(fstr);
            }
        }

        System.out.println("running built-in parseDouble...");

        for (int i = 0; i < 10; i++) {
            for (String fstr : floats) {
                double d = Double.parseDouble(fstr);
            }
        }

        for (int i = 0; i < 1000; i++) {
            long startTime = System.nanoTime();
            for (String fstr : floats) {
                double d = Double.parseDouble(fstr);
            }
            builtinTimes[i] = System.nanoTime() - startTime;
        }

        System.out.println("running Eisel-Lemire parseDouble...");

        for (int i = 0; i < 10; i++) {
            for (String fstr : floats) {
                double d = EiselLemire.parseDouble(fstr);
            }
        }

        for (int i = 0; i < 1000; i++) {
            long startTime = System.nanoTime();
            for (String fstr : floats) {
                double d = EiselLemire.parseDouble(fstr);
            }
            eiselTimes[i] = System.nanoTime() - startTime;
        }

        System.out.println(String.format("--- results ---\n"));
        Arrays.sort(builtinTimes);
        Arrays.sort(eiselTimes);

        long bytes = 0;
        long builtinTotal = 0;
        long eiselTotal = 0;

        for (String fstr : floats) {
            bytes += fstr.length();
        }

        for (long t : builtinTimes) {
            builtinTotal += t;
        }

        double builtMean = builtinTotal / 1000.0;
        double builtSD = 0;

        for (long t : builtinTimes) {
            builtSD += Math.pow(t - builtMean, 2);
        }
        builtSD = Math.sqrt(builtSD / 1000.0);

        for (long t : eiselTimes) {
            eiselTotal += t;
        }

        double eiselMean = eiselTotal / 1000.0;
        double eiselSD = 0;

        for (long t : eiselTimes) {
            eiselSD += Math.pow(t - eiselMean, 2);
        }
        eiselSD = Math.sqrt(eiselSD / 1000.0);

        System.out.println("built-in parseDouble:");
        System.out.println(
                String.format("  <TIME per file> mean: %.3f ms, min: %.3f ms, max: %.3f ms, stddev: %.3f ms",
                        builtMean / 1000000.0,
                        builtinTimes[0] / 1000000.0, builtinTimes[999] / 1000000.0, builtSD / 1000000.0));

        double builtTP = floats.size() / (builtMean / 1000000.0);
        double builtBytes = bytes * 1000 / builtMean;
        System.out.println(String.format("  <THROUGHPUT> %.3f Kops/s, %.3f MB/s\n", builtTP, builtBytes));

        System.out.println("Eisel-Lemire parseDouble:");
        System.out.println(
                String.format("  <TIME per file> mean: %.3f ms, min: %.3f ms, max: %.3f ms, stddev: %.3f ms",
                        eiselMean / 1000000.0,
                        eiselTimes[0] / 1000000.0, eiselTimes[999] / 1000000.0, eiselSD / 1000000.0));

        double eiselTP = floats.size() / (eiselMean / 1000000.0);
        double eiselBytes = bytes * 1000 / eiselMean;
        System.out.println(String.format("  <THROUGHPUT> %.3f Kops/s, %.3f MB/s", eiselTP, eiselBytes));
    }
}
