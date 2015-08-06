package jamder.structural;

import java.util.ArrayList;
import java.util.List;

public class CompositeGoal extends Goal {
	private List<LeafGoal> goals = new ArrayList<LeafGoal>();

	public void add(LeafGoal g) {
		goals.add(g);
	}

	public void remove(Goal g) {
		goals.remove(g);
	}
	
	public LeafGoal get(int index) {
		return goals.get(index);
	}
}
