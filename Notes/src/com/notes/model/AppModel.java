package com.notes.model;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AppModel {

	private Map<NotebookModel, File> notebookFiles = new HashMap<>();

	public void addNotebook(NotebookModel model, File fileLoadedFrom) {
		notebookFiles.put(model, fileLoadedFrom);
		model.addDirtyListener(this::dirtyListener);
	}

	public List<NotebookModel> getNotebooks() {
		return Collections.unmodifiableList(notebookFiles.keySet().stream().collect(Collectors.toList()));
	}

	public File getFileForNotebook(NotebookModel model) {
		return notebookFiles.get(model);
	}

	public List<NotebookModel> getNotebooksForFile(File file) {
		return notebookFiles.entrySet().stream().filter(e -> e.getValue().equals(file)).map(e -> e.getKey())
				.collect(Collectors.toList());
	}

	public int getNotebookCount() {
		return notebookFiles.size();
	}
	
	public void clearDirty() {
		for (NotebookModel it1 : notebookFiles.keySet()) {
			it1.setDirty(false);
			for (TopicModel it2 : it1.getTopics()) {
				it2.setDirty(false);
				for (NoteModel it3 : it2.getNotes()) {
					it3.setDirty(false);
					for (NoteTextBoxModel it4 : it3.getTextBoxes()) {
						it4.setDirty(false);
					}
				}
			}
		}
	}

	protected void dirtyListener(BaseModel model) {
		// To be overridden.
	}

}
