package com.notes.actionQueue.sizeLimit;

import com.notes.actionQueue.ActionQueue;
import com.notes.actionQueue.ActionQueueFactory;
import com.notes.config.ConfigValue;
import com.notes.config.RuntimeConfig;

public class SizeLimitActionQueueFactory implements ActionQueueFactory {
	
	private final ConfigValue UNDO_LIMIT = RuntimeConfig.UNDO_LIMIT.getValue();

	@Override
	public ActionQueue createActionQueue() {
		return new SizeLimitedActionQueue(UNDO_LIMIT.getIntValue());
	}

}
