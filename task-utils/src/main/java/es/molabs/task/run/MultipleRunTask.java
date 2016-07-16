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

import es.molabs.task.base.AbstractTask;

/**
 * Task that will run until isFinished returns true.
 */
public abstract class MultipleRunTask extends AbstractTask
{
	private int timesProcessed;
	
	private boolean finished;
		
	protected MultipleRunTask()
	{		
		timesProcessed = 0;
		
		finished = false;
	}
	
	public boolean isFinished()
	{		
		return finished; 
	}
	
	/**
	 * Sets this task as finished.
	 * 
	 * @param finished value.
	 */
	protected void setFinished(boolean finished)
	{
		this.finished = finished;
	}
	
	public void reset()
	{
		super.reset();
				
		timesProcessed = 0;
		
		finished = false;
	}
	
	/**
	 * Returns the internal counter representing how many times this task has been successfully processed.
	 * 
	 * @return How many times this task has been successfully processed.
	 */
	protected int getTimesProcessed()
	{
		return timesProcessed;
	}
	
	protected void doExecute(float delta)
	{
		// Increases the times called counter
		timesProcessed++;
		
		doProcess();
	}
	
	protected abstract void doProcess();
}
