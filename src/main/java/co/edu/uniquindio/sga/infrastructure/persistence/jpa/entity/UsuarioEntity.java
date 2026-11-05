package co.edu.uniquindio.sga.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class UsuarioEntity {

    @Id
    @Column(name = "correo", nullable = false, unique = true)
    private String correo;

    @Column(name = "contrasena", nullable = false)
    private String contrasena; // Guardada cifrada con BCrypt

    @Column(name = "rol", nullable = false)
    private String rol; // EJ: "ADMIN", "RECEPCIONISTA", "HUESPED"

    public UsuarioEntity() {}

    public UsuarioEntity(String correo, String contrasena, String rol) {
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}