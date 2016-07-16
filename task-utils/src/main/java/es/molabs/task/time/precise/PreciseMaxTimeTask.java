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

/**
 * Task that will run for a maximum amount of time.
 */
public abstract class PreciseMaxTimeTask extends PreciseTimeTask
{
	private float maxTime;
	
	/**
	 * Constructor.
	 * 
	 * @param maxTime in seconds.
	 */
	public PreciseMaxTimeTask(float maxTime)
	{
		this.maxTime = maxTime; 
	}
	
	protected float getMaxTime()
	{
		return maxTime;
	}

	protected boolean resolveProcess(long timeSinceLastUpdate)
	{
		boolean process = true;
		
		// If the time passed is equal or greater than the maximum running time
		if (getTimePassed() >= (long) (maxTime * 1_000_000_000f))
		{			
			process = false;
			
			// Sets the task as finished
			setFinished(true);
		}
		
		return process;
	}
}
