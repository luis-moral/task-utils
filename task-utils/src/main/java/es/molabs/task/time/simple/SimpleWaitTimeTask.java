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
 * Task that will wait for a time and then executes one time.
 */
public abstract class SimpleWaitTimeTask extends SimpleTimeTask
{
	private float waitTime;

	protected SimpleWaitTimeTask(float waitTime)
	{
		this.waitTime = waitTime;
	}
	
	protected float getWaitTime()
	{
		return waitTime;
	}
	
	protected boolean resolveProcess(float delta)
	{
		boolean process = false;
		
		// If the time passed is equal or greater than the wait time
		if (getTimePassed() >= waitTime)
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
