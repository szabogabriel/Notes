package com.notes.serialize.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.notes.model.NoteModel;
import com.notes.model.NoteTextBoxModel;
import com.notes.model.NotebookModel;
import com.notes.model.TopicModel;
import com.notes.serialization.xml.XmlDeserializationProvider;

public class XmlDeserializationProviderTest {
	
	private static final String NOTE_TEXT_BOX_XML = "<noteTextBox height=\"20\" width=\"10\" x=\"5\" y=\"6\">Text content</noteTextBox>";
	private static final String NOTE_XML = "<note name=\"Note model name\">" + NOTE_TEXT_BOX_XML + "</note>";
	private static final String TOPIC_XML = "<topic name=\"Topic name\">" + NOTE_XML + "</topic>";
	private static final String NOTEBOOK_XML = "<notebook name=\"Notebook name\">" + TOPIC_XML + "</notebook>";
	
	private XmlDeserializationProvider dp = new XmlDeserializationProvider();
	
	@AfterEach
	void afterEach() {
		System.out.println("======================");
	}
	
	@Test
	void testDeserializeNoteTextBox() {
		System.out.println("Test deserialize NoteTextBox");
		List<NoteTextBoxModel> data = dp.parseNoteTextBox(NOTE_TEXT_BOX_XML);
		
		assertNotNull(data);
		assertEquals(1, data.size());
		
		NoteTextBoxModel model = data.get(0);
		
		checkIfStandardNoteBoxModel(model);
	}
	
	void checkIfStandardNoteBoxModel(NoteTextBoxModel model) {
		assertEquals(5, model.getPositionX());
		assertEquals(6, model.getPositionY());
		assertEquals(20, model.getHeight());
		assertEquals(10, model.getWidth());
		assertEquals("Text content", model.getContent());
	}
	
	@Test
	void testDeserializeNote() {
		System.out.println("Test deserialize Note");
		List<NoteModel> data = dp.parseNote(NOTE_XML);
		
		assertNotNull(data);
		assertEquals(1, data.size());
		
		NoteModel model = data.get(0);
		
		checkIfStandardNoteModel(model);
	}
	
	void checkIfStandardNoteModel(NoteModel model) {
		assertEquals("Note model name", model.getName());
		model.getTextBoxes().stream().forEach(this::checkIfStandardNoteBoxModel);
	}
	
	@Test
	void testDeserializeTopic() {
		System.out.println("Test deserialize Topic");
		List<TopicModel> data = dp.parseTopic(TOPIC_XML);
		
		assertNotNull(data);
		assertEquals(1, data.size());
		
		TopicModel model = data.get(0);
		
		checkIfStandardTopicModel(model);
	}
	
	void checkIfStandardTopicModel(TopicModel model) {
		assertEquals("Topic name", model.getName());
		model.getNotes().stream().forEach(this::checkIfStandardNoteModel);
	}
	
	@Test
	void testDeserializeNotebook() {
		System.out.println("Test deserialize Notebook");
		List<NotebookModel> data = dp.parseNotebook(NOTEBOOK_XML);
		
		assertNotNull(data);
		assertEquals(1, data.size());
		
		NotebookModel model = data.get(0);
		
		checkIfStandardNotebookModel(model);
	}
	
	void checkIfStandardNotebookModel(NotebookModel model) {
		assertEquals("Notebook name", model.getName());
		model.getTopics().stream().forEach(this::checkIfStandardTopicModel);
	}

}
