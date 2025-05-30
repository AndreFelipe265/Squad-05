package Squad5.API_FSPH.Amostra.entity;

import Squad5.API_FSPH.exception.BusinessRuleException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

// Enum para os tipos de amostras válidos
public enum Tipo {
    MOSQUITO,
    BARBEIRO,
    CARAMUJO,
    CARRAPATO,
    ESCORPIAO,
    LARVAS,
    LAMINA_PCE;

    @JsonCreator
    public static Tipo from(String value) {
        try {
            return Tipo.valueOf(value.trim().toUpperCase());
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Tipo de amostra inválido: " + value);
        }
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
