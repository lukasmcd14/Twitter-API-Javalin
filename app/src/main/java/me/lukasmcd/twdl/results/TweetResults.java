package me.lukasmcd.twdl.results;

import me.lukasmcd.twdl.results.base.PlatformResults;
import me.lukasmcd.twdl.results.base.Type;
import twitter4j.*;

import java.util.*;
import java.util.stream.Collectors;

import static me.lukasmcd.twdl.URLExpressions.mp4Expression;
import static me.lukasmcd.twdl.URLExpressions.tweetExpression;

public class TweetResults implements PlatformResults {

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

	public TweetResults parseURL(String tweet) {
		String statusID = tweetExpression.getText(tweet, "id");
		Twitter twitter = TwitterFactory.getSingleton();

		try {
			Status status = twitter.showStatus(Long.parseLong(statusID));
			MediaEntity media = status.getMediaEntities()[0];
			List<Object> sizes = new ArrayList<>();
			Type type = Type.UNKNOWN;

			// Check what type of media the tweet contains
			if (media.getType().equals("animated_gif")) {
				type = Type.GIF;
			} else if (media.getType().equals("video")) {
				type = Type.MP4;
			}

			// Filter out all the mp4 and sort them by bitrate low to high
			List<MediaEntity.Variant> videos = Arrays.stream(media.getVideoVariants())
					.filter(variant -> variant.getContentType().equals("video/mp4"))
					.sorted(Comparator.comparingInt(MediaEntity.Variant::getBitrate))
					.collect(Collectors.toList());

			Type finalType = type;
			videos.forEach(variant -> {
				HashMap<String, Object> element = new HashMap<>();

				element.put("url", variant.getUrl());

				if (finalType == Type.MP4) {
					element.put("bitrate", variant.getBitrate());
					element.put("resolution", mp4Expression.getText(variant.getUrl(), "resolution"));
				} else {
					element.put("resolution", "GIF");
				}

				sizes.add(element);
			});

			this.sizes = sizes;
			this.type = finalType;
			fetched = true;
		} catch (TwitterException ignored) {
			this.sizes = null;
			this.type = null;
			fetched = false;
		}

		return this;
	}
}
