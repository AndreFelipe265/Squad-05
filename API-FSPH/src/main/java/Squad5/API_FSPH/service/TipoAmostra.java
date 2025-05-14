package Squad5.API_FSPH.service;

// Enum com regras de envio por tipo
public enum TipoAmostra {
    MOSQUITO("48h"),
    BARBEIRO("5 dias"),
    ESCORPIAO("sem prazo, não pode estar esmagado"),
    CARRAPATO("sem prazo"),
    LARVAS("tempo indefinido"),
    CARAMUJO("mesmo dia (7h às 12h)"),
    LAMINAS_PCE("semanalmente");

    private final String regraEnvio;

    TipoAmostra(String regraEnvio) {
        this.regraEnvio = regraEnvio;
    }

    public String getRegraEnvio() {
        return regraEnvio;
    }

    public static boolean isValido(String tipo) {
        try {
            TipoAmostra.valueOf(tipo.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
