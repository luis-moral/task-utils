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
package es.molabs.task.time.simple;

import es.molabs.task.base.AbstractTask;

/**
 * Base task for time based tasks.
 */
public abstract class SimpleTimeTask extends AbstractTask
{	
	// Time in seconds
	private float timePassed;
	
	private boolean finished;	

	protected SimpleTimeTask()
	{
		timePassed = 0f;
		
		finished = false;
	}
	
	public boolean isFinished() 
	{
		return finished;
	}
	
	protected void setFinished(boolean finished)
	{
		this.finished = finished;
	}
	
	public void reset()
	{
		super.reset();
		
		timePassed = 0f;
		
		finished = false;
	}
	
	protected float getTimePassed()
	{
		return timePassed;
	}

	protected void doExecute(float delta) 
	{
		// Adds the delta time to the time passed
		timePassed += delta;
		
		// Calls the resolveProcess hook and if it returns true to the doProcess hook
		if (resolveProcess(delta)) doProcess(delta);
	}
	
	/**
	 * Hook that returns if the task should be executed this time.
	 * 
	 * @param delta time passed in seconds.
	 * 
	 * @return if the task should be executed this time.
	 */
	protected boolean resolveProcess(float delta)
	{
		return true;
	}
	
	/**
	 * Main method where this task logic should be executed.
	 * 
	 * @param delta time passed in seconds.
	 */
	protected abstract void doProcess(float delta);
}
