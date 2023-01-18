package com.notes.actionQueue.sizeLimit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.jupiter.api.Test;

import com.notes.actionQueue.Action;

public class SizeLimitActionQueueTest {
	
	class TestAction implements Action {

		private String name;
		private OutputStream out;
		
		public TestAction(String name, OutputStream out) {
			this.out = out;
			this.name = name;
		}
		
		@Override
		public void undo() {
			try {
				out.write((name + "undo\n").getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void redo() {
			try {
				out.write((name + "redo\n").getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	void undoTest() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Action a1 = new TestAction("1", baos);
		Action a2 = new TestAction("2", baos);
		Action a3 = new TestAction("3", baos);
		
		SizeLimitedActionQueue queue = new SizeLimitedActionQueue(5);
		
		queue.add(a1);
		queue.add(a2);
		queue.add(a3);
		
		queue.undo();
		queue.undo();
		queue.undo();
		
		String content = baos.toString();
		
		assertEquals("3undo\n2undo\n1undo\n", content);
		
		queue.undo();
		
		content = baos.toString();
		
		assertEquals("3undo\n2undo\n1undo\n", content);
	}
	
	@Test
	void redoTest() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Action a1 = new TestAction("1", baos);
		Action a2 = new TestAction("2", baos);
		Action a3 = new TestAction("3", baos);
		
		SizeLimitedActionQueue queue = new SizeLimitedActionQueue(5);
		
		queue.add(a1);
		queue.add(a2);
		queue.add(a3);
		
		queue.undo();
		queue.undo();
		queue.undo();
		queue.redo();
		queue.redo();
		queue.redo();
		
		String content = baos.toString();
		
		assertEquals("3undo\n2undo\n1undo\n1redo\n2redo\n3redo\n", content);
		
		// Make sure no more redo's are done.
		queue.redo();
		
		content = baos.toString();
		
		assertEquals("3undo\n2undo\n1undo\n1redo\n2redo\n3redo\n", content);
	}
	
	@Test
	void overridePreviousEntriesAfterUndoByAddingNewEntries() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Action a1 = new TestAction("1", baos);
		Action a2 = new TestAction("2", baos);
		Action a3 = new TestAction("3", baos);
		Action a4 = new TestAction("4", baos);
		
		SizeLimitedActionQueue queue = new SizeLimitedActionQueue(5);
		
		queue.add(a1);
		queue.add(a2);
		queue.add(a3);
		
		// Remove all previous elements.
		queue.undo();
		queue.undo();
		queue.undo();
		
		// Add a new one. This should remove all previous actions.
		queue.add(a4);
		
		// Make it noticeable in the logs.
		queue.undo();
		
		String content = baos.toString();
		
		assertEquals("3undo\n2undo\n1undo\n4undo\n", content);
		
		// Reintroduce A4.
		queue.redo();
		
		content = baos.toString();
		
		assertEquals("3undo\n2undo\n1undo\n4undo\n4redo\n", content);
		
		// Now it should be no more elements after A4.
		queue.redo();
		
		content = baos.toString();
		
		assertEquals("3undo\n2undo\n1undo\n4undo\n4redo\n", content);
	}

}
