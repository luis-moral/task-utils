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

/**
 * Task that will be executed periodically.
 */
public abstract class SimpleRepeatTimeTask extends SimpleTimeTask
{
	private float delay;
	private float period;

	private long lastExecution;
	private float timeSinceLastExecution;

	/**
	 * Constructor. With 0 seconds delay.
	 *
	 * @param period in seconds.
	 */
	protected SimpleRepeatTimeTask(float period)
	{
		this(0f, period);
	}
	
	/**
	 * Constructor.
	 *
	 * @param delay till first execution in seconds.
	 * @param period in seconds.
	 */
	protected SimpleRepeatTimeTask(float delay, float period)
	{
		this.delay = delay;
		this.period = period;
		
		lastExecution = 0;
	}
	
	public void reset()
	{		
		super.reset();
		
		lastExecution = 0;
	}
	
	protected float getDelay()
	{
		return delay;
	}
	
	protected float getPeriod()
	{
		return period;
	}

	protected boolean resolveProcess(float delta)
	{
		boolean process = false;
		
		// If the delay has passed
		if (getTimePassed() >= delay)
		{
			// Time in nanoseconds since last execution
			timeSinceLastExecution = (lastExecution > 0 ? System.nanoTime() - lastExecution : 0);

			// If time since last execution is greater than or equal than the period
			if (timeSinceLastExecution == 0 || timeSinceLastExecution >= (long) (period * 1_000_000_000f))
			{
				// We should call process
				process = true;
				
				// Updates the last execution time
				lastExecution = System.nanoTime();
			}
		}
		
		return process;
	}
}
