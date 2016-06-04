
package bean;

import dao.UsuarioDao;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import modelo.Usuario;


/**
 *
 * @author esmeralda
 */
@ManagedBean
@RequestScoped
public class beanLogin {
    
    private final UsuarioDao daoP;
    
    
    private String correo;
    private String contrasenha;
    
    private final HttpServletRequest httpServletRequest;
    private final FacesContext faceContext;
    private FacesMessage message;
    
    public beanLogin(){
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest)faceContext.getExternalContext().getRequest();
        daoP = new UsuarioDao();
    }
    
    public String login(){
        
        Usuario p1,p2;
        p1 = new Usuario();
        p1.setUCorreo(correo);
        p1.setUContrasenha(contrasenha);
        try{
            p2 = daoP.verificarUsuario(p1);
            if(p2 != null){
                httpServletRequest.getSession().setAttribute("id", p2.getIdUsuario());
                httpServletRequest.getSession().setAttribute("sesionNombre", p2.getUNombre());
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Acceso Otorgado.", "Acceso Otorgado");
                faceContext.addMessage(null, message);
                faceContext.getExternalContext().getFlash().setKeepMessages(true);
                return beanIndex.INICIO();
            }else{
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario o contraseña incorrecto.", null);
                faceContext.addMessage(null, message);
                faceContext.getExternalContext().getFlash().setKeepMessages(true);
            }
        }catch(Exception e){
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getLocalizedMessage(), null);
            faceContext.addMessage(null, message);
            faceContext.getExternalContext().getFlash().setKeepMessages(true);
        }
        return beanIndex.INDEX();
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenha() {
        return contrasenha;
    }

    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }
}
