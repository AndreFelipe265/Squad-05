package Squad5.API_FSPH.Login.Usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Tipo_usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TipoUsuario {
    @Id
    @Column(name = "User_Id")
    private Integer userId;

    @Column(name = "Tipo_nome")
    private String tipoNome;

    @OneToMany(mappedBy = "tipoUsuario", cascade = CascadeType.ALL)
    private List<Usuario> usuarios;
}
