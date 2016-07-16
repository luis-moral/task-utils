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
package es.molabs.task.time.precise;

import es.molabs.task.base.AbstractTask;

/**
 * Base task for time based tasks.
 */
public abstract class PreciseTimeTask extends AbstractTask
{	
	// Time in nanoseconds
	private long lastUpdate;
	
	// Time in nanoseconds
	private long timePassed;
	
	private boolean finished;	

	protected PreciseTimeTask()
	{
		lastUpdate = 0;
		timePassed = 0;
		
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
		
		lastUpdate = 0;
		timePassed = 0;
		
		finished = false;
	}
	
	protected long getTimePassed()
	{
		return timePassed;
	}

	protected void doExecute(float delta) 
	{
		// Time in nanoseconds since the last call to this method
		long timeSinceLastUpdate = (lastUpdate > 0 ? System.nanoTime() - lastUpdate : 0);
		
		// Updates the time passed since the task was started
		timePassed += timeSinceLastUpdate;
		
		// Calls the hooks and executes the task
		if (resolveProcess(timeSinceLastUpdate)) doProcess(resolveDelta(delta, timeSinceLastUpdate));
		
		lastUpdate = System.nanoTime();
	}
	
	/**
	 * Hook that returns if the task should be executed this time.
	 * 
	 * @param timeSinceLastUpdate in nanoseconds.
	 * 
	 * @return if the task should be executed this time.
	 */
	protected boolean resolveProcess(long timeSinceLastUpdate)
	{
		return true;
	}
	
	/**
	 * Hook that returns the delta time.
	 * 
	 * @param delta time received from the executor.
	 * @param timeSinceLastUpdate in nanoseconds.
	 * 
	 * @return delta time in seconds.
	 */
	protected float resolveDelta(float delta, long timeSinceLastUpdate)
	{
		return ((float) timeSinceLastUpdate) / 1_000_000_000f;
	}
	
	/**
	 * Main method where this task logic should be executed.
	 * 
	 * @param delta time passed in seconds.
	 */
	protected abstract void doProcess(float delta);
}
