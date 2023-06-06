package co.edu.uceva.programa_service.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="programas")
public class Programa {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_programa")
    private Long id_programa;

    private String nombre;

    
}
