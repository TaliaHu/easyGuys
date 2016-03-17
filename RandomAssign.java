package main;

import dataType.Contact;
import dataType.Node;
import dataType.Task;

public class RandomAssign extends AssignAlgorithm
{
	
	public RandomAssign( Task[] task, Node[] node , Contact[] contact)
	{
		super( task, node, contact );
		
		randomAssign();
	}
	
	private void randomAssign()
	{
		randomizeTasks();
		
		Node chosenNode;
		taskTotalTime = 0;
		int numTask = tasks.length;
		
		for(int i = 0; i < numTask; i++ )
		{
			randomizeNodes();
			chosenNode = nodes[0];
			addTaskIntoNode( tasks[i], chosenNode );
			chosenNode.currentTaskOverload += tasks[i].taskOverload;
			tasks[i].processingNode = chosenNode;
			tasks[i].finishTime = chosenNode.currentTaskOverload + chosenNode.contactParameter * 2;
			taskTotalTime += tasks[i].finishTime;
		}
		
		taskAverageTime = taskTotalTime / (long)numTask;
	}

}
