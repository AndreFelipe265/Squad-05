# Squad-05
Abrir o arquivo API-FSPH como projeto no intellij  
Colocar o arquivo ApiFsphApplication.java para rodar no intellij  
Usar os links abaixo no POSTMAN para teste ou abrir o Swagger: http://localhost:8080/swagger-ui/index.html  

## Amostra

#### link pra Amostra metodo POST: http://localhost:8080/api/amostras
Esse mesmo metodo pode ser usado para mandar varias amostras de uma vez  
json:  
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
#### link pra Amostra metodo GET: http://localhost:8080/api/amostras/{protocoloAmostra}

#### link pra todas as Amostras metodo GET: http://localhost:8080/api/amostras/todas

#### link pra Amostra metodo DELETE: http://localhost:8080/api/amostras/{protocoloAmostra}

#### link pra Amostra metodo PATCH: http://localhost:8080/api/amostras/{protocoloAmostra}
json:  
{  
  "status": "Concluída",  
  "observacao": "Amostra analisada sem anomalias."  
}  

## Lote

#### link pra Lote metodo POST: http://localhost:8080/api/lotes
json:  
{  
  "loteLamina": false,  
  "dataEnvio": "2025-05-20",  
  "amostrasId": [  
    "212025-00001",  
    "212025-00002"  
  ]  
}  

#### link pra Lote metodo GET: http://localhost:8080/api/lotes/{protocoloLote}

#### link pra Lote (status, dataEnvio e dataRecebimento) metodo PATCH: http://localhost:8080/api/lotes/{protocoloLote}
É possivel alterar cada um individualmente nesse Link  
json:  
{  
  "status": "ENVIADO",  
  "dataEnvio": "2025-05-21",  
  "dataRecebimento": "2025-05-22"  
}  

#### link pra Lote (lista de amostras) metodo PATCH: http://localhost:8080/api/lotes/{protocoloLote}/amostras
Link para alterar apenas a lista de Amostras de um Lote pode ser usado pra remover e adicionar amostras  
json:  
{  
  "amostrasId": []  
}  

#### link pra Lote metodo DELETE: http://localhost:8080/api/lotes/{protocoloLote}
Deleta o Lote apenas quando n tiver mais amostras dentro dele  

