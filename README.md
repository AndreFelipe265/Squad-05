# Squad-05
Colocar o arquivo ApiFsphApplication.java para rodar no intellij
#### link pra Amostra metodo POST: http://localhost:8080/api/amostras
json:  
{  
  "tipo": "MOSQUITO",  
  "quantidade": 10,  
  "enderecoCaptura": "Rua das Travecias, Centro",  
  "dataCaptura": "2025-05-14",  
  "insetoLarva": "Culex(teste)",  
  "municipioId": "e1a5f084-21cb-4d2b-a1d4-cd3c3f4f4e2f",  
  "municipioNome": "Juazeiro",  
  "protocoloLote": "PROTO-JZN-000789"  
}
#### link pra Amostra metodo GET: http://localhost:8080/api/amostras/{protocolo(ex: 052025-00001)}

#### link pra Amostra metodo DELETE: http://localhost:8080/api/amostras/{protocolo(ex: 052025-00001)}
apaga a amostra do sistema

#### link pra Amostra metodo PATCH: http://localhost:8080/api/amostras/{protocolo(ex: 052025-00001)}
json:  
{  
  "status": "Concluída",  
  "observacao": "Amostra analisada sem anomalias."  
}  
