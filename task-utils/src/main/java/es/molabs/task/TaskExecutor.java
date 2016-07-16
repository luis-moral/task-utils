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
package es.molabs.task;

public interface TaskExecutor 
{
	/**
	 * Adds a task to the executor.
	 * 
	 * @param task to add.
	 */
	public void add(Task task);
	
	/**
	 * Removes an existing task from the executor.
	 * 
	 * @param task to remove.
	 */
	public void remove(Task task);
	
	/**
	 * This method will run once each task in the executor. Each task will determine the time passed if needed.
	 */
	public void execute();
	
	/**
	 * This method will run once each task in the executor.
	 * 
	 * @param delta time passed in seconds.
	 */
	public void execute(float delta);
	
	/**
	 * Removes all task from the executor.
	 */
	public void clear();
	
	/**
	 * Returns the amount of task in this executor.
	 * 
	 * @return the amount of task in this executor.
	 */
	public int size();
}
