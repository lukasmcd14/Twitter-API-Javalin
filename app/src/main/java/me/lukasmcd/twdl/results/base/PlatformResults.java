package me.lukasmcd.twdl.results.base;

import java.util.List;

public interface PlatformResults {

	Type getType();

	List<Object> getSizes();

	boolean hasFetched();

	PlatformResults parseURL(String url);

}
