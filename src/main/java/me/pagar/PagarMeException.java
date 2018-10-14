package me.pagar;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.pagar.util.JsonUtils;

import java.util.ArrayList;
import java.util.Collection;

public class PagarMeException extends Exception {

	private int returnCode;

	private String url;

	private String method;

	private String parameterMap;

	private String type;

	Collection<PagarMeError> errors = new ArrayList<PagarMeError>();

	public static PagarMeException buildWithError(final Exception error) {
		return new PagarMeException(error.getMessage(), null);
	}

	@SuppressWarnings("unchecked")
	public static PagarMeException buildWithError(final PagarMeResponse response) {

		if (null == response)
			return null;

		final JsonObject responseError = JsonUtils.getInterpreter().fromJson(response.getBody(), JsonObject.class);

		final JsonArray errors = responseError.getAsJsonArray("errors");

		final StringBuilder joinedMessages = new StringBuilder();

		for (int i = 0; i < errors.size(); i++) {
			final JsonObject error = errors.get(i).getAsJsonObject();
			joinedMessages
					.append(error.get("message").getAsString())
					.append("\n");
		}

		final PagarMeException exception = new PagarMeException(joinedMessages.toString(), responseError);
		exception.returnCode = response.getCode();

		return exception;
	}

	public PagarMeException(int returnCode, String url, String method, String message) {
		super(message);
		this.returnCode = returnCode;
		this.url = url;
		this.method = method;
	}

	public PagarMeException(final String message) {
		this(message, null);
	}

	@SuppressWarnings("unchecked")
	public PagarMeException(final String message, final JsonObject responseError) {
		super(message);

		if (null == responseError || !responseError.has("errors"))
			return;

		this.url = responseError.get("url").getAsString();
		this.method = responseError.get("method").getAsString();

		final JsonArray errors = responseError.get("errors").getAsJsonArray();

		for (int i = 0; i < errors.size(); i++) {
			final JsonObject error = errors.get(i).getAsJsonObject();
			this.errors.add(new PagarMeError(error));
		}
	}

	public Collection<PagarMeError> getErrors() {
		return errors;
	}

	public String getUrl() {
		return url;
	}

	public String getMethod() {
		return method;
	}

	public int getReturnCode() {
		return returnCode;
	}

}
