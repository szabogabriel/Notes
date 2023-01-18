package com.notes.serialization.xml;

import java.awt.Dimension;
import java.awt.Point;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.notes.model.NoteModel;
import com.notes.model.NoteTextBoxModel;
import com.notes.model.NotebookModel;
import com.notes.model.TopicModel;
import com.notes.serialization.DeserializationProvider;

public class XmlDeserializationProvider implements DeserializationProvider {
	private static final String XML_NOTE_TEXT_BOX_ATTRIBUTE_X = "x";
	private static final String XML_NOTE_TEXT_BOX_ATTRIBUTE_Y = "y";
	private static final String XML_NOTE_TEXT_BOX_ATTRIBUTE_WIDTH = "width";
	private static final String XML_NOTE_TEXT_BOX_ATTRIBUTE_HEIGHT = "height";

	private static final String XML_NOTEBOOK_ATTRIBUTE_NAME = "name";

	private static final String XML_NOTE_ATTRIBUTE_NAME = "name";

	private static final String XML_TOPIC_ATTRIBUTE_NAME = "name";

	private DocumentBuilderFactory dbFactory;
	private DocumentBuilder dBuilder;

	public XmlDeserializationProvider() {
		try {
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<NotebookModel> parseNotebook(String xml) {
		List<NotebookModel> ret = Collections.emptyList();
		try {
			Document doc = dBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
			doc.getDocumentElement().normalize();

			ret = parseNotebook(doc.getChildNodes());
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	private List<NotebookModel> parseNotebook(NodeList nodes) {
		List<NotebookModel> ret = new ArrayList<>(nodes.getLength());
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			NamedNodeMap attributes = node.getAttributes();
			String name = attributes.getNamedItem(XML_NOTEBOOK_ATTRIBUTE_NAME).getNodeValue();

			NotebookModel topicModel = new NotebookModel(name);

			NodeList topicNodes = node.getChildNodes();
			parseTopic(topicNodes).stream().forEach(e -> topicModel.addTopic(e));

			ret.add(topicModel);
		}
		return ret;
	}

	@Override
	public List<TopicModel> parseTopic(String xml) {
		List<TopicModel> ret = Collections.emptyList();
		try {
			Document doc = dBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
			doc.getDocumentElement().normalize();

			ret = parseTopic(doc.getChildNodes());
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	private List<TopicModel> parseTopic(NodeList nodes) {
		List<TopicModel> ret = new ArrayList<>(nodes.getLength());
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node != null) {
				NamedNodeMap attributes = node.getAttributes();
				if (attributes != null) {
					String name = attributes.getNamedItem(XML_TOPIC_ATTRIBUTE_NAME).getNodeValue();

					TopicModel topicModel = new TopicModel(name);

					NodeList noteModelNodes = node.getChildNodes();
					parseNote(noteModelNodes).stream().forEach(e -> topicModel.addNote(e));

					ret.add(topicModel);
				}
			}
		}
		return ret;
	}

	@Override
	public List<NoteModel> parseNote(String xml) {
		List<NoteModel> ret = Collections.emptyList();
		try {
			Document doc = dBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
			doc.getDocumentElement().normalize();

			ret = parseNote(doc.getChildNodes());
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}

		return ret;
	}

	private List<NoteModel> parseNote(NodeList nodes) {
		List<NoteModel> ret = new ArrayList<>(nodes.getLength());
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node != null) {
				NamedNodeMap attributes = node.getAttributes();
				if (attributes != null) {
					String name = attributes.getNamedItem(XML_NOTE_ATTRIBUTE_NAME).getNodeValue();

					NoteModel noteModel = new NoteModel(name);

					NodeList noteTextBoxNodes = node.getChildNodes();
					parseNoteTextBox(noteTextBoxNodes).stream().forEach(e -> noteModel.addNoteTextBoxModel(e));

					ret.add(noteModel);
				}
			}
		}
		return ret;
	}

	@Override
	public List<NoteTextBoxModel> parseNoteTextBox(String xml) {
		List<NoteTextBoxModel> ret = Collections.emptyList();
		try {
			Document doc = dBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
			doc.getDocumentElement().normalize();

			ret = parseNoteTextBox(doc.getChildNodes());
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	private List<NoteTextBoxModel> parseNoteTextBox(NodeList nodes) {
		List<NoteTextBoxModel> ret = new ArrayList<>(nodes.getLength());
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node != null) {
				NamedNodeMap attributes = node.getAttributes();
				if (attributes != null) {
					int x = Integer.parseInt(attributes.getNamedItem(XML_NOTE_TEXT_BOX_ATTRIBUTE_X).getNodeValue());
					int y = Integer.parseInt(attributes.getNamedItem(XML_NOTE_TEXT_BOX_ATTRIBUTE_Y).getNodeValue());
					int width = Integer
							.parseInt(attributes.getNamedItem(XML_NOTE_TEXT_BOX_ATTRIBUTE_WIDTH).getNodeValue());
					int height = Integer
							.parseInt(attributes.getNamedItem(XML_NOTE_TEXT_BOX_ATTRIBUTE_HEIGHT).getNodeValue());
					String content = node.getTextContent();

					NoteTextBoxModel tmp = new NoteTextBoxModel(new Point(x, y), new Dimension(width, height), content);
					ret.add(tmp);
				}
			}
		}
		return ret;
	}

}
