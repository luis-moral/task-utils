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
package es.molabs.task.base;

import es.molabs.task.Task;

public class SequenceTask implements Task 
{	
	private int timesToRun;
	private Task[] tasks = null;
	
	private int currentTask;
	private int timesRan;
	
	public SequenceTask(Task...tasks)
	{
		this(1, tasks);
	}
	
	public SequenceTask(int timesToRun, Task...tasks)
	{
		this.timesToRun = timesToRun;		
		this.tasks = tasks;
		
		reset();
	}
	
	public void added()
	{
	}

	public void removed() 
	{	
	}
	
	public boolean isFinished() 
	{
		return timesRan == timesToRun;
	}
	
	public void reset()
	{
		currentTask = 0;
		timesRan = 0;
	}

	public Task getNext() 
	{
		return null;
	}
	
	public void execute(float delta) 
	{
		// Executes the current task
		tasks[currentTask].execute(delta);
		
		// If the current task is finished
		if (tasks[currentTask].isFinished())
		{
			// Resets the current task
			tasks[currentTask].reset();
			
			// Increases the current task counter
			currentTask++;
			
			// If the current task counter is greater than the amount of tasks
			if (currentTask >= tasks.length)
			{
				// Increases the times ran counter
				timesRan++;
				
				// Resets the current task counter
				currentTask = 0;
			}
		}		
	}
}