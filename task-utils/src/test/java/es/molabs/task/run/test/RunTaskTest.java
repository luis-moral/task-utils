/**
 * Copyright (C) 2016 Luis Moral Guerrero <luis.moral@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.molabs.task.run.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import es.molabs.task.TaskExecutor;
import es.molabs.task.base.SingleThreadTaskExecutor;
import es.molabs.task.run.MaxRunTask;
import es.molabs.task.run.MultipleRunTask;
import es.molabs.task.run.SingleRunTask;

@RunWith(MockitoJUnitRunner.class)
public class RunTaskTest 
{
	@Test
	public void testSingleRunTask() throws Throwable
	{
		TaskExecutor taskExecutor = new SingleThreadTaskExecutor();		
		
		TestSingleRunTask task = Mockito.mock(TestSingleRunTask.class, Mockito.CALLS_REAL_METHODS);
		
		// Adds the task
		taskExecutor.add(task);
		
		for (int i=0; i<20; i++)
		{
			// Executes the tasks
			taskExecutor.execute();
		}
		
		// Checks that the task was called only one time
		Mockito.verify(task, Mockito.times(1)).doProcess();
	}
	
	@Test
	public void testMultipleRunTask() throws Throwable
	{
		int TIMES = 10;
		
		TaskExecutor taskExecutor = new SingleThreadTaskExecutor();
				
		TestMultipleRunTask task = Mockito.mock(TestMultipleRunTask.class, Mockito.CALLS_REAL_METHODS);
		
		// Adds the task
		taskExecutor.add(task);
		
		for (int i=0; i<20; i++)
		{
			// Executes the tasks
			taskExecutor.execute();
			
			// If was executed already TIMES times
			if (i == TIMES - 1)
			{
				Mockito.when(task.isFinished()).thenReturn(true);
			}
		}
		
		//  Checks that the task was called only TIMES times
		Mockito.verify(task, Mockito.times(TIMES)).doProcess();		
	}
	
	@Test
	public void testMaxRunTask() throws Throwable
	{
		int TIMES = 15;
		
		TaskExecutor taskExecutor = new SingleThreadTaskExecutor();
		
		TestMaxRunTask task = Mockito.spy(new TestMaxRunTask(TIMES));
		
		// Adds the task
		taskExecutor.add(task);
		
		for (int i=0; i<20; i++)
		{
			// Executes the tasks
			taskExecutor.execute();
		}
		
		// Checks that the task was called only TIMES times
		Mockito.verify(task, Mockito.times(TIMES)).doProcess();
	}
	
	private class TestSingleRunTask extends SingleRunTask
	{
		protected void doProcess() 
		{			
		}	
	}
	
	private class TestMultipleRunTask extends MultipleRunTask
	{
		protected void doProcess() 
		{			
		}		
	}
	
	private class TestMaxRunTask extends MaxRunTask
	{
		public TestMaxRunTask(int timesToRun) 
		{
			super(timesToRun);
		}

		protected void doProcess() 
		{
		}		
	}
}
