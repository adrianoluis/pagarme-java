package me.pagar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import me.pagar.util.JsonUtils;

import java.util.Collection;

public class BankAccount extends PagarMeModel<Integer> {

	@Expose(serialize = false)
	private Boolean chargeTransferFees;

	@Expose
	private String bankCode;

	@Expose
	private String agencia;

	@Expose
	private String agenciaDv;

	@Expose
	private String conta;

	@Expose
	private String contaDv;

	@Expose
	private String documentNumber;

	@Expose
	private String legalName;

	@Expose
	private DocumentType documentType;

	public Boolean isChargeTransferFees() {
		return chargeTransferFees;
	}

	public String getBankCode() {
		return bankCode;
	}

	public String getAgencia() {
		return agencia;
	}

	public String getAgenciaDv() {
		return agenciaDv;
	}

	public String getConta() {
		return conta;
	}

	public String getContaDv() {
		return contaDv;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public String getLegalName() {
		return legalName;
	}

	public DocumentType getDocumentType() {
		return documentType;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
		addUnsavedProperty("bankCode");
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
		addUnsavedProperty("agencia");
	}

	public void setAgenciaDv(String agenciaDv) {
		this.agenciaDv = agenciaDv;
		addUnsavedProperty("agenciaDv");
	}

	public void setConta(String conta) {
		this.conta = conta;
		addUnsavedProperty("conta");
	}

	public void setContaDv(String contaDv) {
		this.contaDv = contaDv;
		addUnsavedProperty("contaDv");
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
		addUnsavedProperty("documentNumber");
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
		addUnsavedProperty("legalName");
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
		addUnsavedProperty("documentType");
	}

	public Collection<BankAccount> findAll() throws PagarMeException {
		return find(100, 0);
	}

	public Collection<BankAccount> find(int totalPerPage, int page) throws PagarMeException {
		return JsonUtils.getAsList(super.paginate(totalPerPage, page), new TypeToken<Collection<BankAccount>>() {
		}.getType());
	}

	public BankAccount save() throws PagarMeException {
		final BankAccount saved = super.save(BankAccount.class);
		copy(saved);

		return saved;
	}

	public BankAccount refresh() throws PagarMeException {
		final BankAccount other = JsonUtils.getAsObject(refreshModel(), BankAccount.class);
		copy(other);
		flush();
		return other;
	}

	public void copy(BankAccount other) {
		setId(other.getId());
		this.chargeTransferFees = other.chargeTransferFees;
		this.bankCode = other.bankCode;
		this.agencia = other.agencia;
		this.agenciaDv = other.agenciaDv;
		this.conta = other.conta;
		this.contaDv = other.contaDv;
		this.documentNumber = other.documentNumber;
		this.legalName = other.legalName;
		this.documentType = other.documentType;
	}

	public enum DocumentType {
		@SerializedName("cpf")
		CPF,

		@SerializedName("cnpj")
		CNPJ
	}

}
