package me.pagar;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import me.pagar.util.JsonUtils;
import org.joda.time.DateTime;

import javax.ws.rs.HttpMethod;
import java.util.Collection;

public class Recipient extends PagarMeModel<String> {

    @Expose(serialize = false)
    private Boolean automaticAnticipationEnabled;

    @Expose
    private Boolean transferEnabled;

    @Expose(serialize = false)
    private Integer anticipatableVolumePercentage;

    @Expose
    private Integer transferDay;

    @Expose(deserialize = false)
    private Integer bankAccountId;

    @Expose
    private BankAccount bankAccount;

    @Expose
    private TransferInterval transferInterval;

    @Expose(serialize = false)
    private DateTime lastTransfer;

    @Expose(serialize = false)
    @SerializedName("date_updated")
    private DateTime updatedAt;

    public Boolean isAutomaticAnticipationEnabled() {
        return automaticAnticipationEnabled;
    }

    public Boolean isTransferEnabled() {
        return transferEnabled;
    }

    public Integer getAnticipatableVolumePercentage() {
        return anticipatableVolumePercentage;
    }

    public Integer getTransferDay() {
        return transferDay;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public TransferInterval getTransferInterval() {
        return transferInterval;
    }

    public DateTime getLastTransfer() {
        return lastTransfer;
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setTransferEnabled(Boolean transferEnabled) {
        this.transferEnabled = transferEnabled;
        addUnsavedProperty("transferEnabled");
    }

    public void setTransferDay(Integer transferDay) {
        this.transferDay = transferDay;
        addUnsavedProperty("transferDay");
    }

    public void setBankAccountId(Integer bankAccountId) {
        this.bankAccountId = bankAccountId;
        addUnsavedProperty("bankAccountId");
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
        addUnsavedProperty("bankAccount");
    }

    public void setTransferInterval(TransferInterval transferInterval) {
        this.transferInterval = transferInterval;
        addUnsavedProperty("transferInterval");
    }

    public Recipient save() throws PagarMeException {
        final Recipient saved = super.save(getClass());
        copy(saved);

        return saved;
    }

    public Collection<Recipient> findAll() throws PagarMeException {
        return find(100, 0);
    }

    public Collection<Recipient> find(int totalPerPage, int page) throws PagarMeException {
        return JsonUtils.getAsList(super.paginate(totalPerPage, page), new TypeToken<Collection<Recipient>>() {
        }.getType());
    }

    public Balance balance() throws PagarMeException {
        final Balance balance = new Balance();

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET,
                String.format("/%s/%s/%s", getClassName(), getId(), balance.getClassName()));

        return JsonUtils.getAsObject((JsonObject) request.execute(), Balance.class);
    }

    public Recipient refresh() throws PagarMeException {
        final Recipient other = JsonUtils.getAsObject(refreshModel(), Recipient.class);
        copy(other);
        flush();
        return other;
    }

    /**
     * Retornando todas as antecipações
     *
     * @return {@link Collection} contendo objetos de antecipações.
     * @throws PagarMeException
     */
    public Collection<BulkAnticipation> bulkAnticipations() throws PagarMeException {
        validateId();

        final BulkAnticipation bulkAnticipation = new BulkAnticipation();

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET, String.format("/%s/%s/%s", getClassName(),
                getId(), bulkAnticipation.getClassName()));

        return JsonUtils.getAsList((JsonArray) request.execute(), new TypeToken<Collection<BulkAnticipation>>() {
        }.getType());
    }

    /**
     * Criando uma antecipação
     *
     * @param bulkAnticipation
     * @return
     * @throws PagarMeException
     */
    public BulkAnticipation bulkAnticipations(final BulkAnticipation bulkAnticipation) throws PagarMeException {
        validateId();

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.POST,
                String.format("/%s/%s/%s", getClassName(), getId(), bulkAnticipation.getClassName()));
        request.setParameters(JsonUtils.objectToMap(bulkAnticipation));

        return JsonUtils.getAsObject((JsonObject) request.execute(), BulkAnticipation.class);
    }

    /**
     * Confirma a antecipação criada, assim seu status passará para pending, ou seja, está criada com sucesso e
     * aguardando aprovação do Pagar.me.
     *
     * @param bulkAnticipationId
     * @return
     * @throws PagarMeException
     */
    public BulkAnticipation bulkAnticipationsConfirm(final String bulkAnticipationId) throws PagarMeException {
        validateId();

        final BulkAnticipation bulkAnticipation = new BulkAnticipation();

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.POST,
                String.format("/%s/%s/%s/%s/confirm", getClassName(), getId(), bulkAnticipation.getClassName(),
                        bulkAnticipationId));

        return JsonUtils.getAsObject((JsonObject) request.execute(), BulkAnticipation.class);
    }

    /**
     * Cancela uma antecipação com status pending. Enquanto a antecipação foi criada e o Pagar.me ainda não a confirmou,
     * você pode cancelar a antecipação a qualquer momento.
     *
     * @param bulkAnticipationId
     * @return
     * @throws PagarMeException
     */
    public BulkAnticipation bulkAnticipationsCancel(final String bulkAnticipationId) throws PagarMeException {
        validateId();

        final BulkAnticipation bulkAnticipation = new BulkAnticipation();

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.POST,
                String.format("/%s/%s/%s/%s/cancel", getClassName(), getId(),
                        bulkAnticipation.getClassName(), bulkAnticipationId));

        return JsonUtils.getAsObject((JsonObject) request.execute(), BulkAnticipation.class);
    }

    /**
     * Enquanto você está construindo uma antecipação (status building), você pode cancelar o processo deletando a
     * criação daquela antecipação. Lembrando que caso você não a destrua no status building, após 5 minutos ela é
     * automaticamente destruída.
     *
     * @param bulkAnticipationId
     * @return
     * @throws PagarMeException
     */
    public BulkAnticipation bulkAnticipationsDelete(final String bulkAnticipationId) throws PagarMeException {
        validateId();

        final BulkAnticipation bulkAnticipation = new BulkAnticipation();

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.DELETE,
                String.format("/%s/%s/%s/%s", getClassName(), getId(), bulkAnticipation.getClassName(),
                        bulkAnticipationId));

        return JsonUtils.getAsObject((JsonObject) request.execute(), BulkAnticipation.class);
    }

    public BulkAnticipation.Limits bulkAnticipationsLimits() throws PagarMeException {
        final BulkAnticipation bulkAnticipation = new BulkAnticipation();

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET,
                String.format("/%s/%s/%s/%s", getClassName(), getId(), bulkAnticipation.getClassName(),
                        BulkAnticipation.Limits.class.getSimpleName().toLowerCase()));

        return JsonUtils.getAsObject((JsonObject) request.execute(), BulkAnticipation.Limits.class);
    }

    private void copy(Recipient other) {
        setId(other.getId());
        this.automaticAnticipationEnabled = other.automaticAnticipationEnabled;
        this.transferEnabled = other.transferEnabled;
        this.transferDay = other.transferDay;
        this.anticipatableVolumePercentage = other.anticipatableVolumePercentage;
        this.lastTransfer = other.lastTransfer;
        this.transferInterval = other.transferInterval;
        this.updatedAt = other.updatedAt;
        this.bankAccount = other.bankAccount;
    }

    public enum TransferInterval {
        @SerializedName("daily")
        DAILY,

        @SerializedName("weekly")
        WEEKLY,

        @SerializedName("monthly")
        MONTHLY
    }

}
