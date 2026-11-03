package in.carbon.core;

public enum FactorStatus {
    VERIFIED("Verified source"),
    ILLUSTRATIVE("Illustrative fixture"),
    USER_SUPPLIED("User-supplied, unverified");

    private final String label;

    FactorStatus(String label) { this.label = label; }
    public String label() { return label; }
    @Override public String toString() { return label; }
}
