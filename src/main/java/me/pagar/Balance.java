package me.pagar;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import me.pagar.util.JsonUtils;

import javax.ws.rs.HttpMethod;
import java.io.Serializable;

public class Balance extends PagarMeModel {

	@Expose(serialize = false)
	private Summary waitingFunds;

	@Expose(serialize = false)
	private Summary available;

	@Expose(serialize = false)
	private Summary transferred;

	public Balance() {
		super.setClassName(getClass().getSimpleName().toLowerCase());
	}

	public Summary getWaitingFunds() {
		return waitingFunds;
	}

	public Summary getAvailable() {
		return available;
	}

	public Summary getTransferred() {
		return transferred;
	}

	@Override
	public void setId(Serializable id) {
		throw new UnsupportedOperationException("Not allowed.");
	}

	@Override
	public void setClassName(String className) {
		throw new UnsupportedOperationException("Not allowed.");
	}

	public Balance refresh() throws PagarMeException {
		final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET, String.format("/%s", getClassName()));
		final Balance other = JsonUtils.getAsObject((JsonObject) request.execute(), Balance.class);
		copy(other);
		flush();
		return other;
	}

	private void copy(Balance other) {
		this.waitingFunds = other.waitingFunds;
		this.available = other.available;
		this.transferred = other.transferred;
	}

	public static class Summary {

		@Expose(serialize = false)
		private Integer amount;

		public Integer getAmount() {
			return amount;
		}

	}

}
