package me.lukasmcd.twdl.results;

import junit.framework.TestCase;
import org.hamcrest.MatcherAssert;

import static org.hamcrest.core.StringContains.containsString;

public class RedditResultsTest extends TestCase {

	private RedditResults reddit;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		reddit = new RedditResults();
	}

	public void testParseURL() {
	}

	public void testGetContent() {
		String content = reddit.getContent("https://projects.lukasmcd.me");
		MatcherAssert.assertThat("Content contains 'Index of /'",
				content,
				containsString("Index of /"));
	}
}