package in.carbon.core;

import java.util.Optional;

/** Explicit, inspectable action rules. This is deliberately not machine learning. */
public final class ActionPlanner {
    private ActionPlanner() { }

    public static Optional<ActionPlan> suggestion(Activity activity) {
        if (activity == null || activity.protectedChoice()) return Optional.empty();
        return switch (activity.category()) {
            case ELECTRICITY -> Optional.of(new ActionPlan(activity.id(), "Try one lower-energy routine",
                    "Compare a feasible appliance or timing change; this is a proposal, not completed savings."));
            case TRAVEL -> Optional.of(new ActionPlan(activity.id(), "Inspect a safe alternative",
                    "Compare walking, cycling, public transport or shared travel only when safe and available."));
            case WATER -> Optional.of(new ActionPlan(activity.id(), "Check one avoidable-use opportunity",
                    "Use the volume comparison as a prompt; it is not a carbon-saving estimate."));
            case FOOD -> Optional.of(new ActionPlan(activity.id(), "Review one low-waste choice",
                    "Choose a realistic food or storage adjustment without treating food impact as precisely measured."));
            case PACKAGING, WASTE, REPAIR_REUSE -> Optional.of(new ActionPlan(activity.id(), "Keep a descriptive action note",
                    "Record reuse, repair, packaging or disposal context; no unsupported carbon credit is assigned."));
        };
    }
}
