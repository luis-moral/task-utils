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

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import es.molabs.task.Task;
import es.molabs.task.TaskExecutor;

public class SingleThreadTaskExecutor implements TaskExecutor
{
	//private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private Collection<Task> taskCollection = null;
	
	private long lastUpdate;
	private long lastExecutionTime;
	
	/**
	 * Creates a new TaskExecutor with a ConcurrentLinkedQueue as underlying storage.
	 */
	public SingleThreadTaskExecutor()
	{
		this(new ConcurrentLinkedQueue<Task>());
	}
	
	/**
	 * Creates a new TaskExecutor with the collection implementation parameter as underlying storage. 
	 * 
	 * @param collection underlying storage.
	 */
	public SingleThreadTaskExecutor(Collection<Task> collection)
	{
		this.taskCollection = collection;
		
		lastUpdate = 0;
		lastExecutionTime = 0;
	}
	
	public void add(Task task)
	{
		// Calls the task executor task added hook
		added(task);
		
		// Calls the task added hook
		task.added();
		
		// Adds the task to the collection
		taskCollection.add(task);
	}
	
	public void remove(Task task)
	{
		// Calls the task executor task removed hook
		removed(task);
		
		// Removes the task from the collection
		taskCollection.remove(task);
		
		// Calls the task removed hook
		task.removed();
	}	
	
	public void execute()
	{
		long timeSinceLastUpdate = (lastUpdate > 0 ? System.nanoTime() - lastUpdate : 0);
		
		execute((timeSinceLastUpdate + lastExecutionTime) / 1_000_000_000f);
	}
	
	public void execute(float delta)
	{
		long startTime = System.nanoTime();
		
		// For each task in the collection
		Iterator<Task> iterator = taskCollection.iterator();
		while (iterator.hasNext())
		{
			Task task = iterator.next();
			
			// If the task in not finished
			if (!task.isFinished())
			{
				// Executes the task
				task.execute(delta);
			}
			
			// If the task if finished after executing it
			if (task.isFinished())
			{
				// Calls the task executor task removed hook
				removed(task);
				
				// Removes the task from the collection
				iterator.remove();
				
				// Calls the task removed hook
				task.removed();
				
				// If it has next task
				if (task.getNext() != null)
				{
					// Adds it to the executor
					add(task.getNext());
				}
			}			
		}
		
		lastExecutionTime = System.nanoTime() - startTime;
		lastUpdate = System.nanoTime();
	}
	
	public void clear() 
	{
		// For each task in the collection
		Iterator<Task> iterator = taskCollection.iterator();
		while (iterator.hasNext())
		{	
			Task task = iterator.next();
			
			// Calls the task executor task removed hook
			removed(task);			
			
			// Removes the task from the collection
			iterator.remove();
			
			// Calls the task removed hook
			task.removed();
		}
	}
	
	public int size() 
	{
		return taskCollection.size();
	}
	
	/**
	 * Hook that will be called before a task is added to the queue.
	 * 
	 * @param task to be added to the queue.
	 */
	protected void added(Task task)
	{		
	}
	
	/**
	 * Hook that will be called before a task is removed from the queue.
	 * 
	 * @param task to be removed from the queue.
	 */
	protected void removed(Task task)
	{		
	}
}
