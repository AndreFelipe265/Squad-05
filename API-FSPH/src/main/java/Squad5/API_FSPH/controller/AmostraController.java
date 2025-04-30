package Squad5.API_FSPH.controller;

import Squad5.API_FSPH.entity.Amostra;
import Squad5.API_FSPH.service.AmostraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/idProtocolo")
//declarar que é um arquivo de controler, declarar caminho,
public class AmostraController {
    //declarar metodos (post/get/put/delete) e seus comportamentos

    private AmostraService amostraService;

    public AmostraController(AmostraService amostraService) {
        this.amostraService = amostraService;
    }

    @PostMapping
    ResponseEntity<Amostra> createAmostra(@RequestBody CreateAmostraDto createAmostraDto){
        amostraService.createAmostra(createAmostraDto);
        return ResponseEntity.created(URI.create("v1/Amostra/" + amostraId.toString())).build();;
    }

    @GetMapping("/{idProtocolo}")
    ResponseEntity<Amostra> getAmostraByID(@PathVariable("idProtocolo") String idProtocolo){
        var amostra = amostraService.getAmostraById(idProtocolo);
        if (amostra.isPresent()){
          return ResponseEntity.ok(amostra.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Amostra>> listAmostras() {
        var amostras = amostraService.listAmostras();
        return ResponseEntity.ok(amostras);
    }

    @PutMapping("/{idProtocolo}")
    public ResponseEntity<Void> updateAmostraById(@PathVariable("idProtocolo")String idProtocolo,
                                                  @RequestBody UpdateAmostraDto updateAmostraDto) {
        amostraService.updateAmostraById(idProtocolo, updateAmostraDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idProtocolo}")
    public ResponseEntity<Void> deleteById(@PathVariable("idProtocolo") String idProtocolo) {
        amostraService.deleteById(idProtocolo);
        return ResponseEntity.noContent().build();
    }

}
