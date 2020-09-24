package de.wwag.hackathon.team2.domain.enumeration;

public enum DeskgroupThresholdValues {
    FULL_OCCUPATION(1.0), HALF_OCCUPATION(0.5);

    private final double percentage;

    private DeskgroupThresholdValues(double percentage) {
        this.percentage = percentage;
    }

    public double getPercentage() {
        return percentage;
    }

}
