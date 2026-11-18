package com.taskflow;

import com.taskflow.engine.TaskQueueEngine;
import com.taskflow.model.Task;

public class App {
    public static void main(String[] args) {
    	TaskQueueEngine engine=new TaskQueueEngine();
    	
    	engine.submit(new Task("Generate monthly report", 3, 1));
    	engine.submit(new Task("Critical security patch", 10, 2));
    	engine.submit(new Task("Sync user preferences", 6, 1));
    	
    	System.out.println("Tasks in coda: "+engine.pendingCount());
    	
    	while(engine.pendingCount()>0) {
    		engine.processNext().ifPresent(task->System.out.println("Eseguito -> "+task));
    	}
    }
}
