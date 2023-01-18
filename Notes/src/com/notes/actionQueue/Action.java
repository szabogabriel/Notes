package com.notes.actionQueue;

public interface Action {
	
	default boolean mergable(Action action) {
		return false;
	}
	
	default void merge(Action action) {
		
	}
	
	void undo();
	
	void redo();

}
