package A2;
import java.util.*;

class Assignment implements Comparator<Assignment>{
	int number;
	int weight;
	int deadline;


	protected Assignment() {
	}

	protected Assignment(int number, int weight, int deadline) {
		this.number = number;
		this.weight = weight;
		this.deadline = deadline;
	}



	/**
	 * This method is used to sort to compare assignment objects for sorting.
	 * The way you implement this method will define which order the assignments appear in when you sort.
	 * Return 1 if a1 should appear after a2
	 * Return -1 if a1 should appear before a2
	 * Return 0 if a1 and a2 are equivalent
	 */
	@Override
	public int compare(Assignment a1, Assignment a2) {
		//YOUR CODE GOES HERE, DONT FORGET TO EDIT THE RETURN STATEMENT
		/**
		 * when compare() is called by Collections.sort(),
		 * I wish to sort in the following way - sort the
		 * assignments in decreasing order of their deadlines
		 * i.e. the assignment with the latest deadline comes
		 * before assignments with earlier deadlines. in case
		 * of a tie over the deadline, the assignment with higher
		 * weight appears before the assignment with lower weight.
		 *
		 * if the weights are tied as well, then the assignments
		 * are equivalent.
		 */
		if(a1.deadline > a2.deadline) {
			return -1;
		}
		else if (a1.deadline < a2.deadline) {
			return 1;
		}
		else {
			if (a1.weight < a2.weight) {
				return 1;
			}
			else if (a1.weight > a2.weight) {
				return -1;
			}
			else {
				return 0;
			}
		}
	}
}

public class HW_Sched {
	ArrayList<Assignment> Assignments = new ArrayList<Assignment>();
	int m;
	int lastDeadline = 0;

	protected HW_Sched(int[] weights, int[] deadlines, int size) {
		for (int i=0; i<size; i++) {
			Assignment homework = new Assignment(i, weights[i], deadlines[i]);
			this.Assignments.add(homework);
			if (homework.deadline > lastDeadline) {
				lastDeadline = homework.deadline;
			}
		}
		m =size;
	}


	/**
	 *
	 * @return Array where output[i] corresponds to when assignment #i will be completed. output[i] is 0 if assignment #i is never completed.
	 * The homework you complete first will be given an output of 1, the second, 2, etc.
	 */
	public int[] SelectAssignments() {
		//Use the following command to sort your Assignments:
		//Collections.sort(Assignments, new Assignment());
		//This will re-order your assignments. The resulting order will depend on how the compare function is implemented
		Collections.sort(Assignments, new Assignment());

		//Initializes the homeworkPlan, which you must fill out and output
		int[] homeworkPlan = new int[Assignments.size()];
		//YOUR CODE GOES HERE
		Assignments.forEach(a->System.out.println(a.weight));
		System.out.println();
		/**
		 * creating an array which will keep track
		 * of the deadlines of the assignments since
		 * I intend to shift those deadlines (cannot
		 * modify the deadline value of the Assignment
		 * object itself). copy the initial deadlines
		 * into the array.
		 */
		int[] shiftedDeadlines = new int[this.m];
		for(int i = 0; i < this.m; i++) {
			shiftedDeadlines[i] = Assignments.get(i).deadline;
		}

		/**
		 * keeps track of the deadline being
		 * tracked currently. starts from the
		 * last deadline.
		 */
		int currentDeadline = this.lastDeadline;

		while(currentDeadline > 0) {
			System.out.println("Current Deadline: " + currentDeadline);
			// ArrayList<Assignment> assWithSpDeadline = new ArrayList<Assignment>();
			Assignment highestWtAssignment = new Assignment();
			int highestWt = 0;
			/**
			 * keeps track of the index of the
			 * assignment with the highest weight
			 * amongst the sorted assignments.
			 */
			int indexWithHighest = 0;
			for(int i = 0; i < this.m; i++) {
				/**
				 * iterate over all those assignments
				 * whose deadline (dl) matches the current
				 * dl. amongst those, find the one with the
				 * highest weight and store its details in the
				 * different variables instantiated above.
				 */
				if(shiftedDeadlines[i] == currentDeadline) {
					if(Assignments.get(i).weight > highestWt) {
						highestWtAssignment = Assignments.get(i);
						highestWt = Assignments.get(i).weight;
						indexWithHighest = i;
					}
				}
			}

			System.out.println(highestWtAssignment.number + ", " + highestWtAssignment.weight + ", " + highestWtAssignment.deadline + ", " + currentDeadline);
			/**
			 * after finding the assignment with the highest
			 * weight amongst those with the same current dl,
			 * put it in the time slot starting from the LAST
			 * time slot available, i.e. the 1st assignment
			 * found with this procedure will be done in the
			 * last time slot, the 2nd one will be done in the second
			 * last time slot and so on.
			 */
			homeworkPlan[highestWtAssignment.number] = currentDeadline;
			/**
			 * for those assignments which possessed the current dl
			 * (except the highest weight assignment), decrease their
			 * deadlines by one. since they weren't chosen in this
			 * iteration, they may be chosen in the next, so we decrease
			 * their deadline so that they may be considered in the
			 * computation of the next iteration.
			 */
			for(int i = 0; i < this.m; i++) {
				if(shiftedDeadlines[i] == currentDeadline) {
					if(i != indexWithHighest) {
						shiftedDeadlines[i] -=1;
					}
				}
			}
			/**
			 * decrement the deadline and progress
			 * towards the earlier assignments.
			 */
			currentDeadline -= 1;
		}
		return homeworkPlan;
	}
}




