
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
    public static final String REGISTRAR = "registrar";
    public static final String RECUPERAR_CONTRASENHA = "recuperarcontrasenha";
    public static final String PUBLICAR = "publicar";
    public static final String MIS_PUBLICACIONES = "mispublicaciones";
    public static final String VER_PUBLICACIONES = "verpublicaciones";
    public static final String DETALLES_LIBRO = "detalleslibro";
    public static final String DETALLES_MI_LIBRO = "detallesmilibro";
    public static final String ACTUALIZAR_PUBLICACION = "actualizarpublicacion";
    
    public static String ACTUALIZAR_PUBLICACION(){
        return ACTUALIZAR_PUBLICACION;
    }    
    
    public static String DETALLES_MI_LIBRO(){
        return DETALLES_MI_LIBRO;
    }
    
    public static String DETALLES_LIBRO(){
        return DETALLES_LIBRO;
    }
    
    public static String INDEX() {
        return INDEX;
    }

    public static String LOGIN() {
        return LOGIN;
    }

    public static String INICIO(){
        return INICIO;
    }
    
    public static String REGISTRAR(){
        return REGISTRAR;
    }
    
    public static String RECUPERAR_CONTRASENHA(){
        return RECUPERAR_CONTRASENHA;
    }
    
    public static String PUBLICAR(){
        return PUBLICAR;
    }
    
    public static String MIS_PUBLICACIONES(){
        return MIS_PUBLICACIONES;
    }
    
    public static String VER_PUBLICACIONES(){
        return VER_PUBLICACIONES;
    }
}