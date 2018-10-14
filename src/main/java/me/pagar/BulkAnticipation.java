package me.pagar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;

public class BulkAnticipation extends PagarMeModel<String> {

	@Expose(serialize = false)
	private Integer amount;

	@Expose(serialize = false)
	private Integer anticipationFee;

	/**
	 * Data de atualização da transação no formato ISODate
	 */
	@Expose(serialize = false)
	@SerializedName("date_updated")
	private DateTime updatedAt;

	@Expose(serialize = false)
	private Integer fee;

	@Expose
	private DateTime paymentDate;

	@Expose(serialize = false)
	private Status status;

	@Expose
	private Timeframe timeframe;

	@Expose(serialize = false)
	private String type;

	@Expose(deserialize = false)
	private Integer requestedAmount;

	@Expose(deserialize = false)
	private Boolean building;

	public BulkAnticipation() {
		super();
	}

	public BulkAnticipation(final DateTime paymentDate, final Timeframe timeframe, final Integer requestedAmount,
							final Boolean building) {
		super();
		this.paymentDate = paymentDate;
		this.timeframe = timeframe;
		this.requestedAmount = requestedAmount;
		this.building = building;
	}

	public Integer getAmount() {
		return amount;
	}

	public Integer getAnticipationFee() {
		return anticipationFee;
	}

	public DateTime getUpdatedAt() {
		return updatedAt;
	}

	public Integer getFee() {
		return fee;
	}

	public DateTime getPaymentDate() {
		return paymentDate;
	}

	public Status getStatus() {
		return status;
	}

	public Timeframe getTimeframe() {
		return timeframe;
	}

	public String getType() {
		return type;
	}

	public void setPaymentDate(final DateTime paymentDate) {
		this.paymentDate = paymentDate;
	}

	public void setTimeframe(final Timeframe timeframe) {
		this.timeframe = timeframe;
	}

	public void setRequestedAmount(final Integer requestedAmount) {
		this.requestedAmount = requestedAmount;
	}

	public void setBuilding(final Boolean building) {
		this.building = building;
	}

	@Override
	public void setId(final String id) {
		throw new UnsupportedOperationException("Not allowed.");
	}

	@Override
	public void setClassName(final String className) {
		throw new UnsupportedOperationException("Not allowed.");
	}

	public static class Limits {

		@Expose(serialize = false)
		private Detail maximum;

		@Expose(serialize = false)
		private Detail minimum;

		public Detail getMaximum() {
			return maximum;
		}

		public Detail getMinimum() {
			return minimum;
		}

		public static class Detail {

			@Expose(serialize = false)
			private Integer amount;

			@Expose(serialize = false)
			@SerializedName("anticipation_fee")
			private Integer anticipationFee;

			@Expose(serialize = false)
			private Integer fee;

			public Integer getAmount() {
				return amount;
			}

			public Integer getAnticipationFee() {
				return anticipationFee;
			}

			public Integer getFee() {
				return fee;
			}

		}

	}

	public enum Status {

		@SerializedName("approved")
		APPROVED,

		@SerializedName("building")
		BUILDING,

		@SerializedName("canceled")
		CANCELED,

		@SerializedName("pending")
		PENDING,

		@SerializedName("refused")
		REFUSED

	}

	public enum Timeframe {

		@SerializedName("end")
		END,

		@SerializedName("start")
		START

	}

}
