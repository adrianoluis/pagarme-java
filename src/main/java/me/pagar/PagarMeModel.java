package me.pagar;

import com.google.common.base.CaseFormat;
import com.google.gson.*;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import me.pagar.util.BulkAnticipationAdapter;
import me.pagar.util.DateTimeAdapter;
import me.pagar.util.JsonUtils;
import me.pagar.util.LocalDateAdapter;
import org.atteo.evo.inflector.English;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import javax.ws.rs.HttpMethod;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe base para modelos contendo metodos genéricos de retorno de dados.
 *
 * @param <PK> Tipo da Primary Key
 */
public abstract class PagarMeModel<PK extends Serializable> {

    /**
     * Número identificador da transação
     */
    @Expose(serialize = false)
    @SerializedName("id")
    private PK id;

    /**
     * Data de criação da transação no formato ISODate
     */
    @Expose(serialize = false)
    @SerializedName("date_created")
    private DateTime createdAt;

    /**
     * Nome da classe no plural, lower case e undesrcored.
     */
    private transient String className;

    /**
     * {@link Collection} de atributos que tiveram seus valores alterados.
     */
    private transient Collection<String> dirtyProperties;

    /**
     * Valida se o atributo {@link #id} foi preenchido.
     */
    protected void validateId() {

        if (getId() == null) {
            throw new IllegalArgumentException("The Object ID must be set in order to use this method.");
        }

    }

    public PagarMeModel() {
        className = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, English.plural(getClass().getSimpleName()));
        dirtyProperties = new ArrayList<String>();
    }

    /**
     * {@link #id}
     */
    public PK getId() {
        return id;
    }

    /**
     * {@link #createdAt}
     */
    public DateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * {@link #className}
     */
    public String getClassName() {
        return className;
    }

    public void setId(final PK id) {
        this.id = id;
    }

    public void setClassName(final String className) {
        this.className = className;
    }

    /**
     * Atualiza o estado do modelo
     *
     * @return A representação atualizada do estado do modelo
     * @throws PagarMeException
     */
    protected JsonObject refreshModel() throws PagarMeException {
        return get(this.id);
    }

    /**
     * Obtem um modelo pelo id.
     *
     * @param id Identificação do Modelo
     * @return A representação do estado do modelo
     * @throws PagarMeException
     */
    protected JsonObject get(final PK id) throws PagarMeException {
        validateId();
        return new PagarMeRequest(HttpMethod.GET, String.format("/%s/%s", className, id)).execute();
    }

    /**
     * @param totalPerPage
     * @return
     * @throws PagarMeException
     */
    protected JsonArray paginate(final Integer totalPerPage) throws PagarMeException {
        return paginate(totalPerPage, 1);
    }

    /**
     * @param totalPerPage
     * @param page
     * @return
     * @throws PagarMeException
     */
    protected JsonArray paginate(final Integer totalPerPage, Integer page) throws PagarMeException {
        final Map<String, Object> parameters = new HashMap<String, Object>();

        if (null != totalPerPage && 0 != totalPerPage) {
            parameters.put("count", totalPerPage);
        }

        if (null == page || 0 >= page) {
            page = 1;
        }

        parameters.put("page", page);

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET, String.format("/%s", className));
        request.getParameters().putAll(parameters);

        return request.execute();
    }

    /**
     * Persiste o estado do modelo no servidor.
     *
     * @param clazz {@link Class} do Modelo
     * @param <T>   Classe do Modelo
     * @return O Modelo com os dados persistidos no servidor.
     * @throws PagarMeException
     */
    protected <T extends PagarMeModel> T save(final Class<T> clazz) throws PagarMeException {

        if (!beforeSave()) {
            return null;
        }

        final PagarMeRequest request = null == id ?
                new PagarMeRequest(HttpMethod.POST, String.format("/%s", className)) :
                new PagarMeRequest(HttpMethod.PUT, String.format("/%s/%s/", className, id));
        request.setParameters(JsonUtils.objectToMap(this));

        final JsonElement element = request.execute();
        flush();

        return JsonUtils.getAsObject((JsonObject) element, clazz);
    }

    /**
     * Atualiza lista de atributos alterados.
     *
     * @param name Nome do atributo
     */
    protected void addUnsavedProperty(final String name) {
        for (String s : dirtyProperties) {
            if (s.startsWith(name.concat("."))) {
                dirtyProperties.remove(s);
            }
        }
        dirtyProperties.add(name);
    }

    /**
     * Limpa lista de atributos com dados alterados.
     */
    protected void flush() {
        dirtyProperties.clear();
    }

    /**
     * Callback para validação de modelo no método {@link #save(Class)}.
     *
     * @return <code>true</code> para persistir os dados; <code>false</code> para não persistir
     */
    protected boolean beforeSave() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final PagarMeModel<?> that = (PagarMeModel<?>) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        try {
            return new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .registerTypeAdapter(DateTime.class, new DateTimeAdapter())
                    .registerTypeAdapter(BulkAnticipation.class, new BulkAnticipationAdapter())
                    .setLongSerializationPolicy(LongSerializationPolicy.STRING)
                    .create()
                    .toJson(this);
        } catch (UnsupportedOperationException e) {
            return getClass().getSimpleName().concat(String.format("=(%s)", this.id));
        }
    }

}
