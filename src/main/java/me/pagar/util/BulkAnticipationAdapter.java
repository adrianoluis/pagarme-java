package me.pagar.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import me.pagar.BulkAnticipation;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Adapter necessário para tratar o campo payment_date que hora é long, hora é datetime.
 */
public class BulkAnticipationAdapter implements JsonSerializer<BulkAnticipation> {

	@Override
	public JsonElement serialize(BulkAnticipation src, Type type, JsonSerializationContext jsonSerializationContext) {

		if (src == null) {
			return null;
		}

		final Gson gson = new GsonBuilder()
				.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
				.create();
		final String json = gson.toJson(src);

		final Map<String, Object> map = gson.fromJson(json, new TypeToken<HashMap<String, Object>>() {
		}.getType());

		if (null != src.getPaymentDate()) {
			map.put("payment_date", src.getPaymentDate().getMillis());
		}

		return JsonUtils.getInterpreter().toJsonTree(map);
	}

}
