package com.monopoco;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String inputStr = null, outputStr = "results";
        int topN = 10;

        for (int i = 0; i < args.length - 1; i++) {
            switch (args[i]) {
                case "--input"  -> inputStr  = args[++i];
                case "--output" -> outputStr = args[++i];
                case "--top"    -> topN      = Integer.parseInt(args[++i]);
                default -> {
                    System.err.println("Unknown flag: " + args[i]);
                    System.exit(1);
                }
            }
        }

        if (inputStr == null) {
            System.err.println("Usage: --input <csv> [--output dir]");
            System.exit(1);
        }

        Path input  = Paths.get(inputStr);
        Path outDir = Paths.get(outputStr);
        long start  = System.currentTimeMillis();

        Map<String, CampaignStats> data;

        try { data = CsvParser.parse(input); }
        catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage());
            System.exit(1); return;
        }
        Aggregator aggregator = new Aggregator();
        try {
            CsvWriter.write(aggregator.topCtr(10, data), outDir.resolve("top10_ctr.csv"));
            CsvWriter.write(aggregator.topCpa(10, data), outDir.resolve("top10_cpa.csv"));
        } catch (IOException e) {
            System.err.println("ERROR export: " + e.getMessage());
            System.exit(1); return;
        }



        long ms   = System.currentTimeMillis() - start;
        long heap = (Runtime.getRuntime().totalMemory()
                - Runtime.getRuntime().freeMemory()) / (1024 * 1024);
        System.out.printf("Done in %.2fs | heap %dMB%n", ms/1000.0, heap);
    }
}