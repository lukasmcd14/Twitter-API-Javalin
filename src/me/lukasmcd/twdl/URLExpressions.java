package me.lukasmcd.twdl;

import ru.lanwen.verbalregex.VerbalExpression;

public class URLExpressions {

	/**
	 * Tweet URL Expression
	 */
	public static final VerbalExpression tweetExpression = VerbalExpression.regex()
			.then("http").maybe("s")
			.then("://")
			.maybe("www.")
			.then("twitter.com/")
			.anything()
			.then("/status/")
			.capture("id")
			.digit().oneOrMore()
			.endCapture()
			.anything()
			.build();

	/**
	 * Tweet Media Resolution Expression
	 */
	public static final VerbalExpression resolutionExpression = VerbalExpression.regex()
			.capture("width")
			.digit().oneOrMore()
			.endCapture()
			.then("x")
			.capture("height")
			.digit().oneOrMore()
			.endCapture()
			.build();

	/**
	 * Twitter MP4 URL Expression
	 */
	public static final VerbalExpression mp4Expression = VerbalExpression.regex()
//			.then("https://")
//			.then("video.twimg.com/ext_tw_video/")
//			.digit().oneOrMore()
//			.then("/pu/vid/")
			.anything()
			.then("/")
			.capture("resolution")
			.digit().oneOrMore()
			.then("x")
			.digit().oneOrMore()
			.endCapture()
			.then("/")
			.anything()
			.build();

}
