package com.taskflow.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TaskTest {

	//test percorso felice
	@Test
	void shouldTransitionFromPendingToCompleted() {
		Task task=new Task("Backup database", 5, 2);
		assertEquals(TaskStatus.PENDING, task.getStatus());
		
		task.strat();
		assertEquals(TaskStatus.RUNNING, task.getStatus());
		
		task.complete();
		assertEquals(TaskStatus.COMPLETED, task.getStatus());
	}
	
	//test logica retry 
	@Test
	void shouldRetryUntilMaxRetriesReached() {
		Task task=new Task("Send Email", 8, 2);
		
		task.strat();
		task.fail();
		assertEquals(TaskStatus.PENDING, task.getStatus());
		assertEquals(1, task.getRetryCount());
		
		task.strat();
		task.fail();
		assertEquals(TaskStatus.PENDING, task.getStatus());
		assertEquals(2, task.getRetryCount());
		
		task.strat();
		task.fail();
		assertEquals(TaskStatus.FAILED, task.getStatus());
	}
	
	//test casi limite/errori
	@Test
	void shouldThrowExceptionForInvalidPriority() {
		assertThrows(IllegalArgumentException.class, ()->new Task("Invalid task", 15, 1));
	}
	

}
