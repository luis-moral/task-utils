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

import es.molabs.task.Task;
import es.molabs.task.TaskExecutor;
import es.molabs.task.base.AbstractTask;
import es.molabs.task.base.SingleThreadTaskExecutor;

@RunWith(MockitoJUnitRunner.class)
public class SingleThreadTaskExecutorTest 
{	
	@Test
	public void testAddRemove() throws Throwable
	{
		TaskExecutor taskExecutor = new SingleThreadTaskExecutor();
		
		TestTask mockTask = Mockito.mock(TestTask.class, Mockito.CALLS_REAL_METHODS);
		
		// Adds the task to the executor
		taskExecutor.add(mockTask);		
	
		// Checks that added and onCreate have been called
		Mockito.verify(mockTask, Mockito.times(1)).added();
		Mockito.verify(mockTask, Mockito.times(1)).onCreate();		
		
		// Removes the task form the executor
		taskExecutor.remove(mockTask);
		
		// Checks that removed and onDispose have been called		
		Mockito.verify(mockTask, Mockito.times(1)).onDispose();
		Mockito.verify(mockTask, Mockito.times(1)).removed();
	}
	
	@Test
	public void testFinished() throws Throwable
	{
		TaskExecutor taskExecutor = new SingleThreadTaskExecutor();
		
		TestTask mockTask = Mockito.mock(TestTask.class, Mockito.CALLS_REAL_METHODS);
		
		// Adds the task to the executor
		taskExecutor.add(mockTask);
		
		// Calls execute
		taskExecutor.execute();
		
		// Checks that the task is finished
		Mockito.verify(mockTask, Mockito.times(1)).onDispose();
	}
	
	@Test
	public void testClear() throws Throwable
	{
		TaskExecutor taskExecutor = new SingleThreadTaskExecutor();
		
		Task taskOne = Mockito.mock(Task.class);
		Task taskTwo = Mockito.mock(Task.class);
		
		// Adds the tasks to the executor
		taskExecutor.add(taskOne);
		taskExecutor.add(taskTwo);
		
		// Calls clear
		taskExecutor.clear();
		
		// Checks that the tasks are removed
		Mockito.verify(taskOne, Mockito.times(1)).removed();
		Mockito.verify(taskTwo, Mockito.times(1)).removed();
		Assert.assertEquals(0, taskExecutor.size());
	}
	
	private class TestTask extends AbstractTask
	{
		public boolean isFinished() 
		{
			return true;
		}

		protected void doExecute(float delta) throws Exception 
		{
		}

		protected void onCreate()
		{			
		}
		
		protected void onDispose()
		{			
		}
	}
}
