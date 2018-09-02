package me.pagar.util;

import com.google.common.base.Strings;
import com.google.gson.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.lang.reflect.Type;

public class DateTimeAdapter implements JsonDeserializer<DateTime>, JsonSerializer<DateTime> {

    private final DateTimeFormatter formatter;

    public DateTimeAdapter() {
        this.formatter = ISODateTimeFormat
                .dateOptionalTimeParser()
                .withZoneUTC();
    }

    @Override
    public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final String dateTime = json.getAsString();
        return Strings.isNullOrEmpty(dateTime) ? null : formatter.parseDateTime(dateTime);
    }

    @Override
    public JsonElement serialize(DateTime src, Type typeOfSrc, JsonSerializationContext context) {
        return src == null ? null : new JsonPrimitive(src.toString());
    }

}