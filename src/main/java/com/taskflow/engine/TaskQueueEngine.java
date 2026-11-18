package com.taskflow.engine;

import java.util.Comparator;
import java.util.Optional;
import java.util.PriorityQueue;
import com.taskflow.model.Task;

public class TaskQueueEngine {
	
	private final PriorityQueue<Task> queue;
	
	public TaskQueueEngine(){
		this.queue=new PriorityQueue<>(Comparator.comparingInt(Task::getPriority).reversed());
	}
	
	public void submit(Task task) {
		if(task==null) {
			throw new IllegalArgumentException("Task cannot be null");
		}
		queue.offer(task);
	}
	
	public Optional<Task> processNext() {
		Task next=queue.poll();
		if(next==null)
			return Optional.empty();
		
		next.strat();
		next.complete();
		return Optional.of(next);
	}
	
	public int pendingCount() {
		return queue.size();
	}

}
