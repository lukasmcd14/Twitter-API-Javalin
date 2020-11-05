package me.lukasmcd.twdl.results.base;

public enum Type {
	MP4("mp4"),
	GIF("gif"),
	UNKNOWN("unknown");

	private final String value;

	Type(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
