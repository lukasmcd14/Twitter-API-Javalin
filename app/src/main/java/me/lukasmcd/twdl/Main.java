package me.lukasmcd.twdl;

import io.javalin.Javalin;
import io.javalin.http.Context;
import me.lukasmcd.twdl.results.TweetResults;

import java.util.HashMap;

import static me.lukasmcd.twdl.URLExpressions.tweetExpression;

public class Main {

	public static final String VERSION = "1.0.1-Beta";

	public static void main(String[] args) {
		int port = args.length > 0 ? Integer.parseInt(args[0]) : 7000;
		Javalin app = Javalin.create(javalinConfig -> {
			// javalinConfig.enableDevLogging();
		}).start(port);
		app.post("/", Main::handlePostRequest);
		app.get("/version", Main::version);
	}

	public static void version(Context ctx) {
		HashMap<String, Object> result = new HashMap<>();

		result.put("server", VERSION);
		// TODO Read this from file and provide link to shortcut
		result.put("shortcut", "1.0.0");

		ctx.json(result);
	}

	public static void handlePostRequest(Context ctx) {
		HashMap<String, Object> result = new HashMap<>();
		String decodedComponent = ctx.formParam("url");

		if (tweetExpression.test(decodedComponent)) {
			TweetResults tweet = new TweetResults().parseURL(decodedComponent);

			if (tweet.hasFetched()) {
				result.put("sizes", tweet.getSizes());
				result.put("type", tweet.getType().getValue());
			} else {
				result.put("error", "Could not fetch tweet.");
			}
		} else {
			result.put("error", "Invalid URL.");
		}

		ctx.json(result);
	}

}