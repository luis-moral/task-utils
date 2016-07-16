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

public interface Task 
{
	public void execute(float delta);
	
	/**
	 * Hook to inform that this object has been added to the queue.
	 */
	public void added();
		
	/**
	 * Hook to inform that this object has been removed to the queue.
	 */
	public void removed();
		
	/**
	 * Returns if the task is finished.
	 * 
	 * @return if the task is finished.
	 */
	public boolean isFinished();
	
	/**
	 * Resets the task to his initial status. 
	 */
	public void reset();	
	
	/**
	 * Returns the task to execute after this one is finished.
	 * 
	 * @return task to execute after this one is finished.
	 */
	public Task getNext();
}
