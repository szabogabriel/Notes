package com.notes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.notes.serialization.SerializationProvider;

public class NotebookModel extends BaseModel {
	
	private String name;
	
	private List<TopicModel> topics = new ArrayList<>();
	
	public NotebookModel(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void addTopic(TopicModel topic) {
		topic.addDirtyListener(this::dirtyListener);
		topics.add(topic);
		setDirty(true);
	}
	
	public void removeTopic(TopicModel topic) {
		topics.remove(topic);
		setDirty(true);
	}
	
	public void removeTopic(String topicName) {
		getTopics().stream().filter(e -> e.getName().equals(topicName)).findAny().ifPresent(this::removeTopic);
	}
	
	public List<TopicModel> getTopics() {
		return Collections.unmodifiableList(topics);
	}

	@Override
	public String serialize(SerializationProvider provider) {
		return provider.serialize(this);
	}

	private void dirtyListener(BaseModel model) {
		if (model.isDirty()) {
			setDirty(true);
		}
	}
}
