package com.notes.serialize.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Dimension;
import java.awt.Point;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.notes.model.NoteModel;
import com.notes.model.NoteTextBoxModel;
import com.notes.model.NotebookModel;
import com.notes.model.TopicModel;
import com.notes.serialization.xml.XmlSerializationProvider;

public class XmlSerializationProviderTest {
	
	private static final String NOTE_TEXT_BOX_XML = "<noteTextBox height=\"20\" width=\"10\" x=\"5\" y=\"6\">Text content</noteTextBox>\n";
	
	private static final String NOTE_XML = 
			  "<note name=\"Note model name 1\">\n"
			+ "    <noteTextBox height=\"20\" width=\"10\" x=\"5\" y=\"6\">Text content</noteTextBox>\n"
			+ "</note>\n";
	
	private static final String TOPIC_XML = 
			  "<topic name=\"Topic name 1\">\n"
			+ "    <note name=\"Note model name 1\">\n"
			+ "        <noteTextBox height=\"20\" width=\"10\" x=\"5\" y=\"6\">Text content</noteTextBox>\n"
			+ "    </note>\n"
			+ "</topic>\n";
	
	private static final String NOTEBOOK_XML = 
			  "<notebook name=\"Notebook name\">\n"
			+ "    <topic name=\"Topic name 1\">\n"
			+ "        <note name=\"Note model name 1\">\n"
			+ "            <noteTextBox height=\"20\" width=\"10\" x=\"5\" y=\"6\">Text content</noteTextBox>\n"
			+ "        </note>\n"
			+ "    </topic>\n"
			+ "</notebook>\n";
	private static final String NOTEBOOK_MULTI_TOPIC_XML = 
			  "<notebook name=\"Notebook name\">\n" 
			+ "    <topic name=\"Topic name 1\">\n"
			+ "        <note name=\"Note model name 1\">\n"
			+ "            <noteTextBox height=\"20\" width=\"10\" x=\"5\" y=\"6\">Text content</noteTextBox>\n"
			+ "        </note>\n"
			+ "    </topic>\n"
			+ "    <topic name=\"Topic name 2\">\n"
			+ "        <note name=\"Note model name 1\">\n"
			+ "            <noteTextBox height=\"20\" width=\"10\" x=\"5\" y=\"6\">Text content</noteTextBox>\n"
			+ "        </note>\n"
			+ "    </topic>\n"
			+ "</notebook>\n";
	private static final String NOTEBOOK_MULTI_NOTE_XML = 
			  "<notebook name=\"Notebook name\">\n" 
			+ "    <topic name=\"Topic name 1\">\n"
			+ "        <note name=\"Note model name 1\">\n"
			+ "            <noteTextBox height=\"20\" width=\"10\" x=\"5\" y=\"6\">Text content</noteTextBox>\n"
			+ "        </note>\n"
			+ "        <note name=\"Note model name 2\">\n"
			+ "            <noteTextBox height=\"20\" width=\"10\" x=\"5\" y=\"6\">Text content</noteTextBox>\n"
			+ "        </note>\n"
			+ "    </topic>\n"
			+ "</notebook>\n";
	
	private XmlSerializationProvider sp = new XmlSerializationProvider();
	
	@AfterEach
	void afterEach() {
		System.out.println("======================");
	}
	
	@Test
	void serializeNoteTextBox() {
		System.out.println("NoteTextBox serialize test:\n");
		NoteTextBoxModel model = createNoteTextBox();
		
		String xml = model.serialize(sp);
		
		String expected = NOTE_TEXT_BOX_XML;
		
		System.out.println("Expected:\n" + expected);
		System.out.println("Created:\n" + xml);
		
		assertEquals(expected, xml);
	}
	
	@Test
	void serializeNote() {
		System.out.println("Note serialize test:\n");
		
		String xml = createNoteModel(1, 1).serialize(sp);
		
		String expected = NOTE_XML;
		
		System.out.println("Expected\n" + expected);
		System.out.println("Created:\n" + xml);
		
		assertEquals(expected, xml);
	}
	
	@Test
	void serializeTopic() {
		System.out.println("Topic serialize test:\n");
		
		String xml = createTopicModel(1, 1, 1).serialize(sp);
		
		String expected = TOPIC_XML;
		
		System.out.println("Expected:\n" + expected);
		System.out.println("Created:\n" + xml);
		
		assertEquals(expected, xml);
	}
	
	@Test
	void serializeNotebook() {
		System.out.println("Notebook serialize test:\n");
		
		String xml = createNotebookModel(1, 1, 1).serialize(sp);
		
		String expected = NOTEBOOK_XML;
		
		System.out.println("Expected:\n" + expected);
		System.out.println("Created:\n" + xml);
		
		assertEquals(expected, xml);
	}
	
	@Test
	void serializeNotebookWithMultipleTopics() {
		System.out.println("Notebook with multiple topic serialize test:\n");
		
		String xml = createNotebookModel(2, 1, 1).serialize(sp);
		
		String expected = NOTEBOOK_MULTI_TOPIC_XML;
		
		System.out.println("Expected:\n" + expected);
		System.out.println("Created:\n" + xml);
		
		assertEquals(expected, xml);
	}
	
	@Test
	void serializeNotebookWithMultipleNote() {
		System.out.println("Notebook with multiple topic serialize test:\n");
		
		String xml = createNotebookModel(1, 2, 1).serialize(sp);
		
		String expected = NOTEBOOK_MULTI_NOTE_XML;
		
		System.out.println("Expected:\n" + expected);
		System.out.println("Created:\n" + xml);
		
		assertEquals(expected, xml);
	}
	
	private NotebookModel createNotebookModel(int topicCount, int noteCount, int noteModelCount) {
		NotebookModel model = new NotebookModel("Notebook name");
		
		for (int i = 1; i <= topicCount; i++)
			model.addTopic(createTopicModel(i, noteCount, noteModelCount));
		
		return model;
	}
	
	private TopicModel createTopicModel(int id, int noteCount, int noteModelCount) {
		TopicModel model = new TopicModel("Topic name " + id);
		for (int i = 1; i <= noteCount; i++)
			model.addNote(createNoteModel(i, noteModelCount));
		return model;
	}
	
	private NoteModel createNoteModel(int id, int noteModelCount) {
		NoteModel model = new NoteModel("Note model name " + id);
		for (int i = 1; i <= noteModelCount; i++)
			model.addNoteTextBoxModel(createNoteTextBox());
		return model;
	}
	
	private NoteTextBoxModel createNoteTextBox() {
		return new NoteTextBoxModel(new Point(5, 6), new Dimension(10, 20), "Text content");
	}

}
