package bean;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author esmeralda
 */
@ManagedBean
@RequestScoped
public class beanLogout {

    private String usuario;
    private int id;
    private final HttpServletRequest httpServletRequest;
    private final FacesContext faceContext;
    private FacesMessage message;

    public beanLogout() {
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        usuario = httpServletRequest.getSession().getAttribute("sesionNombre").toString();
        id = (int)httpServletRequest.getSession().getAttribute("id");
    }

    public String cerrarSesion() {
        httpServletRequest.getSession().removeAttribute("sesionNombre");
        httpServletRequest.getSession().removeAttribute("id");
        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Session cerrada correctamente", null);
        faceContext.addMessage(null, message);
        return beanIndex.INDEX;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}