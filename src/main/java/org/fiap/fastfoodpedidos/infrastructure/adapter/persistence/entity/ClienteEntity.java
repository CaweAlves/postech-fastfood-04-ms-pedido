package org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "cliente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ClienteEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Size(max = 100)
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Size(max = 150)
    @Email
    @Column(name = "email", length = 150)
    private String email;

    @NotNull
    @Size(max = 11)
    @Column(name = "cpf", length = 11, nullable = false, unique = true)
    private String cpf;

    public ClienteEntity id(Integer id) {
        this.setId(id);
        return this;
    }

    public ClienteEntity nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public ClienteEntity email(String email) {
        this.setEmail(email);
        return this;
    }

    public ClienteEntity cpf(String cpf) {
        this.setCpf(cpf);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClienteEntity)) {
            return false;
        }
        return id != null && id.equals(((ClienteEntity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + getId() +
                ", nome='" + getNome() + "'" +
                ", email='" + getEmail() + "'" +
                ", cpf='" + getCpf() + "'" +
                "}";
    }
}
