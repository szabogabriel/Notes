package com.notes.actionQueue;

public interface ActionQueue {
	
	void add(Action action);
	
	void undo();
	
	void redo();

}
