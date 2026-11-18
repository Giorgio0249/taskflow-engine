package com.taskflow.engine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import com.taskflow.model.Task;
import com.taskflow.model.TaskStatus;

class TaskQueueEngineTest {
	
	@Test
	void shouldProcessHighestPriorityTaskFirst() {
		TaskQueueEngine engine=new TaskQueueEngine();
		Task low=new Task("Clean logs", 2, 1);
		Task high=new Task("Fix payment bug", 9, 1);
		
		engine.submit(low);
		engine.submit(high);
		
		assertEquals(2, engine.pendingCount());
		
		Optional<Task> processed=engine.processNext();
		assertTrue(processed.isPresent());
		assertEquals("Fix payment bug", processed.get().getName());
		assertEquals(TaskStatus.COMPLETED, processed.get().getStatus());
		assertEquals(1, engine.pendingCount());
	}
	
	@Test
	void shouldReturnEmptyWhenQueueIsEmpty() {
		TaskQueueEngine engine=new TaskQueueEngine();
		assertTrue(engine.processNext().isEmpty());
	}
	
}
