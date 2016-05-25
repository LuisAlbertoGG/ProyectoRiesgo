/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.UsuarioDao;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import logica.mail;
import modelo.Usuario;

/**
 *
 * @author luis
 */
@ManagedBean
@RequestScoped
public class beanRecuperarContrasenha {
    private String correo;

    private final UsuarioDao dao;
    //varibale de request  y el de mensajes 
    private final HttpServletRequest httpServletRequest;
    private final FacesContext faceContext;
    private FacesMessage message;

    /**
     * constructor que debe de inicializar el request y el dao
     */
    public beanRecuperarContrasenha() {
        dao = new UsuarioDao();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
    }

    public String recuperarContrasenha() {

        Usuario p1, p2;
        p1 = new Usuario();
        p1.setUCorreo(correo);
        String vCorreo;
        vCorreo = validarCorreo(correo);

        if (!vCorreo.equals("")) {

            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, vCorreo, null);
            faceContext.addMessage(null, message);
            faceContext.getExternalContext().getFlash().setKeepMessages(true);
            return beanIndex.RECUPERAR_CONTRASENHA();
        } else {
            String contra;
            p2 = dao.validarCorreo(p1);
            contra = p2.getUContrasenha();
            mail e = new mail();
            e.correo(correo, contra);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "El correo de recuperación fue enviado", null);
            faceContext.addMessage(null, message);
            faceContext.getExternalContext().getFlash().setKeepMessages(true);
            return beanIndex.INDEX();
        }        
    }

    

     public String validarCorreo(String c) {

        UsuarioDao daoTemp = new UsuarioDao(); //crea un alumnodao para validar
        String aux;
        if (c.equals("")) { // si es vacaio 
            return "Correo vacio.";
        }

        if (daoTemp.existeCorreo(c)) {
            return "";
        }

        return "El correo no se encuentra en la base de datos.";
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
