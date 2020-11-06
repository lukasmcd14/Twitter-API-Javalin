package me.lukasmcd.twdl.results;

import me.lukasmcd.twdl.results.base.PlatformResults;
import me.lukasmcd.twdl.results.base.Type;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class RedditResults implements PlatformResults {

	private List<Object> sizes;
	private Type         type;
	private boolean      fetched = false;

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public List<Object> getSizes() {
		return sizes;
	}

	@Override
	public boolean hasFetched() {
		return fetched;
	}

	@Override
	public PlatformResults parseURL(String url) {
		if (!url.endsWith("/")) {
			url = url.substring(0, url.lastIndexOf("/"));
		}

		System.out.println(url);

		return this;
	}

	public String getContent(String url) {
		StringBuilder response = new StringBuilder();

		try {
			URL website = new URL(url);
			URLConnection connection = website.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String inputLine;

			while ((inputLine = in.readLine()) != null)
				response.append(inputLine);

			in.close();
		} catch (Exception ignored) {}

		return response.toString();
	}
}
