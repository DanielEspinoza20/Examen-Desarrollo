package ui;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.inject.Named;
import mx.sauap_db.entity.Usuario;
import mx.sauap_db.desarrollo.facade.FacadeUsuario;

import java.io.Serializable;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private String username;
    private String password;
    private Usuario usuario;
    private FacadeUsuario facade = new FacadeUsuario();

    public String login() {
        System.out.println("LOG: Intentando login con: " + username);
        usuario = facade.login(username, password);

        if (usuario != null) {
            System.out.println("LOG: Usuario encontrado. Rol en BD: " + usuario.getRol());

            if ("ADMINISTRADOR".equalsIgnoreCase(usuario.getRol())) {
                return "admin?faces-redirect=true";
            } else if ("PROFESOR".equalsIgnoreCase(usuario.getRol())) {
                return "profesor?faces-redirect=true";
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario o contraseña incorrectos"));
        }
        return null;
    }

    public void verificarProfesor(ComponentSystemEvent event) {
        if (usuario == null || !"PROFESOR".equalsIgnoreCase(usuario.getRol())) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void verificarAdmin(ComponentSystemEvent event) {
        if (usuario == null || !"ADMINISTRADOR".equalsIgnoreCase(usuario.getRol())) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Usuario getUsuario() { return usuario; }
}