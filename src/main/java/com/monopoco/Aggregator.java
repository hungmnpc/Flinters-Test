package com.monopoco;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Project: Flinters_Test
 * Package: com.monopoco
 * Author: hungk
 * Date: 3/27/2026
 * Time: 6:39 AM
 */

// Service
public class Aggregator {

    // get Top N by CTR DESC
    public List<CampaignStats> topCtr(int top, Map<String, CampaignStats> data) {
        if (top <= 0) {
            throw new IllegalArgumentException("top must be greater than 0");
        }

        return data.values().stream()
                .sorted(Comparator
                        .comparingDouble(CampaignStats::getCtr).reversed()
                        .thenComparing(CampaignStats::getCampaignId))
                .limit(top)
                .collect(Collectors.toList());
    }


    // get Top N by CPA ASC
    public List<CampaignStats> topCpa(int top, Map<String, CampaignStats> data) {
        if (top <= 0) {
            throw new IllegalArgumentException("top must be greater than 0");
        }

        return data.values().stream()
                .filter(s -> s.getCpa() != null)
                .sorted(Comparator
                        .comparingDouble(CampaignStats::getCpa)
                        .thenComparing(CampaignStats::getCampaignId))
                .limit(top)
                .collect(Collectors.toList());
    }
}
