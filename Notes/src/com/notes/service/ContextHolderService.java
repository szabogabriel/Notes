package com.notes.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.notes.config.ConfigValue;
import com.notes.config.RuntimeConfig;
import com.notes.gui.model.GuiAppModel;
import com.notes.gui.model.GuiNotebookModel;
import com.notes.model.NoteModel;
import com.notes.model.NotebookModel;
import com.notes.model.TopicModel;
import com.notes.serialization.DeserializationProvider;
import com.notes.serialization.SerializationProvider;

public class ContextHolderService {

	private final ConfigValue DEFAULT_NOTEBOOK_NAME = RuntimeConfig.DEFAULT_NOTEBOOK_NAME.getValue();

	private final ConfigValue USER_NOTEBOOKS = RuntimeConfig.USER_NOTEBOOKS.getValue();

	private DeserializationProvider deserializationProvider;

	private SerializationProvider serializationProvider;
	
	private GuiAppModel appModel = null;
	
	private String context = "default";

	public ContextHolderService(DeserializationProvider deserializationProvider, SerializationProvider serializationProvider) {
		this.deserializationProvider = deserializationProvider;
		this.serializationProvider = serializationProvider;
	}

	public GuiAppModel getAppModel() {
		if (appModel == null) {
			synchronized(this) {
				if (appModel == null) {
					appModel = new GuiAppModel();
			
					readKnownNotebooks(appModel);
					createDefaultNotebook(appModel);
					appModel.clearDirty();
				}
			}
		}

		return appModel;
	}
	
	public void setContext(String context) {
		this.context = context;
	}
	
	public String getCurrentContext() {
		return context;
	}
	
	public void saveNotebook(GuiNotebookModel model) {
		File targetFile = appModel.getFileForNotebook(model);
		saveNotebook(model, targetFile);
	}

	private void saveNotebook(GuiNotebookModel model, File file) {
		String serializedModel = serializationProvider.serialize(model);
		try {
			StandardOpenOption openOption = StandardOpenOption.TRUNCATE_EXISTING; 
			if (!file.exists()) {
				openOption = StandardOpenOption.CREATE;
			}
			Files.write(file.toPath(), serializedModel.getBytes(), openOption, StandardOpenOption.WRITE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<String> knownFiles = new ArrayList<>();
		knownFiles.addAll(USER_NOTEBOOKS.getListValue());
		if (!knownFiles.stream().filter(f -> f.equals(file.getAbsolutePath())).findAny().isPresent()) {
			knownFiles.add(file.getAbsolutePath());
			USER_NOTEBOOKS.save(knownFiles);
		}
	}
	
	public void loadNotebook(File file) {
		List<GuiNotebookModel> notebooks = loadNotebooksFromFile(file);
		if (notebooks != null && notebooks.size() > 0) {
			List<String> storedNotebooks = new ArrayList<>();
			storedNotebooks.addAll(USER_NOTEBOOKS.getListValue());
			storedNotebooks.add(file.getAbsolutePath());
			USER_NOTEBOOKS.save(storedNotebooks);
			
			for (GuiNotebookModel it : notebooks) {
				appModel.addNotebook(it, file);
			}
		}
	}

	private void createDefaultNotebook(GuiAppModel model) {
		File defaultNotebookFile = new File(DEFAULT_NOTEBOOK_NAME.getStringValue());
		List<GuiNotebookModel> defaultNotebooks = loadNotebooksFromFile(defaultNotebookFile);

		if (defaultNotebooks.size() == 0 && model.getNotebookCount() == 0) {
			NoteModel note = new NoteModel("Default note");
			
			TopicModel topic = new TopicModel("Default topic");
			topic.addNote(note);
			
			NotebookModel notebook = new NotebookModel("Default notebook");
			notebook.addTopic(topic);
			
			GuiNotebookModel newNotebook = new GuiNotebookModel(notebook);
			model.addNotebook(newNotebook, defaultNotebookFile);
			
			saveNotebook(newNotebook, defaultNotebookFile);
		} else {
			defaultNotebooks.stream().forEach(e -> model.addNotebook(e, defaultNotebookFile));
		}
	}

	private void readKnownNotebooks(GuiAppModel model) {
		List<String> notebooks = USER_NOTEBOOKS.getListValue();

		for (String notebook : notebooks) {
			File notebookFile = new File(notebook);
			if (notebookFile.exists()) {
				loadNotebooksFromFile(notebookFile).stream().forEach(e -> model.addNotebook(e, notebookFile));
			}
		}
	}

	private List<GuiNotebookModel> loadNotebooksFromFile(File file) {
		List<GuiNotebookModel> ret = Collections.emptyList();
		String content = null;
		if (file.exists()) {
			try {
				content = Files.readString(file.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (content != null) {
			ret = deserializationProvider.parseNotebook(content).stream().map(GuiNotebookModel::new)
					.collect(Collectors.toList());
		}
		return ret;
	}

}
