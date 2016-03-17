package main;

import util.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;

import reader.*;
import util.ConstantPath;
import util.Data;
import util.DataGenerator;

public class TaskAssignment 
{
	public static final String TIP = "TaskAssignment Select:\n"
			+ "\tVariate Node Parameter Enter:\t \"NP\" \n"
			+ "\tVariate Node Number Enter:\t \"NN\" \n"
			+ "\tVariate Task Load Enter:\t \"TL\" \n"
			+ "\tVariate Task Number Enter:\t \"TN\" \n"
			+ "\tExit enter:\t \"exit\" \n";
	
	public static final String TRACE_MODE = "traceMode";
	public static final String GENERATE_MODE = "generateMode";
	public static String mode = TRACE_MODE;
	
	public static Reader reader;
	public static MAMOffline mamOffline;
	public static RandomAssign randomAssign;
	public static ReverseAssign reverseAssign;
	public static MAMOnline mamOnline;
	public static MAMOnlineOptimum mamOnlineOptimum;
	
	public static double[][] totalTime;
	public static double[][] averageTime;
	
	public static double[][] improvedPercent;

	public static String[] taskPath;
	public static String[] nodePath;
	
	public static String taskListPath;
	public static String nodeListPath;
	public static String resultPath;
	
	public static String listHeader;
	public static String resultHeader;
	
	public static void main( String[] args ) throws IOException
	{	
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println(TIP);
		String str;
		
		if( mode == GENERATE_MODE )
		{
			listHeader = ConstantPath.DATA_HEAD + ConstantPath.GENERATE;
			resultHeader = ConstantPath.RESULT_HEAD + ConstantPath.GENERATE;
		}
		else
		{
			listHeader = ConstantPath.DATA_HEAD + ConstantPath.TRACE;
			resultHeader = ConstantPath.RESULT_HEAD + ConstantPath.TRACE;
		}
		
		while( true )
		{
			str = br.readLine();
			if( str != null && str.length() != 0 )
			{
				if( str.equals("NP") )
				{
					
					if( mode == TRACE_MODE )
					{
						String traceSub = "";
						for(int i = 1; i < 5; i++ )
						{
							traceSub = ConstantPath.TRACE_SUB + i + "/";
							taskListPath = listHeader + ConstantPath.V_N_P + traceSub + ConstantPath.TASK_LIST;
							nodeListPath = listHeader + ConstantPath.V_N_P + traceSub + ConstantPath.NODE_LIST;
							resultPath = resultHeader + ConstantPath.V_N_P + traceSub;
							simulate( 'n' );
						}
					}
					else
					{
						taskListPath = listHeader + ConstantPath.V_N_P + ConstantPath.TASK_LIST;
						nodeListPath = listHeader + ConstantPath.V_N_P + ConstantPath.NODE_LIST;
						resultPath = resultHeader + ConstantPath.V_N_P;
						simulate( 'n' );
					}

				}
				else if( str.equals("NN"))
				{
					if( mode == TRACE_MODE )
					{
						String traceSub = "";
						for(int i = 1; i < 5; i++ )
						{
							traceSub = ConstantPath.TRACE_SUB + i + "/";
							taskListPath = listHeader + ConstantPath.V_N_N + traceSub + ConstantPath.TASK_LIST;
							nodeListPath = listHeader + ConstantPath.V_N_N + traceSub + ConstantPath.NODE_LIST;
							resultPath = resultHeader + ConstantPath.V_N_N + traceSub;
							simulate( 'n' );
						}
					}
					else
					{
						taskListPath = listHeader + ConstantPath.V_N_N + ConstantPath.TASK_LIST;
						nodeListPath = listHeader + ConstantPath.V_N_N + ConstantPath.NODE_LIST;
						resultPath = resultHeader + ConstantPath.V_N_N;
						simulate( 'n' );
					}

				}
				else if( str.equals("TL"))
				{
					if( mode == TRACE_MODE )
					{
						String traceSub = "";
						for(int i = 1; i < 5; i++ )
						{
							traceSub = ConstantPath.TRACE_SUB + i + "/";
							taskListPath = listHeader + ConstantPath.V_T_L + traceSub + ConstantPath.TASK_LIST;
							nodeListPath = listHeader + ConstantPath.V_T_L + traceSub + ConstantPath.NODE_LIST;
							resultPath = resultHeader + ConstantPath.V_T_L + traceSub;
							simulate('t');
						}
					}
					else
					{
						taskListPath = listHeader + ConstantPath.V_T_L + ConstantPath.TASK_LIST;
						nodeListPath = listHeader + ConstantPath.V_T_L + ConstantPath.NODE_LIST;
						resultPath = resultHeader + ConstantPath.V_T_L;
						simulate('t');
					}
					

				}
				else if( str.equals("TN") )
				{
					if( mode == TRACE_MODE )
					{
						String traceSub = "";
						for(int i = 1; i < 5; i++ )
						{
							traceSub = ConstantPath.TRACE_SUB + i + "/";
							taskListPath = listHeader + ConstantPath.V_T_N + traceSub + ConstantPath.TASK_LIST;
							nodeListPath = listHeader + ConstantPath.V_T_N + traceSub + ConstantPath.NODE_LIST;
							resultPath = resultHeader + ConstantPath.V_T_N + traceSub;
							simulate('t');
						}
					}
					else
					{
						taskListPath = listHeader + ConstantPath.V_T_N + ConstantPath.TASK_LIST;
						nodeListPath = listHeader + ConstantPath.V_T_N + ConstantPath.NODE_LIST;
						resultPath = resultHeader + ConstantPath.V_T_N;
						simulate('t');
					}

				}
				else if(str.equals("exit"))
				{
					break;
				}
			}
			System.out.println("**************************************************");
			System.out.println(DataGenerator.TIP);
		}

	}
	
	
	public static void simulate(char choose) throws IOException 
	{
		getPath();
		if( choose == 'N' || choose == 'n')
		{
			totalTime = new double[5][nodePath.length];
			averageTime = new double[5][nodePath.length];
			improvedPercent = new double[5][nodePath.length];
			variateNode();
		}
		else
		{
			totalTime = new double[5][taskPath.length];
			averageTime = new double[5][taskPath.length];
			improvedPercent = new double[5][taskPath.length];
			variateTask();
		}
		
		saveResult("MAMOffline", averageTime[0], improvedPercent[0]);
		saveResult("RandomAssign", averageTime[1], improvedPercent[1]);
		saveResult("ReverseAssign", averageTime[2], improvedPercent[2]);
		saveResult("MAMOnline", averageTime[3], improvedPercent[3]);
		saveResult("MAMOnlineOptimum", averageTime[4], improvedPercent[4]);
	}
	
