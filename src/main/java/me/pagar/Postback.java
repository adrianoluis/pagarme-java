package me.pagar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;

import java.util.Collection;

public class Postback extends PagarMeModel<String> {

	@Expose(serialize = false)
	private Integer retries;

	@Expose(serialize = false)
	private String headers;

	@Expose(serialize = false)
	private String model;

	@Expose(serialize = false)
	private String modelId;

	@Expose(serialize = false)
	private String nextRetry; // ???

	@Expose(serialize = false)
	private String payload;

	@Expose(serialize = false)
	private String requestUrl;

	@Expose(serialize = false)
	private String signature;

	@Expose(serialize = false)
	@SerializedName("date_updated")
	private DateTime updatedAt;

	@Expose(serialize = false)
	private Collection<PostbackDelivery> deliveries;

	@Expose(serialize = false)
	private Transaction.Status status;

	public Integer getRetries() {
		return retries;
	}

	public String getHeaders() {
		return headers;
	}

	public String getModel() {
		return model;
	}

	public String getModelId() {
		return modelId;
	}

	public String getNextRetry() {
		return nextRetry;
	}

	public String getPayload() {
		return payload;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public String getSignature() {
		return signature;
	}

	public DateTime getUpdatedAt() {
		return updatedAt;
	}

	public Transaction.Status getStatus() {
		return status;
	}

	public Collection<PostbackDelivery> getDeliveries() {
		return deliveries;
	}

	@Override
	public void setId(String id) {
		throw new UnsupportedOperationException("Not allowed.");
	}

	@Override
	public void setClassName(String className) {
		throw new UnsupportedOperationException("Not allowed.");
	}

	public static class PostbackDelivery {

		@Expose(serialize = false)
		@SerializedName("response_time")
		private Integer responseTime;

		@Expose(serialize = false)
		@SerializedName("response_body")
		private String responseBody;

		@Expose(serialize = false)
		@SerializedName("response_headers")
		private String responseHeaders;

		@Expose(serialize = false)
		@SerializedName("status_code")
		private String statusCode;

		@Expose(serialize = false)
		@SerializedName("status_reason")
		private String statusReason;

		@Expose(serialize = false)
		@SerializedName("date_updated")
		private DateTime updatedAt;

		@Expose(serialize = false)
		private Transaction.Status status;

		public Integer getResponseTime() {
			return responseTime;
		}

		public String getResponseBody() {
			return responseBody;
		}

		public String getResponseHeaders() {
			return responseHeaders;
		}

		public String getStatusCode() {
			return statusCode;
		}

		public String getStatusReason() {
			return statusReason;
		}

		public DateTime getUpdatedAt() {
			return updatedAt;
		}

		public Transaction.Status getStatus() {
			return status;
		}

	}

}
