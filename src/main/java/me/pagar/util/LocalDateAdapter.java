package me.pagar.util;

import com.google.common.base.Strings;
import com.google.gson.*;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.lang.reflect.Type;

public class LocalDateAdapter implements JsonDeserializer<LocalDate>, JsonSerializer<LocalDate> {

	private final DateTimeFormatter formatter;

	public LocalDateAdapter() {
		this.formatter = ISODateTimeFormat
				.dateOptionalTimeParser()
				.withZoneUTC();
	}

	@Override
	public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		final String dateTime = json.getAsString();
		return Strings.isNullOrEmpty(dateTime) ? null : formatter.parseLocalDate(dateTime);
	}

	@Override
	public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
		return src == null ? null : new JsonPrimitive(src.toString());
	}

}
