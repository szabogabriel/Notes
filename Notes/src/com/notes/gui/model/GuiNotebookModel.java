package com.notes.gui.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import com.notes.model.NotebookModel;

public class GuiNotebookModel extends NotebookModel implements ListModel<GuiTopicModel> {

	private List<ListDataListener> listDataListeners = new ArrayList<>();

	public GuiNotebookModel(NotebookModel model) {
		super(model.getName());
		model.getTopics().stream().map(GuiTopicModel::new).forEach(this::addTopic);
	}

	public void addGuiTopicModel(GuiTopicModel model) {
		super.addTopic(model);
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, getSize() - 1, getSize() - 1);
		listDataListeners.stream().forEach(e -> e.contentsChanged(event));
	}

	public String toString() {
		return (isDirty() ? "* " : "") + super.getName();
	}

	@Override
	public int getSize() {
		return super.getTopics().size();
	}

	@Override
	public GuiTopicModel getElementAt(int index) {
		return (GuiTopicModel) super.getTopics().get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listDataListeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listDataListeners.remove(l);
	}

}
