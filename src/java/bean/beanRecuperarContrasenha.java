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
            e.correo(correo, cifrar(contra));
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "El correo de recuperación fue enviado", null);
            faceContext.addMessage(null, message);
            faceContext.getExternalContext().getFlash().setKeepMessages(true);
            return beanIndex.INDEX();
        }        
    }
    
    public static String cifrar(String contra){
        int longi = contra.length();
        String resultado = "";
        String[] deaqui = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","ñ","o","p","q","r","s","t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9"};
        String[] aaqui = {"z","y","x","w","v","u","t","s","r","q","p","o","ñ","n","m","l","k","j","i","h","g","f","e","d","c","b","a","9","8","7","6","5","4","3","2","1","0"};
        for(int i = 0; i<longi; i++){
            String tmp = ""+contra.charAt(i);
            for(int e = 0; e<37; e++){
                if(tmp.equals(deaqui[e])){
                    resultado = resultado+aaqui[e];
                }
            }
        }
        return resultado;
        
    }

    

     public String validarCorreo(String c) {

        UsuarioDao daoTemp = new UsuarioDao();
        String aux;
        if (c.equals("")) {  
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
