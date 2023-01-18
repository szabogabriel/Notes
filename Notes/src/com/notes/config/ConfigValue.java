package com.notes.config;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ConfigValue {
	
	private final Supplier<String> provider;
	private final Consumer<String> consumer;
	
	public ConfigValue(Supplier<String> provider, Consumer<String> consumer) {
		this.provider = provider;
		this.consumer = consumer;
	}
	
	public String getStringValue() {
		return provider.get();
	}
	
	public int getIntValue() {
		return Integer.parseInt(getStringValue());
	}
	
	public List<String> getListValue() {
		return Arrays.asList(getStringValue().split(";"));
	}
	
	public void save(String value) {
		consumer.accept(value);
	}
	
	public void save(Integer value) {
		save(value.toString());
	}
	
	public void save(List<String> value) {
		save(value.stream().reduce((a, b) -> a + ";" + b).orElse(""));
	}

}
