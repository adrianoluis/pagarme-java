package me.pagar.util;

import com.google.common.base.Strings;
import me.pagar.PagarMeModel;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

public class MapUtils {

    private static final String UTF_8 = "UTF-8";

    public static String mapToQuery(final Map<String, Object> map) {
        final StringBuilder stringBuilder = new StringBuilder();

        for (String key : map.keySet()) {

            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }

            final Object value = map.get(key);

            try {
                stringBuilder.append(URLEncoder.encode(Strings.nullToEmpty(key), UTF_8));
                stringBuilder.append("=");
                stringBuilder.append(null == value ? "" : URLEncoder.encode(Strings.nullToEmpty(String.valueOf(value)), UTF_8));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("This method requires UTF-8 encoding support", e);
            }

        }

        return stringBuilder.toString();
    }

    public static Map<String, String> queryToMap(final String query) {
        final Map<String, String> map = new HashMap<String, String>();
        final String[] keyValuePairs = query.split("&");

        for (String KeyValuePair : keyValuePairs) {
            final String[] keyValue = KeyValuePair.split("=");

            if (keyValue.length > 0) {
                try {
                    map.put(URLDecoder.decode(keyValue[0], UTF_8), keyValue.length == 2 ? keyValue[1] : null);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException("This method requires UTF-8 encoding support", e);
                }
            }

        }

        return map;
    }

    public static Map<String, Object> objectToMap(final Object obj) {
        return objectToMap(obj, new ArrayList<String>());
    }

    public static Map<String, Object> objectToMap(final Object obj, final List<String> blacklist) {
        final Map<String, Object> result = new LinkedHashMap<String, Object>();

        // XXX always ignore class and className attribute
        blacklist.addAll(Arrays.asList("class", "className"));

        try {
            final BeanInfo info = Introspector.getBeanInfo(obj.getClass());

            for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
                final Method reader = pd.getReadMethod();

                if (reader != null && (blacklist.size() == 0 || !blacklist.contains(pd.getName()))) {
                    final Object value = reader.invoke(obj);

                    if (null == value ||
                            value instanceof Number ||
                            value instanceof CharSequence ||
                            value instanceof Boolean) {
                        result.put(pd.getName(), value);
                    } else {
                        result.put(pd.getName(), objectToMap(value));
                    }
                }
            }

        } catch (Exception ignored) {
        }
        return result;
    }
}
