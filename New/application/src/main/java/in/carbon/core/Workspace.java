package in.carbon.core;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/** Application state separated from the JavaFX scene graph. */
public final class Workspace {
    private final LocalStore store;
    private final List<Activity> activities = new ArrayList<>();
    private final Set<String> chosenActions = new HashSet<>();
    private final Set<String> dismissedActions = new HashSet<>();

    public Workspace(Path storePath) { this.store = new LocalStore(storePath); }
    public Path path() { return store.path(); }
    public List<Activity> activities() { return Collections.unmodifiableList(activities); }

    public void load() throws IOException {
        List<Activity> loaded = store.load();
        activities.clear();
        activities.addAll(loaded);
        chosenActions.clear();
        dismissedActions.clear();
    }

    public void add(Activity activity) throws IOException {
        if (activities.stream().anyMatch(existing -> existing.id().equals(activity.id())))
            throw new IllegalArgumentException("An activity with this id already exists");
        activities.add(activity);
        try { store.save(activities); }
        catch (IOException ex) { activities.remove(activity); throw ex; }
    }

    public void delete(Activity activity) throws IOException {
        if (!activities.remove(activity)) return;
        chosenActions.remove(activity.id());
        dismissedActions.remove(activity.id());
        try { store.save(activities); }
        catch (IOException ex) { activities.add(activity); throw ex; }
    }

    public Optional<ActionPlan> suggestion(Activity activity) {
        if (activity == null || chosenActions.contains(activity.id()) || dismissedActions.contains(activity.id())) return Optional.empty();
        return ActionPlanner.suggestion(activity);
    }

    public void choose(ActionPlan action) { dismissedActions.remove(action.activityId()); chosenActions.add(action.activityId()); }
    public void dismiss(ActionPlan action) { chosenActions.remove(action.activityId()); dismissedActions.add(action.activityId()); }
    public boolean chosen(String id) { return chosenActions.contains(id); }
    public boolean dismissed(String id) { return dismissedActions.contains(id); }
}
