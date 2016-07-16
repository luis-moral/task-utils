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
package es.molabs.task.run;

/**
 * Task that will run a fixed amount of times.
 */
public abstract class MaxRunTask extends MultipleRunTask
{
	private int timesToRun;
	
	/**
	 * Constructor.
	 * 
	 * @param timesToRun this task.
	 */
	public MaxRunTask(int timesToRun)
	{
		this.timesToRun = timesToRun;
	}
	
	protected int getTimesToRun()
	{
		return timesToRun;
	}
	
	protected void doExecute(float delta)
	{	
		// Executes the task
		super.doExecute(delta);
		
		// If the times processes is equal or greater that the times to run
		if (getTimesProcessed() >= timesToRun)
		{
			// Sets the task as finished
			setFinished(true);
		}
	}
}
