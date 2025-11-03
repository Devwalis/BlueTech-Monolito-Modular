package com.ecommerce.bluetech.model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor

@Entity(name = "tb_usuarios")
public class User {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String name;

    @Column(nullable =  false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private LocalDate dateOfBirth;


    @Column(nullable = false)
    private Boolean administrator;

    @Column(nullable = false)
    private Boolean supplie;

    @Column(nullable = false)
    private Boolean customer;


    public User(){
        this.administrator = Boolean.FALSE;
        this.customer = Boolean.FALSE;
        this.supplie = Boolean.FALSE;
    }

    public String getTipoUsuario(){
        if(this.administrator) return "ADMINISTRATOR";
        if(this.supplie) return "SUPPLIE";
        return "CUSTOMER";
    }

    
}
