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
package es.molabs.task.base.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import es.molabs.task.TaskExecutor;
import es.molabs.task.base.SequenceTask;
import es.molabs.task.base.SingleThreadTaskExecutor;
import es.molabs.task.run.MaxRunTask;

@RunWith(MockitoJUnitRunner.class)
public class SequenceTaskTest 
{
	@Test
	public void testSequenceTask() throws Throwable
	{
		int SEQUENCE_REPEATS = 5;
		int TASK_RUNS = 3;
		
		TaskExecutor taskExecutor = new SingleThreadTaskExecutor();
		
		// Creates the tasks
		TestTask taskOne = Mockito.spy(new TestTask(TASK_RUNS));
		TestTask taskTwo = Mockito.spy(new TestTask(TASK_RUNS));
		
		// Creates the sequence
		SequenceTask sequence = new SequenceTask(SEQUENCE_REPEATS, taskOne, taskTwo);
		
		// Adds the task to the executor
		taskExecutor.add(sequence);
		
		// For each sequence repeat
		for (int i=0; i<SEQUENCE_REPEATS; i++)
		{			
			// For each task run
			for (int j=0; j<TASK_RUNS; j++)
			{
				// Calls execute
				taskExecutor.execute();
				
				// Checks that taskOne was executed one (i * TASK_RUNS) + (j+1)) times and taskTwo (i * TASK_RUNS) times
				Mockito.verify(taskOne, Mockito.times((i * TASK_RUNS) + (j+1))).doProcess();
				Mockito.verify(taskTwo, Mockito.times((i * TASK_RUNS))).doProcess();
			}
			
			// Checks that both tasks are not finished
			Assert.assertEquals(false, taskOne.isFinished());
			Assert.assertEquals(false, taskTwo.isFinished());
			
			// For each task run
			for (int j=0; j<TASK_RUNS; j++)
			{
				// Calls execute
				taskExecutor.execute();
				
				// Checks that taskOne was executed one ((i+1) * TASK_RUNS) times and taskTwo (i * TASK_RUNS) + (j+1) times
				Mockito.verify(taskOne, Mockito.times(((i+1) * TASK_RUNS))).doProcess();
				Mockito.verify(taskTwo, Mockito.times((i * TASK_RUNS) + (j+1))).doProcess();
			}
			
			// Checks that both tasks are not finished
			Assert.assertEquals(false, taskOne.isFinished());
			Assert.assertEquals(false, taskTwo.isFinished());
			
			// If it is not the last call
			if (i+1 < SEQUENCE_REPEATS)
			{
				// Checks that sequence task is not finished
				Assert.assertEquals(false, sequence.isFinished());
				
				// Checks that there is one task left in the executor
				Assert.assertEquals(1, taskExecutor.size());
			}
		}
		
		// Checks that sequence task is finished
		Assert.assertEquals(true, sequence.isFinished());
				
		// Checks that there is no tasks left in the executor
		Assert.assertEquals(0, taskExecutor.size());
	}
	
	private class TestTask extends MaxRunTask
	{
		public TestTask(int timesToRun)
		{
			super(timesToRun);
		}
		
		protected void doProcess() 
		{			
		}	
	}
}
