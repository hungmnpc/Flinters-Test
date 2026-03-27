package com.monopoco;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.Locale;

/**
 * Project: Flinters_Test
 * Package: com.monopoco
 * Author: hungk
 * Date: 3/27/2026
 * Time: 3:30 AM
 */
public class CsvWriter {

    private static final String HEADER =
            "campaign_id,total_impressions,total_clicks,total_spend,total_conversions,CTR,CPA";


    //

    /**
     * Export data to file
     * @param rows List<CampaignStats>
     * @param outputPath (exp: top10_cpa.csv)
     * @throws IOException
     */
    public static void write(List<CampaignStats> rows, Path outputPath) throws IOException {
        Files.createDirectories(outputPath.getParent());
        try (BufferedWriter w = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8)) {
            w.write(HEADER);
            w.newLine();
            for (CampaignStats s : rows) {
                Double cpa = s.getCpa();
                String cpaStr = (cpa == null) ? "" : String.format(Locale.US, "%.10f", cpa);
                w.write(String.join(",",
                        s.getCampaignId(),
                        Long.toString(s.getTotalImpressions()),
                        Long.toString(s.getTotalClicks()),
                        String.format(Locale.US, "%.2f", s.getTotalSpend()),
                        Long.toString(s.getTotalConversions()),
                        String.format(Locale.US, "%.10f", s.getCtr()),
                        cpaStr
                ));
                w.newLine();
            }
        }
    }
}
