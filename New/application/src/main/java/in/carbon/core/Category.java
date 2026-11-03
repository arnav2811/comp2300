package in.carbon.core;

/** Activity categories deliberately distinguish numerical and descriptive paths. */
public enum Category {
    ELECTRICITY("Home electricity", "kWh"),
    TRAVEL("Travel", "passenger-km"),
    WATER("Water", "litres"),
    FOOD("Food", "kg"),
    PACKAGING("Delivery packaging", "kg"),
    WASTE("Waste", "kg"),
    REPAIR_REUSE("Repair or reuse", "items");

    private final String label;
    private final String unit;

    Category(String label, String unit) {
        this.label = label;
        this.unit = unit;
    }

    public String label() { return label; }
    public String unit() { return unit; }

    @Override
    public String toString() { return label; }
}
