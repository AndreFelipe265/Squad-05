# Squad-05 - Guia para rodar e testar a API-FSPH

Este guia explica como abrir, executar e testar a aplicação backend API-FSPH usando IntelliJ, Postman ou Swagger UI.

---

## 1. Abrir o projeto no IntelliJ IDEA

- Abra o IntelliJ IDEA.
- Clique em **Open** e selecione a pasta do projeto **API-FSPH**.
- Aguarde o IntelliJ carregar as dependências.

---

## 2. Rodar a aplicação

- No IntelliJ, abra o arquivo `ApiFsphApplication.java` dentro de `src/main/java`.
- Clique com o botão direito no arquivo e escolha **Run 'ApiFsphApplication'**.
- A aplicação irá iniciar e ficará rodando localmente na porta **8080**.

---

## 3. Testar a aplicação

Você pode usar o **Swagger UI** ou o **Postman** para testar os endpoints.

### Swagger UI

- Acesse no navegador:  
  `http://localhost:8080/swagger-ui/index.html`

### Postman

- Abra o Postman.
- Use os links e exemplos abaixo para testar os endpoints.

---

## Endpoints e exemplos de uso

### Amostra

- **Criar amostra(s) (POST)**  
  `http://localhost:8080/api/amostras`  
  Exemplo JSON para uma ou várias amostras:

  ```json
  [
    {
      "tipo": "LARVAS",
      "localCaptura": "Área urbana",
      "enderecoCaptura": "Rua das Travecias, Centro",
      "dataCaptura": "2025-05-21",
      "municipioId": "e1a5f084-21cb-4d2b-a1d4-cd3c3f4f4e2f",
      "municipioNome": "Juazeiro"
    }
  ]
  ```

- **Buscar amostra pelo protocolo (GET)**  
  `http://localhost:8080/api/amostras/{protocoloAmostra}`

- **Buscar todas as amostras (GET)**  
  `http://localhost:8080/api/amostras/todas`

- **Deletar amostra (DELETE)**  
  `http://localhost:8080/api/amostras/{protocoloAmostra}`

- **Atualizar amostra parcialmente (PATCH)**  
  `http://localhost:8080/api/amostras/{protocoloAmostra}`  
  Exemplo JSON:

  ```json
  {
    "status": "Concluída",
    "observacao": "Amostra analisada sem anomalias."
  }
  ```

---

### Lote

- **Criar lote (POST)**  
  `http://localhost:8080/api/lotes`  
  Exemplo JSON:

  ```json
  {
    "loteLamina": false,
    "dataEnvio": "2025-05-20",
    "amostrasId": [
      "212025-00001",
      "212025-00002"
    ]
  }
  ```

- **Buscar lote pelo protocolo (GET)**  
  `http://localhost:8080/api/lotes/{protocoloLote}`

- **Atualizar status, dataEnvio e dataRecebimento do lote (PATCH)**  
  `http://localhost:8080/api/lotes/{protocoloLote}`  
  Exemplo JSON (pode atualizar um ou mais campos):

  ```json
  {
    "status": "ENVIADO",
    "dataEnvio": "2025-05-21",
    "dataRecebimento": "2025-05-22"
  }
  ```

- **Atualizar lista de amostras do lote (PATCH)**  
  `http://localhost:8080/api/lotes/{protocoloLote}/amostras`  
  Exemplo JSON (adicione ou remova IDs de amostras):

  ```json
  {
    "amostrasId": []
  }
  ```

- **Deletar lote (DELETE)**  
  `http://localhost:8080/api/lotes/{protocoloLote}`  
  *Só pode deletar o lote se não tiver amostras dentro.*

---

### Laudo (relatório PDF)

- **Enviar arquivo PDF do laudo para um lote (POST)**  
  `http://localhost:8080/laudo/upload/{protocoloLote}`  
  *Enviar arquivo PDF do laudo associado ao lote.*

- **Baixar arquivo PDF do laudo (GET)**  
  `http://localhost:8080/laudo/download/{id}`  
  *Baixa o arquivo PDF anexado ao lote.*

---

## Observação sobre autenticação JWT

- Algumas rotas podem exigir autenticação usando token JWT.
- Normalmente, você deve obter o token via login e enviar no cabeçalho `Authorization` do Postman:  
  `Authorization: Bearer SEU_TOKEN_AQUI`

---

