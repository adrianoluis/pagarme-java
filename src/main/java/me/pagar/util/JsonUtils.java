package me.pagar.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import me.pagar.BulkAnticipation;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class JsonUtils {

    private static final Gson GSON_DATA_PROVIDER;

    static {
        GSON_DATA_PROVIDER = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(DateTime.class, new DateTimeAdapter())
                .registerTypeAdapter(BulkAnticipation.class, new BulkAnticipationAdapter())
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setLongSerializationPolicy(LongSerializationPolicy.STRING)
                .create();
    }

    public static final Gson getInterpreter() {
        return GSON_DATA_PROVIDER;
    }

    public static <T> T getAsObject(JsonObject json, Class<T> clazz) {
        return GSON_DATA_PROVIDER.fromJson(json, clazz);
    }

    public static <T> Collection<T> getAsList(JsonArray json, Type listType) {
        return GSON_DATA_PROVIDER.fromJson(json, listType);
    }

    public static String getAsJson(Object object) {
        return GSON_DATA_PROVIDER.toJson(object);
    }

    public static Map<String, Object> objectToMap(Object object) {
        final String json = GSON_DATA_PROVIDER.toJson(object);

        return GSON_DATA_PROVIDER.fromJson(json, new TypeToken<HashMap<String, Object>>() {
        }.getType());
    }

}
