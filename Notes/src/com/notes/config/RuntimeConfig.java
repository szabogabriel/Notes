package com.notes.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;

import com.notes.actionQueue.ActionQueueFactory;
import com.notes.actionQueue.sizeLimit.SizeLimitActionQueueFactory;
import com.notes.serialization.DeserializationProvider;
import com.notes.serialization.SerializationProvider;
import com.notes.serialization.xml.XmlDeserializationProvider;
import com.notes.serialization.xml.XmlSerializationProvider;

public enum RuntimeConfig {
	/*
	 * impl.class -> canonical name of the class
	 * type.class -> type com.jdi.ServiceClassType
	 */
	JDI_IMPL_ACTION_QUEUE_FACTORY("impl." + ActionQueueFactory.class.getCanonicalName(), SizeLimitActionQueueFactory.class.getCanonicalName()),
	JDI_IMPL_DESERIALIZATION_PROVIDER("impl." + DeserializationProvider.class.getCanonicalName(), XmlDeserializationProvider.class.getCanonicalName()),
	JDI_IMPL_SERIALIZATION_PROVIDER("impl." + SerializationProvider.class.getCanonicalName(), XmlSerializationProvider.class.getCanonicalName()),
	
	DEFAULT_NOTEBOOK_NAME("notebook.default.name", "deault.xnotes"),
	USER_NOTEBOOKS("notebook.user", ""),
	
	UNDO_LIMIT("undo.limit", "1000"),
	;

	private static final File PROPERTIES_FILE = new File("./runtime.properties");
	private static final Properties CONFIG_VALUES = new Properties();
	
	static {
		load();
	}
	
	private final String key;
	private final String defaultValue;
	
	private RuntimeConfig(String key, String defaultValue) {
		this.key = key;
		this.defaultValue = defaultValue;
	}
	
	public String getKey() {
		return key;
	}
	
	public ConfigValue getValue() {
		return new ConfigValue(this::toString, this::save);
	}
	
	private void save(String value) {
		CONFIG_VALUES.put(key, value);
		save();
	}
	
	public String toString() {
		return CONFIG_VALUES.getProperty(key, defaultValue);
	}

	public static Optional<String> get(String key) {
		return Arrays.asList(values())
				.stream()
				.filter(v -> v.key.equals(key))
				.map(v -> v.toString())
				.findAny();
	}
	
	private static void load() {
		if (PROPERTIES_FILE.exists() && PROPERTIES_FILE.isFile()) {
			try (InputStream in = new FileInputStream(PROPERTIES_FILE)) {
				CONFIG_VALUES.load(in);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void save() {
		if (PROPERTIES_FILE.exists() && PROPERTIES_FILE.isFile()) {
			try (OutputStream out = new FileOutputStream(PROPERTIES_FILE)) {
				CONFIG_VALUES.store(out, "");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
