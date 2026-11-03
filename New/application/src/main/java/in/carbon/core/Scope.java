package in.carbon.core;

public enum Scope {
    PERSONAL("Personal"),
    HOUSEHOLD("Household");

    private final String label;

    Scope(String label) { this.label = label; }
    public String label() { return label; }

    @Override
    public String toString() { return label; }
}
