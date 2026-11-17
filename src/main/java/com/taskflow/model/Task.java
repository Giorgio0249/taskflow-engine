package com.taskflow.model;

import java.util.UUID;

public class Task {
	private final String id;
	private final String name;
	private final int priority;
	private final int maxRetries;
	private int retryCount;
	private TaskStatus status;
	
	//costruttore con logica
	public Task(String name, int priority, int maxRetries) {
		if(name==null || name.isBlank())
			throw new IllegalArgumentException("Task name cannot be empty");
		
		if(priority<1 || priority>10)
			throw new IllegalArgumentException("Priority must be between 1 and 10");
		
		this.id=UUID.randomUUID().toString();
		this.name=name;
		this.priority=priority;
		this.maxRetries=Math.max(0, maxRetries);
		this.retryCount=0;
		this.status=TaskStatus.PENDING;
	}
	
	//metodi
	public void strat() {
		if(this.status!=TaskStatus.PENDING)
			throw new IllegalStateException("Only PENDING tasks can be started");
		
		this.status=TaskStatus.RUNNING;
	}
	
	public void complete() {
		if(this.status!=TaskStatus.RUNNING)
			throw new IllegalStateException("Only RUNNING tasks can be completed");
		
		this.status=TaskStatus.COMPLETED;
	}
	
	public void fail() {
		if(this.status != TaskStatus.RUNNING)
			throw new IllegalStateException("Only RUNNING tasks can fail");
		
		if(this.retryCount<this.maxRetries) {
			this.retryCount++;
			this.status=TaskStatus.PENDING;
		}
		else
			this.status=TaskStatus.FAILED;
	}
	
	public String getId() {return id;}
	public String getName() {return name;}
	public int getPriority() {return priority;}
	public int getRetryCount() {return retryCount;}
	public TaskStatus getStatus() {return status;}
}
