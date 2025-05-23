package Squad5.API_FSPH.Login.Usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @Column(name = "User_Id")
    private String userId;

    @Column(name = "Nome_usuario")
    private String nomeUsuario;

    @Column(name = "Senha")
    private String senha;

    @ManyToOne
    @JoinColumn(name = "Tipos_usuarios", referencedColumnName = "User_Id")
    private TipoUsuario tipoUsuario;

}




