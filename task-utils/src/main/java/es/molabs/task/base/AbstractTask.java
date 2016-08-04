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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.molabs.task.Task;

public abstract class AbstractTask implements Task
{
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private Exception error = null;	
	private Task next = null;
	
	protected AbstractTask()
	{
		this(null);
	}
	
	protected AbstractTask(AbstractTask next)
	{				
		this.next = next;
	}
	
	public void reset()
	{
		error = null;
	}
			
	public void execute(float delta)
	{	
		try
		{
			beforeProcess();
			
			doExecute(delta);
			
			afterProcess();
		}
		catch (Exception e)
		{
			error = e;
			
			logger.error(e.getLocalizedMessage(), e);
			
			onError(e);
		}
	}
	
	public Task getNext()
	{
		return next;
	}
	
	public void setNext(Task next)
	{
		this.next = next;
	}	
	
	public void added()
	{
		onCreate();
	}		
	
	public void removed()
	{
		onDispose();
	}
	
	/**
	 * Returns the last error thrown after calling process(). Null if there was no error.
	 * 
	 * @return The last error thrown after calling process().
	 */
	public Exception getError()
	{
		return error;
	}
	
	protected Logger getLogger()
	{
		return logger;
	}
	
	/**
	 * Hook that will be called once the task is all set.
	 */
	protected void onCreate()
	{		
	}
	
	/**
	 * Hook that will be called once the task is finished and removed from the queue.
	 */
	protected void onDispose()
	{
	}
	
	/**
	 * Hook that will be called before the process() call is made.
	 * 
	 * @throws Exception if an error occurs.
	 */
	protected void beforeProcess() throws Exception
	{
	}
	
	/**
	 * Hook that will be called after the process() call is made.
	 * 
	 * @throws Exception if an error occurs.
	 */
	protected void afterProcess() throws Exception
	{
	}
	
	/**
	 * Hook that will be called if an error occurs in the loop.
	 * 
	 * @param e exception thrown.
	 */
	protected void onError(Exception e)
	{		
	}
	
	/**
	 * Main method where this task logic should be executed.
	 * 
	 * @param delta time passed in seconds.
	 * 
	 * @throws Exception if an error occurs.
	 */
	protected abstract void doExecute(float delta) throws Exception;
}