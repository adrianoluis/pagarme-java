package me.pagar;

import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import me.pagar.util.JsonUtils;
import org.apache.commons.codec.binary.Base64;
import org.joda.time.DateTime;

import javax.crypto.Cipher;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Collection;

public class Card extends PagarMeModel<String> {

	private static final String TEMPLATE_ENCRYPT_QUERY = "card_number=%s&card_holder_name=%s&card_expiration_date=%s&card_cvv=%s\n";

	@Expose(deserialize = false)
	@SerializedName("card_hash")
	private String hash;

	@Expose(serialize = false)
	private Brand brand;

	@Expose
	@SerializedName(value = "card_holder_name", alternate = {"holder_name"})
	private String holderName;

	@Expose(deserialize = false)
	@SerializedName("card_number")
	private String number;

	@Expose(serialize = false)
	private String firstDigits;

	@Expose(serialize = false)
	private String lastDigits;

	@Expose(deserialize = false)
	@SerializedName("card_cvv")
	private String verificationValue;

	@Expose(serialize = false)
	private String fingerprint;

	@Expose(serialize = false)
	private String country;

	@Expose(deserialize = false)
	private Integer customerId;

	@Expose(serialize = false)
	private Boolean valid;

	@Expose
	@SerializedName(value = "card_expiration_date", alternate = {"expiration_date"})
	private String expirationDate;

	@Expose(serialize = false)
	@SerializedName("date_updated")
	private DateTime updatedAt;

	@Expose(serialize = false)
	private Customer customer;

	public Card() {
		super();
	}

	public Card(String id) {
		this();
		setId(id);
	}

	public Card(String number, String holderName, String expirationDate, String verificationValue) {
		this();
		this.number = number;
		this.holderName = holderName;
		this.expirationDate = expirationDate;
		this.verificationValue = verificationValue;
	}

	public Brand getBrand() {
		return brand;
	}

	public String getHolderName() {
		return holderName;
	}

	public String getFirstDigits() {
		return firstDigits;
	}

	public String getLastDigits() {
		return lastDigits;
	}

	public String getVerificationValue() {
		return verificationValue;
	}

	public String getFingerprint() {
		return fingerprint;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public String getCountry() {
		return country;
	}

	public Boolean getValid() {
		return valid;
	}

	public DateTime getUpdatedAt() {
		return updatedAt;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setHash(String hash) {
		this.hash = hash;
		addUnsavedProperty("hash");
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
		addUnsavedProperty("holderName");
	}

	public void setNumber(String number) {
		this.number = number;
		addUnsavedProperty("number");
	}

	public void setVerificationValue(String verificationValue) {
		this.verificationValue = verificationValue;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
		addUnsavedProperty("customerId");
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
		addUnsavedProperty("expirationDate");
	}

	public String encrypt(CardHashKey cardHashKey) {

		if (Strings.isNullOrEmpty(number) || Strings.isNullOrEmpty(holderName) ||
				Strings.isNullOrEmpty(expirationDate) || Strings.isNullOrEmpty(verificationValue)) {
			return hash;
		}

		try {
			final String publicKeyToken = cardHashKey.getPublicKey()
					.replaceAll("\\n", "")
					.replace("-----BEGIN PUBLIC KEY-----", "")
					.replace("-----END PUBLIC KEY-----", "");
			final byte[] publicKeyBytes = Base64.decodeBase64(publicKeyToken);
			final X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
			final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			final PublicKey publicKey = keyFactory.generatePublic(keySpec);

			final Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);

			final String query = URLEncoder.encode(String.format(TEMPLATE_ENCRYPT_QUERY,
					number,
					holderName,
					expirationDate,
					verificationValue), "UTF-8");

			hash = String.format("%d_%s",
					cardHashKey.getId(),
					Base64.encodeBase64String(cipher.doFinal(query.getBytes("UTF-8"))));
		} catch (Exception e) {
			hash = null;
		}

		return hash;
	}

	public Card save() throws PagarMeException {
		final Card saved = super.save(getClass());
		copy(saved);

		return saved;
	}

	/**
	 * @see #list(int, int)
	 */
	public Collection<Card> list() throws PagarMeException {
		return list(100, 0);
	}

	/**
	 * @param totalPerPage Retorna <code>n</code> objetos de transação
	 * @param page         Útil para implementação de uma paginação de resultados
	 * @return Uma {@link Collection} contendo objetos de transações, ordenadas a partir da transação realizada mais
	 * recentemente.
	 * @throws PagarMeException
	 */
	public Collection<Card> list(int totalPerPage, int page) throws PagarMeException {
		return JsonUtils.getAsList(super.paginate(totalPerPage, page), new TypeToken<Collection<Card>>() {
		}.getType());
	}

	public Card refresh() throws PagarMeException {
		final Card other = JsonUtils.getAsObject(refreshModel(), Card.class);
		copy(other);
		flush();
		return other;
	}

	private void copy(Card other) {
		setId(other.getId());
		this.brand = other.brand;
		this.holderName = other.holderName;
		this.firstDigits = other.firstDigits;
		this.lastDigits = other.lastDigits;
		this.fingerprint = other.fingerprint;
		this.country = other.country;
		this.valid = other.valid;
	}

	public enum Brand {

		@SerializedName("amex")
		AMEX,

		@SerializedName("aura")
		AURA,

		@SerializedName("discover")
		DISCOVER,

		@SerializedName("elo")
		ELO,

		@SerializedName("hipercard")
		HIPERCARD,

		@SerializedName("jcb")
		JCB,

		@SerializedName("visa")
		VISA,

		@SerializedName("mastercard")
		MASTERCARD

	}

}
