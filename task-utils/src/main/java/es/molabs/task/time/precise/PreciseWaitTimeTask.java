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
 * Task that will wait for a time and then executes one time.
 */
public abstract class PreciseWaitTimeTask extends PreciseTimeTask
{
	private float waitTime;

	protected PreciseWaitTimeTask(float waitTime)
	{
		this.waitTime = waitTime;
	}
	
	protected float getWaitTime()
	{
		return waitTime;
	}
	
	protected boolean resolveProcess(long timeSinceLastUpdate)
	{
		boolean process = false;
		
		if (getTimePassed() >= (long) (waitTime * 1_000_000_000f))
		{
			process = true;
			
			setFinished(true);
		}
		
		return process;
	}

	/**
	 * Main method where this task logic should be executed.
	 * 
	 * @param delta time passed since the last call to this method.
	 */
	protected abstract void doProcess(float delta);
}
