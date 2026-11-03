package in.carbon.core;

import java.util.Objects;

public record ActionPlan(String activityId, String title, String explanation) {
    public ActionPlan {
        requireText(activityId, "Activity id");
        requireText(title, "Action title");
        requireText(explanation, "Action explanation");
    }

    private static void requireText(String value, String label) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(label + " is required");
    }

    @Override
    public String toString() { return title + " — " + explanation; }
}
