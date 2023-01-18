package com.notes.gui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import com.notes.model.AppModel;
import com.notes.model.BaseModel;

public class GuiAppModel extends AppModel implements ListModel<GuiNotebookModel> {

	private List<ListDataListener> listDataListeners = new ArrayList<>();

	public GuiAppModel() {
	}

	public void addNotebook(GuiNotebookModel model, File fileLoadedFrom) {
		super.addNotebook(model, fileLoadedFrom);
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, getSize() - 1, getSize() - 1);
		listDataListeners.stream().forEach(e -> e.contentsChanged(event));
	}

	@Override
	public int getSize() {
		return getNotebookCount();
	}

	@Override
	public GuiNotebookModel getElementAt(int index) {
		return (GuiNotebookModel)(getNotebooks().get(index));
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listDataListeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listDataListeners.remove(l);
	}
	
	@Override
	protected void dirtyListener(BaseModel model) {
		int index = index(model);
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index, index);
		listDataListeners.stream().forEach(e -> e.contentsChanged(event));
	}
	
	private int index(BaseModel model) {
		int i = 0;
		for (BaseModel it : getNotebooks()) {
			if (it.equals(model)) {
				return i;
			}
			i++;
		}
		return -1;
	}

}
