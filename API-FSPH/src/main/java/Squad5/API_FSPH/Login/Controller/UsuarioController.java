package Squad5.API_FSPH.Login.Controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UsuarioController {
    @GetMapping
    public ResponseEntity<String> getUser(){
        return ResponseEntity.ok("secesso!");
    }
}
