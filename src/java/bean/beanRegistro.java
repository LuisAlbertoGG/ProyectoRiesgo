
package bean;

import dao.UsuarioDao;
import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import modelo.Usuario;


/**
 *
 * @author luis
 */
@ManagedBean
@RequestScoped
public class beanRegistro {
    
    public final static String LETRAS = "QWERTYUIOPASDFGHJKLÑZXCVBNMqwertyuiopasdfghjklñzxcvbnm";
    public final static String NUMEROS = "1234567890";
    public final static String SIMBOLOS = "";
    public final static String DOMINIO = "@ciencias.unam.mx";
    public final static String DOMINIO1 = "@gmail.com";
    public final static String DOMINIO2 = "@hotmail.com";
    
    
    private String nombre;
    private String apellido;
    private String contrasenha;
    private String contrasenha2;
    private String correo;
    private String telefono;
    private final UsuarioDao daoA;
    
    
    private final HttpServletRequest httpServletRequest;
    private final FacesContext faceContext;
    private FacesMessage message;
    
    public beanRegistro() {
        daoA = new UsuarioDao();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
    }
    
    public String registrarUsuario(){
        Usuario a;
        String errorCont,errorNombre,errorCorreo, errorApellido, errorTelefono;
        errorCont = validarContrasenha(contrasenha, contrasenha2);
        errorNombre = validarNombre(nombre);
        errorApellido = validarNombre(apellido);
        errorCorreo = validarCorreo(correo, true);
        errorTelefono = validarTelefono(telefono);
        if(!errorCont.equals("")){
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,errorCont, null);
            faceContext.addMessage(null, message);
            faceContext.getExternalContext().getFlash().setKeepMessages(true);
        }else if(!errorNombre.equals("")){
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,errorNombre, null);
            faceContext.addMessage(null, message);
            faceContext.getExternalContext().getFlash().setKeepMessages(true);
        }else if(!errorApellido.equals("")){
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,errorApellido, null);
            faceContext.addMessage(null, message);
            faceContext.getExternalContext().getFlash().setKeepMessages(true);
        }else if(!errorTelefono.equals("")){
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,errorTelefono, null);
            faceContext.addMessage(null, message);
            faceContext.getExternalContext().getFlash().setKeepMessages(true);
        }else if(!errorCorreo.equals("")){
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,errorCorreo, null);
            faceContext.addMessage(null, message);
            faceContext.getExternalContext().getFlash().setKeepMessages(true);
        }else{
            a = new Usuario();
            a.setUContrasenha(cifrar(contrasenha));
            a.setUNombre(nombre);
            a.setUApellido(apellido);
            a.setUCorreo(correo);
            a.setUTelefono(telefono);
            try{
                System.out.println(a.getIdUsuario());
                daoA.insertar(a);
                message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Cuenta creda correctamente.", null);
                faceContext.addMessage(null, message);
                faceContext.getExternalContext().getFlash().setKeepMessages(true);
                return beanIndex.INDEX();
            }catch(Exception e){
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getLocalizedMessage(), null);
                faceContext.addMessage(null, message);
                faceContext.getExternalContext().getFlash().setKeepMessages(true);
            }
        }
        return beanIndex.REGISTRAR();
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
    
    public static String validarTelefono(String telefono){
        if(telefono.length() != 10){
            return "Longitud del teléfono incorrecta";
        }
        return "";
    }
    public static String validarContrasenha(String c1, String c2){
        String aux;
        if(c1 == null || c2 == null){
            return "Contraseña vacia.";
        }else if(!c1.equals(c2)){
            return "Las contraseñas ingresadas son distintas.";
        }else if(c1.length() < 6 || c1.length() > 20){
            return "Las contraseña debe tener entre 6 y 20 caracteres.";
        }
        aux = borrar(c1, LETRAS);
        aux = borrar(aux, NUMEROS);
        if(!aux.equals("")){
            return "La contraseña solo debe contener caracteres alfanumericos.";
        }
        return "";
    }
    
    public static String validarNombre(String n){
        String aux;
        if(n == null){
            return "Nombre vacio.";
        }else if(n.length() < 3 || n.length() > 20){
            return "El nombre debe tener entre 3 y 20 caracteres.";
        }
        aux = borrar(n, LETRAS);
        if(!aux.equals("")){
            return "El nombre solo puede contener Letras.";
        }
        return "";
    }
    
    public static String validarCorreo(String c, boolean tabla){
        UsuarioDao daoA;
        String aux;
        if(c == null){
            return "Correo vacio.";
        }
        if(!c.endsWith(DOMINIO) && !c.endsWith(DOMINIO1) && !c.endsWith(DOMINIO2)){
            return "El correo debe de ser del dominio de @ciencias.unam.mx, @gmail.com o @hotmail.com";
        }

        if(tabla){
            daoA = new UsuarioDao();
            if(daoA.existeCorreo(c)){
                return "El correo ya existe en la base de datos.";
            }
        }
        return "";
    }
    
    public static String borrar(String borrada, String b){
        String aux = new String(borrada);
        String sub;
        for(int i = 0; i < b.length(); i++){
            sub = b.substring(i, i+1);
            aux = aux.replace(sub, "");
        }
        return aux;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasenha() {
        return contrasenha;
    }

    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }

    public String getContrasenha2() {
        return contrasenha2;
    }

    public void setContrasenha2(String contrasenha2) {
        this.contrasenha2 = contrasenha2;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
