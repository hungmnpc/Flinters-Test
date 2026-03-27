package com.monopoco;

/**
 * Project: Flinters_Test
 * Package: com.monopoco
 * Author: hungk
 * Date: 3/27/2026
 * Time: 2:35 AM
 */

public class CampaignStats {

    private final String campaignId;
    private long   totalImpressions;
    private long   totalClicks;
    private double totalSpend;
    private long   totalConversions;

    public CampaignStats(String campaignId) {
        this.campaignId = campaignId;
    }

    /** Accumulate one CSV row into this campaign's totals. */
    public void accumulate(long impressions, long clicks,
                           double spend, long conversions) {
        this.totalImpressions += impressions;
        this.totalClicks      += clicks;
        this.totalSpend       += spend;
        this.totalConversions += conversions;
    }



    public String getCampaignId() {
        return campaignId;
    }

    public long getTotalImpressions() {
        return totalImpressions;
    }

    public void setTotalImpressions(long totalImpressions) {
        this.totalImpressions = totalImpressions;
    }

    public long getTotalClicks() {
        return totalClicks;
    }

    public void setTotalClicks(long totalClicks) {
        this.totalClicks = totalClicks;
    }

    public double getTotalSpend() {
        return totalSpend;
    }

    public void setTotalSpend(double totalSpend) {
        this.totalSpend = totalSpend;
    }

    public long getTotalConversions() {
        return totalConversions;
    }

    public void setTotalConversions(long totalConversions) {
        this.totalConversions = totalConversions;
    }

    /** CTR = clicks / impressions. Returns 0 if no impressions. */
    public double getCtr() {
        return totalImpressions == 0 ? 0.0
                : (double) totalClicks / totalImpressions;
    }

    /** CPA = spend / conversions. Returns null if no conversions. */
    public Double getCpa() {
        return totalConversions == 0 ? null
                : totalSpend / totalConversions;
    }
}
