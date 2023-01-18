package com.notes.actionQueue;

import java.util.HashMap;
import java.util.Map;

import com.notes.service.ContextHolderService;

public class ActionQueueHolder {

	private final Map<String, ActionQueue> QUEUE_HOLDER = new HashMap<>();
	
	private ActionQueueFactory actionQueueFactory;
	
	private ContextHolderService contextService;
	
	public ActionQueueHolder(ActionQueueFactory actionQueueFactory, ContextHolderService contextService) {
		this.actionQueueFactory = actionQueueFactory;
		this.contextService = contextService;
	}
	
	public ActionQueue getActionQueue() {
		ActionQueue ret = QUEUE_HOLDER.get(contextService.getCurrentContext());
		if (ret == null) {
			ret = actionQueueFactory.createActionQueue();
			QUEUE_HOLDER.put(contextService.getCurrentContext(), ret);
		}
		return ret;
	}
	
	void setQueues(Map<String, ActionQueue> queueHolders) {
		this.QUEUE_HOLDER.clear();
		QUEUE_HOLDER.putAll(queueHolders);
	}
	
}
