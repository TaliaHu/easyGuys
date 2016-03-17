package main;

import java.util.Arrays;

import dataType.*;

public class MAMOffline extends AssignAlgorithm
{
	
	public MAMOffline( Task[] task, Node[] node, Contact[] contact)
	{
		super( task, node, contact);

		MAMOfflineAlgorithm();
	}
	
	
	private void MAMOfflineAlgorithm()
	{
		Arrays.sort( tasks );
		
		Node chosenNode;
		taskTotalTime = 0;
		int numTask = tasks.length;
		
		for(int i = 0; i < numTask; i++ )
		{
			computeNodeAvailableTimeDouble();
			Arrays.sort( nodes );
			
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
