package com.notes.actionQueue.sizeLimit;

import com.notes.actionQueue.Action;
import com.notes.actionQueue.ActionQueue;

public class SizeLimitedActionQueue implements ActionQueue {
	
	private Action [] queue;
	
	private int limit;
	
	private int size = 0;
	private int index = 0;
	
	public SizeLimitedActionQueue(int size) {
		this.queue = new Action[size];
		this.limit = size;
	}

	@Override
	public void add(Action action) {
		if (index == limit) {
			removeOldestElement();
		}
		//TODO: this merge should be extracted from here.
		if (index > 0 && queue[index - 1].mergable(action)) {
			queue[index - 1].merge(action);
		} else {
			queue[index] = action;
			index++;
			size = index;
		}
	}
	
	private void removeOldestElement() {
		Action[] tmp = new Action[limit];
		System.arraycopy(queue, 1, tmp, 0, limit - 1);
		index--;
		size--;
		queue = tmp;
	}

	@Override
	public void undo() {
		if (index > 0) {
			queue[--index].undo();
		}
	}

	@Override
	public void redo() {
		if (index < size) {
			queue[index++].redo();
		}
	}

}