	public static void variateNode() throws IOException
	{
		for(int index = 0; index < nodePath.length; index++)
		{
			int taskIndex = ( taskPath.length == nodePath.length ) ? index : 0;
			reader = new Reader(taskPath[taskIndex], nodePath[index]);
			taskAssign( index );
		}
	}
	
	
	public static void variateTask() throws IOException
	{
		for(int index = 0; index < taskPath.length; index++)
		{
			int nodeIndex = ( taskPath.length == nodePath.length ) ? index : 0;
			reader = new Reader(taskPath[index], nodePath[nodeIndex]);
			taskAssign( index );
		}
	}
	
	public static void taskAssign( int index )
	{
		mamOffline = new MAMOffline( reader.tasks , reader.nodes, reader.contacts);
		mamOffline.showResult( "MAMOffline_" + index );
		totalTime[0][index] = mamOffline.taskTotalTime;
		averageTime[0][index] = mamOffline.taskAverageTime;
		
		randomAssign = new RandomAssign( reader.tasks, reader.nodes, reader.contacts );
		randomAssign.showResult( "RandomAssign_" + index );
		totalTime[1][index] = randomAssign.taskTotalTime;
		averageTime[1][index] = randomAssign.taskAverageTime;
		
		reverseAssign = new ReverseAssign( reader.tasks, reader.nodes, reader.contacts );
		reverseAssign.showResult( "ReverseAssign_" + index );
		totalTime[2][index] = reverseAssign.taskTotalTime;
		averageTime[2][index] = reverseAssign.taskAverageTime;
		
		mamOnline = new MAMOnline( reader.tasks, reader.nodes, reader.contacts, null );
		mamOnline.showResult( "MAMOnline_" + index );
		totalTime[3][index] = mamOnline.taskTotalTime;
		averageTime[3][index] = mamOnline.taskAverageTime;
		
		mamOnlineOptimum = new MAMOnlineOptimum( reader.tasks, reader.nodes, reader.contacts );
		mamOnlineOptimum.showResult("MAMOnlineOptimum_" + index);
		totalTime[4][index] = mamOnlineOptimum.taskTotalTime;
		averageTime[4][index] = mamOnlineOptimum.taskAverageTime;
		
		improvedPercent[0][index] = (mamOffline.taskAverageTime - mamOnlineOptimum.taskAverageTime)
				/mamOnlineOptimum.taskAverageTime;
		improvedPercent[1][index] = (randomAssign.taskAverageTime - mamOnlineOptimum.taskAverageTime)
				/mamOnlineOptimum.taskAverageTime;
		improvedPercent[2][index] = (reverseAssign.taskAverageTime - mamOnlineOptimum.taskAverageTime)
				/mamOnlineOptimum.taskAverageTime;
		improvedPercent[3][index] = (mamOnline.taskAverageTime - mamOnlineOptimum.taskAverageTime)
				/mamOnlineOptimum.taskAverageTime;
		improvedPercent[4][index] = (mamOnlineOptimum.taskAverageTime - mamOnlineOptimum.taskAverageTime)
				/mamOnlineOptimum.taskAverageTime;
		
	}
	
	public static void getPath() throws IOException
	{
		BufferedReader br = new BufferedReader( new FileReader(taskListPath));
		LinkedList<String> list = new LinkedList<String>();
		
		while(true)
		{
			String line = br.readLine();
			if(line == null)
			{
				break;
			}
			
			String[] data = line.split("\\s+");
			if(data.length == 0)
			{
				continue;
			}
			
			list.addLast( data[0] );
//			System.out.println(data[0]);
		}
		br.close();
		taskPath = list.toArray(new String[0]);
		
		br = new BufferedReader( new FileReader(nodeListPath));
		list.clear();
		while(true)
		{
			String line = br.readLine();
			if(line == null)
			{
				break;
			}
			String[] data = line.split("\\s+");
			if(data.length == 0)
			{
				continue;
			}
			list.addLast(data[0]);
//			System.out.println(data[0]);
		}
		br.close();
		nodePath = list.toArray( new String[0] );
	}
	
	public static void saveResult( String name, double[] saveData, double[] percent) throws IOException
	{
		Data data = new Data(name);
		data.data = saveData;
		data.write(resultPath);
		

		double[] temp = new double[percent.length + 1];
		double sum = 0;
		for(int i = 0; i < percent.length; i++)
		{
			temp[i] = percent[i];
			sum += percent[i];
		}
		temp[percent.length] = sum / percent.length;
		Data per = new Data(name);
		per.data = temp;
		per.write(resultPath + "ImprovedPercent__");
		
	}
	
	
}
