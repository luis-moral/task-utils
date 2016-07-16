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
 * Task that will only run once.
 */
public abstract class SingleRunTask extends AbstractTask
{
	private boolean finished;
		
	protected SingleRunTask()
	{
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
	
	protected void afterProcess()
	{
		finished = true;
	}
	
	protected void doExecute(float delta)
	{
		doProcess();
	}
	
	protected abstract void doProcess();
}
