
package bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author esmeralda
 */
@ManagedBean
@RequestScoped
public class beanIndex {

    public static final String INDEX = "index";
    public static final String LOGIN = "login";
    public static final String INICIO = "inicio";
    public static final String MODIFICAR_PERFIL = "modificarperfil";
    public static final String NUEVA_PUBLICACION = "nuevapublicacion";
    public static final String MIS_ACTIVIDADES = "misactividades";
    public static final String VER_ACTIVIDADES = "veractividades";
    public static final String MI_SOLICITUD = "misolicitud";
    public static final String REGISTRAR = "registrar";
    
    public String INDEX() {
        return INDEX;
    }

    public String LOGIN() {
        return LOGIN;
    }

    public String MODIFICAR_PERFIL() {
        return MODIFICAR_PERFIL;
    }
    
    public String NUEVA_PUBLICACION(){
        return NUEVA_PUBLICACION;
    }
    
    public String MIS_ACTIVIDADES(){
        return MIS_ACTIVIDADES;
    }
    
    public String VER_ACTIVIDADES(){
        return VER_ACTIVIDADES;
    }
    
    public String MI_SOLICITUD(){
        return MI_SOLICITUD;
    }
    
    public String REGISTRAR(){
        return REGISTRAR;
    }
}