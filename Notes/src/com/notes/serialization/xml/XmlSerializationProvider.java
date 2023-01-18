package com.notes.serialization.xml;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.notes.model.NoteModel;
import com.notes.model.NoteTextBoxModel;
import com.notes.model.NotebookModel;
import com.notes.model.TopicModel;
import com.notes.serialization.SerializationProvider;

public class XmlSerializationProvider implements SerializationProvider {

	private static final String XML_NOTE_TEXT_BOX_ROOT_ELEMENT = "noteTextBox";
	private static final String XML_NOTE_TEXT_BOX_ATTRIBUTE_X = "x";
	private static final String XML_NOTE_TEXT_BOX_ATTRIBUTE_Y = "y";
	private static final String XML_NOTE_TEXT_BOX_ATTRIBUTE_WIDTH = "width";
	private static final String XML_NOTE_TEXT_BOX_ATTRIBUTE_HEIGHT = "height";

	private static final String XML_NOTEBOOK_ROOT_ELEMENT = "notebook";
	private static final String XML_NOTEBOOK_ATTRIBUTE_NAME = "name";

	private static final String XML_NOTE_ROOT_ELEMENT = "note";
	private static final String XML_NOTE_ATTRIBUTE_NAME = "name";

	private static final String XML_TOPIC_ROOT_ELEMENT = "topic";
	private static final String XML_TOPIC_ATTRIBUTE_NAME = "name";

	private DocumentBuilderFactory docFactory;
	private DocumentBuilder docBuilder;

	public XmlSerializationProvider() {
		try {
			docFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docFactory.newDocumentBuilder();
		} catch (Exception e) {

		}
	}

	@Override
	public String serialize(NotebookModel model) {
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement(XML_NOTEBOOK_ROOT_ELEMENT);

		rootElement.setAttribute(XML_NOTEBOOK_ATTRIBUTE_NAME, model.getName());
		model.getTopics().stream().map(e -> serialize(e, doc)).forEach(e -> rootElement.appendChild(e));

		doc.appendChild(rootElement);

		String ret = serializeDom(doc);

		return ret;
	}

	@Override
	public String serialize(NoteTextBoxModel model) {
		Document doc = docBuilder.newDocument();

		doc.appendChild(serialize(model, doc));

		String ret = serializeDom(doc);

		return ret;
	}

	private Element serialize(NoteTextBoxModel model, Document doc) {
		Element element = doc.createElement(XML_NOTE_TEXT_BOX_ROOT_ELEMENT);

		element.setAttribute(XML_NOTE_TEXT_BOX_ATTRIBUTE_X, model.getPositionX() + "");
		element.setAttribute(XML_NOTE_TEXT_BOX_ATTRIBUTE_Y, model.getPositionY() + "");
		element.setAttribute(XML_NOTE_TEXT_BOX_ATTRIBUTE_WIDTH, model.getWidth() + "");
		element.setAttribute(XML_NOTE_TEXT_BOX_ATTRIBUTE_HEIGHT, model.getHeight() + "");
		element.setTextContent(model.getContent());

		return element;
	}

	@Override
	public String serialize(NoteModel model) {
		Document doc = docBuilder.newDocument();

		doc.appendChild(serialize(model, doc));

		String ret = serializeDom(doc);

		return ret;
	}

	private Element serialize(NoteModel model, Document doc) {
		Element element = doc.createElement(XML_NOTE_ROOT_ELEMENT);

		element.setAttribute(XML_NOTE_ATTRIBUTE_NAME, model.getName());
		model.getTextBoxes().stream().map(e -> serialize(e, doc)).forEach(e -> element.appendChild(e));

		return element;
	}

	@Override
	public String serialize(TopicModel model) {
		Document doc = docBuilder.newDocument();

		doc.appendChild(serialize(model, doc));
		
		String ret = serializeDom(doc);
		
		return ret;
	}

	private Element serialize(TopicModel model, Document doc) {
		Element element = doc.createElement(XML_TOPIC_ROOT_ELEMENT);

		element.setAttribute(XML_TOPIC_ATTRIBUTE_NAME, model.getName());
		model.getNotes().stream().map(e -> serialize(e, doc)).forEach(e -> element.appendChild(e));

		return element;
	}

	private String serializeDom(Document doc) {
		String ret = "";
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(baos);

			transformer.transform(source, result);

			ret = baos.toString();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		return ret;
	}

}
