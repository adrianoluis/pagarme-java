package me.pagar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;

/**
 * Objeto contendo os dados de um recebível. O recebível (payable) é gerado automaticamente após uma transação ser paga,
 * para cada parcela de uma transação é gerado um recebível, que também podem ser divididos por recebedor (no caso de um
 * split ter sido feito).
 * <p>
 * <b>Ex:</b> Uma transação paga com 5 parcelas e 3 recebedores nas regras de split irá gerar 15 (5 x 3) recebíveis. Com
 * isso você tem controle sobre a menor divisão possível de um pagamento.
 */
public class Payable extends PagarMeModel<Integer> {

	@Expose(serialize = false)
	private Integer amount;

	@Expose(serialize = false)
	private Integer fee;

	@Expose(serialize = false)
	private Integer installment;

	@Expose(serialize = false)
	private Integer transactionId;

	@Expose(serialize = false)
	private String splitRuleId;

	@Expose(serialize = false)
	@SerializedName("payment_date")
	private DateTime payment;

	@Expose(serialize = false)
	private Status status;

	@Expose(serialize = false)
	private Type type;

	public Integer getAmount() {
		return amount;
	}

	public Integer getFee() {
		return fee;
	}

	public Integer getInstallment() {
		return installment;
	}

	public Integer getTransactionId() {
		return transactionId;
	}

	public String getSplitRuleId() {
		return splitRuleId;
	}

	public DateTime getPayment() {
		return payment;
	}

	public Status getStatus() {
		return status;
	}

	public Type getType() {
		return type;
	}

	@Override
	public void setId(Integer id) {
		throw new UnsupportedOperationException("Not allowed.");
	}

	@Override
	public void setClassName(String className) {
		throw new UnsupportedOperationException("Not allowed.");
	}

	public enum Status {

		@SerializedName("paid")
		PAID,

		@SerializedName("waiting_funds")
		WAITING_FUNDS

	}

	public enum Type {

		@SerializedName("chargeback")
		CHARGEBACK,

		@SerializedName("credit")
		CREDIT,

		@SerializedName("refund")
		REFUND

	}

}
