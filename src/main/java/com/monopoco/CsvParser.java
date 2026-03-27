package com.monopoco;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Project: Flinters_Test
 * Package: com.monopoco
 * Author: hungk
 * Date: 3/27/2026
 * Time: 2:32 AM
 */

//Class read file csv
// Solution: Using BufferedReader with buffer 1MB and HashMap to store by Campaign_id key
public class CsvParser {

    private static final Logger LOG =
            Logger.getLogger(CsvParser.class.getName());

    private static final int CAMPAIGN_ID_COL = 0;
    private static final int DATE_COL = 1;
    private static final int IMPRESSIONS_COL = 2;
    private static final int CLICKS_COL = 3;
    private static final int SPEND_COL = 4;
    private static final int CONVERSIONS_COL = 5;

    private static final int BUFFER_SIZE = 1024 * 1024;


    /**
     * Parses a CSV file containing advertising campaign metrics and aggregates
     * the results by campaign ID.
     * @param path
     * @return
     * @throws IOException
     */
    public static Map<String, CampaignStats> parse(Path path)
            throws IOException {
        Map<String, CampaignStats> map = new HashMap<>();
        long processed = 0, skipped = 0;

        try (BufferedReader r = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(path.toFile()),
                        StandardCharsets.UTF_8), BUFFER_SIZE)) {

            String header = r.readLine();
            if (header == null)
                throw new IllegalArgumentException("Empty file");

            String line;
            while ((line = r.readLine()) != null) {
                if (line.isBlank()) continue;

                String[] cols = line.split(",", -1);
                if (cols.length < 6) {
                    LOG.warning("Skipping: " + line);
                    skipped++; continue;
                }

                try {
                    String id   = cols[CAMPAIGN_ID_COL].trim();
                    long imp   = Long.parseLong(cols[IMPRESSIONS_COL].trim());
                    long clk   = Long.parseLong(cols[CLICKS_COL].trim());
                    double sp  = Double.parseDouble(cols[SPEND_COL].trim());
                    long conv  = Long.parseLong(cols[CONVERSIONS_COL].trim());

                    map.computeIfAbsent(id, CampaignStats::new)
                            .accumulate(imp, clk, sp, conv);
                    processed++;
                } catch (NumberFormatException e) {
                    LOG.warning("Error row: " + line);
                    skipped++;
                }
            }
        }
        LOG.info(processed + " rows, " + skipped
                + " skipped, " + map.size() + " campaigns");
        return map;
    }
}
