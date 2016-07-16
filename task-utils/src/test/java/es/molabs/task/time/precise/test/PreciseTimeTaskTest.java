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
package es.molabs.task.time.precise.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import es.molabs.task.TaskExecutor;
import es.molabs.task.base.SingleThreadTaskExecutor;
import es.molabs.task.time.precise.PreciseMaxTimeTask;
import es.molabs.task.time.precise.PreciseRepeatTimeTask;
import es.molabs.task.time.precise.PreciseTimeTask;
import es.molabs.task.time.precise.PreciseWaitTimeTask;

@RunWith(MockitoJUnitRunner.class)
public class PreciseTimeTaskTest 
{
	@Test
	public void testTimeTask() throws Throwable
	{
		float TIME = 0.05f;
		
		TaskExecutor taskExecutor = new SingleThreadTaskExecutor();
		
		TestTimeTask task = Mockito.spy(new TestTimeTask());
		
		// Adds the task
		taskExecutor.add(task);
		
		long endTime = System.nanoTime() + ((long) (1_000_000_000 * TIME));
		int timesCalled = 0;
		
		// While TIME has not passed 
		while (System.nanoTime() <= endTime)
		{
			// Executes the tasks
			taskExecutor.execute();
			
			// Increases the times called counter
			timesCalled++;
			
			Thread.sleep(100);
		}
		
		// Checks that the task was called only timesCalled times
		Mockito.verify(task, Mockito.times(timesCalled)).doProcess(Mockito.anyFloat());
	}
	
	@Test
	public void testRepeatTimeTask() throws Throwable
	{
		float PERIOD = 0.01f;
		float DELAY = 0.01f;
		float TIME = 0.05f;
		
		TaskExecutor taskExecutor = new SingleThreadTaskExecutor();
		
		TestRepeatTimeTask taskWithoutDelay = Mockito.spy(new TestRepeatTimeTask(PERIOD));
		TestRepeatTimeTask taskWithDelay = Mockito.spy(new TestRepeatTimeTask(DELAY, PERIOD));		
		
		// Adds the tasks
		taskExecutor.add(taskWithDelay);
		taskExecutor.add(taskWithoutDelay);
		
		long endTime = System.nanoTime() + ((long) (1_000_000_000 * TIME));
		
		// While TIME has not passed
		while (System.nanoTime() <= endTime)
		{
			// Executes the tasks
			taskExecutor.execute();
		}
				
		// Checks that the task was called only TIME/PERIOD times
		Mockito.verify(taskWithoutDelay, Mockito.times(Math.round((TIME) / PERIOD))).doProcess(Mockito.anyFloat());
		
		// Checks that the task was called only TIME - DELAY/PERIOD times
		Mockito.verify(taskWithDelay, Mockito.times(Math.round((TIME - DELAY) / PERIOD))).doProcess(Mockito.anyFloat());
	}
	
	@Test
	public void testMaxTimeTask() throws Throwable
	{
		float MAX_TIME = 0.03f;
		float TIME = 0.1f;
		
		TaskExecutor taskExecutor = new SingleThreadTaskExecutor();		
		
		TestMaxTimeTask task = Mockito.spy(new TestMaxTimeTask(MAX_TIME));
		
		// Adds the task
		taskExecutor.add(task);
		
		long startTime = System.nanoTime();
		long endTime = startTime + ((long) (1_000_000_000 * TIME));
		int timesCalled = 0;
		
		// While TIME has not passed
		while (System.nanoTime() <= endTime)
		{
			// Executes the tasks
			taskExecutor.execute();
			
			// If MAX_TIME has not passed yet
			if ((System.nanoTime() - startTime) < (MAX_TIME * 1_000_000_000))
			{
				// Increases the timesCalled counter
				timesCalled++;				
			}			
			
			Thread.sleep(100);
		}
		
		// Checks that the task was called only timesCalled times
		Mockito.verify(task, Mockito.times(timesCalled)).doProcess(Mockito.anyFloat());
	}
	
	@Test
	public void testWaitTimeTask() throws Throwable
	{
		float WAIT_TIME = 0.03f;
		float TIME = 0.1f;
		
		TaskExecutor taskExecutor = new SingleThreadTaskExecutor();
		
		TestWaitTimeTask taskOne = Mockito.spy(new TestWaitTimeTask(WAIT_TIME));
		TestWaitTimeTask taskTwo = Mockito.spy(new TestWaitTimeTask(WAIT_TIME));
		
		// Adds the taskOne
		taskExecutor.add(taskOne);
		
		// Checks that is called when time passed is less WAIT_TIME
		long endTime = System.nanoTime() + ((long) (1_000_000_000 * TIME));			 
		while (System.nanoTime() <= endTime)
		{			
			// Executes the tasks
			taskExecutor.execute();
		}
		
		// Checks that was called only one time
		Mockito.verify(taskOne, Mockito.times(1)).doProcess(Mockito.anyFloat());
		
		// Adds the taskTwo
		taskExecutor.add(taskTwo);
		
		// Checks that is called when time passed is less WAIT_TIME
		endTime = System.nanoTime() + ((long) (1_000_000_000 * WAIT_TIME)) - 1;
		while (System.nanoTime() < WAIT_TIME)
		{			
			// Executes the tasks
			taskExecutor.execute();
		}
		
		// Checks that was not called
		Mockito.verify(taskTwo, Mockito.times(0)).doProcess(Mockito.anyFloat());
	}
	
	private class TestTimeTask extends PreciseTimeTask
	{
		protected void doProcess(float delta) 
		{		
		}	
	}
	
	private class TestRepeatTimeTask extends PreciseRepeatTimeTask
	{
		public TestRepeatTimeTask(float period)
		{
			super(period);
		}
		
		public TestRepeatTimeTask(float delay, float period)
		{
			super(delay, period);
		}
		
		protected void doProcess(float delta) 
		{
		}
	}
	
	private class TestMaxTimeTask extends PreciseMaxTimeTask
	{
		public TestMaxTimeTask(float maxTime) 
		{
			super(maxTime);
		}

		protected void doProcess(float delta) 
		{			
		}		
	}
	
	private class TestWaitTimeTask extends PreciseWaitTimeTask
	{
		protected TestWaitTimeTask(float waitTime) 
		{
			super(waitTime);
		}

		protected void doProcess(float delta) 
		{		
		}		
	}
}
