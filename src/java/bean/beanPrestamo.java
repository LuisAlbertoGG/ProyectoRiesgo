    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import modelo.Prestamo;
import dao.PrestamoDao;
import modelo.Libro;
import dao.LibroDao;
import modelo.Usuario;
import dao.UsuarioDao;
import modelo.Intercambio;
import dao.IntercambioDao;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import bean.beanPublicacion;
import java.util.ArrayList;
import logica.mail;

/**
 *
 * @author luis
 */
@ManagedBean
@RequestScoped
public class beanPrestamo {
    
    private final HttpServletRequest httpServletRequest;
    private final FacesContext faceContext;
    private FacesMessage message;
    private final PrestamoDao PDao;
    private final LibroDao LDao;
    private final UsuarioDao UDao;
    private final IntercambioDao IDao;
    private int pres = 0;
    private final Intercambio intercambio;
    
    public beanPrestamo(){
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        PDao = new PrestamoDao();
        LDao = new LibroDao();
        UDao = new UsuarioDao();
        IDao = new IntercambioDao();
        intercambio = new Intercambio();
    }
    
    public String cancelarPrestamo(int id_prestamo){
        Libro libro;
        Libro tmp1;
        Prestamo pres1;
        Intercambio tmp;
        Intercambio tmp2;
        List<Intercambio> inter;
        try{
        pres1 = PDao.obtenerPorID(id_prestamo);
        System.out.println("Llega aqui1");
        libro = pres1.getLibro();
        System.out.println("Llega aqui2");
        libro.setLOferta(Boolean.TRUE);
        System.out.println("Llega aqui3");
        inter = IDao.obtenerPorPrestamo(id_prestamo);
        System.out.println("Llega aqui4");
        if(inter != null){
        if(inter.size() > 1){
            tmp = inter.get(0);
            tmp2 = inter.get(1);
            tmp.getLibro().setLOferta(Boolean.TRUE);            
            tmp2.getLibro().setLOferta(Boolean.TRUE);
            IDao.borrar(tmp);
            IDao.borrar(tmp2);
            
        }else{
            if(inter.get(0) != null){
                tmp = inter.get(0);
                tmp.getLibro().setLOferta(Boolean.TRUE);            
                IDao.borrar(tmp);
            }
        }
        }
        PDao.borrar(pres1);
        message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Prestamo cancelado exitosamente", null);
        faceContext.addMessage(null, message);
        faceContext.getExternalContext().getFlash().setKeepMessages(true);
        return beanIndex.INICIO();
        }catch(Exception e){
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getLocalizedMessage(), null);
            faceContext.addMessage(null, message);
            faceContext.getExternalContext().getFlash().setKeepMessages(true);
            return beanIndex.INICIO();
        }
    }
    
    public String eliminar(int id){
        List<Intercambio> list1;
        try{
            list1 = IDao.obtenerPorPrestamo(id);
            for(int i = 0; i < list1.size(); i++){
                IDao.borrar(list1.get(i));
            }
            PDao.borrar(PDao.obtenerPorID(id));
            message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Solicitud eliminada correctamente", null);
            faceContext.addMessage(null, message);
            faceContext.getExternalContext().getFlash().setKeepMessages(true);
            return beanIndex.MIS_SOLICITUDES();
        }catch(Exception e){
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getLocalizedMessage(), null);
            faceContext.addMessage(null, message);
            faceContext.getExternalContext().getFlash().setKeepMessages(true);
            return beanIndex.MIS_SOLICITUDES();
        }
    }
    
    public List<Prestamo> mostrarPrestamoAceptado(int id){
        List<Prestamo> list = null;
        try{
            String[] a = {"id_usuario_prestador", "p_activo"};
            String[] b = {""+id, "true"};
            list = PDao.obtenerPorPrestamo1(a, b);
            return list;
        }catch(Exception e){
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getLocalizedMessage(), null);
            faceContext.addMessage(null, message);
            faceContext.getExternalContext().getFlash().setKeepMessages(true);
            return null;
        }
    }
    
    public String aceptar(int id){
        Prestamo pres1;
        Prestamo pres2;
        List<Prestamo> list1;
        List<Intercambio> list;
        List<Intercambio> list2;
        List<Intercambio> list3;
        List<Integer> impo = new ArrayList<>();
        Libro tmp;
        Libro tmp2;
        Libro tmp3;
        Prestamo tmp1;
        Prestamo s;
        int lib;
        int lib1;
        try{
        pres1 = PDao.obtenerPorID(id);
        pres1.setPActivo(true);
        tmp = pres1.getLibro();
        tmp.setLOferta(false);        
        list2 = IDao.obtenerPorPrestamo(id);
        if(list2.size() > 1){
            lib = list2.get(0).getLibro().getIdLibro();
            lib1 = list2.get(1).getLibro().getIdLibro();
            tmp2 = LDao.obtenerPorID(lib);
            tmp2.setLOferta(Boolean.FALSE);
            tmp3 = LDao.obtenerPorID(lib1);
            tmp3.setLOferta(Boolean.FALSE);
            LDao.actualizar(tmp3);
            list = IDao.obtenerPorLibro(lib);
            for(int i = 0; i < list.size(); i++){
                if(list.get(i).getPrestamo().getIdPrestamo() != id){
                    IDao.borrar(list.get(i));
                }
            }
            list = IDao.obtenerPorLibro(lib1);
            for(int i = 0; i < list.size(); i++){
                if(list.get(i).getPrestamo().getIdPrestamo() != id){
                    IDao.borrar(list.get(i));
                }
            }
        }else{
            if(list2.get(0) != null){
                lib = list2.get(0).getLibro().getIdLibro();
                list = IDao.obtenerPorLibro(lib);
                for(int i = 0; i < list.size(); i++){
                    if(list.get(i).getPrestamo().getIdPrestamo() != id){
                        IDao.borrar(list.get(i));
                    }
                }
                tmp3 = LDao.obtenerPorID(lib);
                tmp3.setLOferta(Boolean.FALSE);
            }
        }
        list = IDao.obtenerPorLibro(tmp.getIdLibro());        
        if(list != null){
            for(int i = 0; i < list.size(); i++){
                s = list.get(i).getPrestamo();
                list3 = IDao.obtenerPorPrestamo(s.getIdPrestamo());
                if(list3 != null){
                    for(int e = 0; e < list3.size(); e++){
                        IDao.borrar(list3.get(e));
                    }
                }
                impo.add(s.getIdPrestamo());
            }
        }
        PDao.actualizar(pres1);
        if(impo != null){
            for(int r = 0; r < impo.size(); r++){                
                pres2 = PDao.obtenerPorID(impo.get(r));
                PDao.borrar(pres2);                
            }
        }
        mail e = new mail();
        e.avisarPrestador(pres1.getUsuarioByIdUsuarioSolicitante().getUNombre(), pres1.getLibro().getLTitulo(), pres1.getUsuarioByIdUsuarioSolicitante().getUTelefono(), pres1.getUsuarioByIdUsuarioPrestador().getUCorreo(), pres1.getUsuarioByIdUsuarioSolicitante().getUCorreo());
        e.avisarSolicitante(pres1.getUsuarioByIdUsuarioPrestador().getUNombre(), pres1.getLibro().getLTitulo(), pres1.getUsuarioByIdUsuarioPrestador().getUTelefono(), pres1.getUsuarioByIdUsuarioSolicitante().getUCorreo(), pres1.getUsuarioByIdUsuarioPrestador().getUCorreo());
        message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Solicitud aceptada", null);
        faceContext.addMessage(null, message);
        faceContext.getExternalContext().getFlash().setKeepMessages(true);
        return beanIndex.INICIO();
        }catch(Exception e){
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getLocalizedMessage(), null);
            faceContext.addMessage(null, message);
            faceContext.getExternalContext().getFlash().setKeepMessages(true);
            return beanIndex.SOLICITUDES();
        }

    }
    
//    public void actualizaPrestamo(Prestamo p){
//        PDao.actualizar(p);
//    }
    
    public void actualizarLibro(Libro l){
        LDao.actualizar(l);
    }
    
    public void eliminarLibSes(){
        try{
            httpServletRequest.getSession().removeAttribute("sesionLibro");
        }catch(Exception e){
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getLocalizedMessage(), null);
            faceContext.addMessage(null, message);
            faceContext.getExternalContext().getFlash().setKeepMessages(true);
        }
        
    }
    
    public String rechazar(int id){
        List<Intercambio> list1;
        try{
            list1 = IDao.obtenerPorPrestamo(id);
            for(int i = 0; i < list1.size(); i++){
                IDao.borrar(list1.get(i));
            }
            PDao.borrar(PDao.obtenerPorID(id));
            message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Solicitud rechazada", null);
            faceContext.addMessage(null, message);
            faceContext.getExternalContext().getFlash().setKeepMessages(true);
            return beanIndex.SOLICITUDES();
        }catch(Exception e){
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getLocalizedMessage(), null);
            faceContext.addMessage(null, message);
            faceContext.getExternalContext().getFlash().setKeepMessages(true);
            return beanIndex.SOLICITUDES();
        }
    }
    
    public List<Intercambio> mostrarIntercambio(int id){
        List<Intercambio> list;
        list = IDao.obtenerPorPrestamo(id);
        return list;
    }
    
    public List<Prestamo> mostrarSolicitudes(int id){
        List<Prestamo> list;
        String[] atributos = {"p_activo","id_usuario_prestador"};
        String[] valores = {"false",""+id};
        list = PDao.obtenerPorPrestamo1(atributos, valores);
        return list;
    }
    
    public List<Prestamo> mostrarMisSolicitudes(int id){
        List<Prestamo> list = null;
        String[] atributos = {"p_activo","id_usuario_solicitante"};
        String[] valores = {"false",""+id};
        list = PDao.obtenerPorPrestamo1(atributos, valores);
        return list;
    }
    
    public int obtenerId(int id_libro){
        Libro libro1;
        Usuario usuario1;
        int id1;
        libro1 = LDao.obtenerPorID(id_libro);
        usuario1 = libro1.getUsuario();
        id1 = usuario1.getIdUsuario();
        return id1;
    }
    
    public String ofrecer(int id_libro2){
        try{
            Intercambio intTemp;
            pres = (int)httpServletRequest.getSession().getAttribute("prestamo");
            Prestamo temp = PDao.obtenerPorID(pres);
            intercambio.setPrestamo(temp);
            intercambio.setLibro(LDao.obtenerPorID(id_libro2));
            String[] atributos = {"id_libro","id_prestamo"};
            String[] valores = {""+id_libro2, ""+pres};
            intTemp = IDao.obtenerPorPrestamo(atributos, valores);
            if(IDao.obtenerPorPrestamo(pres) == null || IDao.obtenerPorPrestamo(pres).size() < 2){
                if(intTemp == null){
                IDao.insertar(intercambio);
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Libro añadido a la solicitud", null);
                faceContext.addMessage(null, message);
                faceContext.getExternalContext().getFlash().setKeepMessages(true);
                return beanIndex.INTERCAMBIO();
                }else{
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Este libro ya fue añadido", null);
                    faceContext.addMessage(null, message);
                    faceContext.getExternalContext().getFlash().setKeepMessages(true);
                    return beanIndex.INTERCAMBIO();
                }
            }else{
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sólo puedes intercambiar ese libro por a los más 2 libros ", null);
                faceContext.addMessage(null, message);
                faceContext.getExternalContext().getFlash().setKeepMessages(true);
                return beanIndex.INTERCAMBIO();
            }
        }catch(Exception e){
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getLocalizedMessage(), null);
            faceContext.addMessage(null, message);
            faceContext.getExternalContext().getFlash().setKeepMessages(true);
            return beanIndex.INTERCAMBIO();
        }
    }
    
    public String guardarPrestamo(int id_libro1, int id_solicitante) {

        Usuario prestador;
        int id_prestador;
        Usuario solicitante;
        Libro libro;
        Prestamo prestamo;
        id_prestador = obtenerId(id_libro1);
        if(id_solicitante == id_prestador){
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "No puedes solicitar un libro que tú publicaste ", null);
            faceContext.addMessage(null, message);
            faceContext.getExternalContext().getFlash().setKeepMessages(true);
            return beanIndex.DETALLES_LIBRO();
        }
        
        try{
            prestador = UDao.obtenerPorID(id_prestador);
            solicitante = UDao.obtenerPorID(id_solicitante);
            libro = LDao.obtenerPorID(id_libro1);
            prestamo = new Prestamo();
            prestamo.setLibro(libro);
            prestamo.setUsuarioByIdUsuarioPrestador(prestador);
            prestamo.setUsuarioByIdUsuarioSolicitante(solicitante);
            prestamo.setPActivo(false);
            Prestamo temp;
            int idTemp;
            String[] atributos = {"id_libro","id_usuario_solicitante"};
            String[] valores = {""+libro.getIdLibro(),""+id_solicitante};
            temp = PDao.obtenerPorPrestamo(atributos, valores);
            if(temp == null){
                PDao.insertar(prestamo);
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitud iniciada, elige el o los libros a intercambiar ", null);
                faceContext.addMessage(null, message);
                faceContext.getExternalContext().getFlash().setKeepMessages(true);
                temp = PDao.obtenerPorPrestamo(atributos, valores);
                idTemp = temp.getIdPrestamo();
                httpServletRequest.getSession().setAttribute("prestamo", idTemp);
                return beanIndex.INTERCAMBIO();
            }else{
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ya tienes una solicitud por este libro ", null);
                faceContext.addMessage(null, message);
                faceContext.getExternalContext().getFlash().setKeepMessages(true);
                return beanIndex.DETALLES_LIBRO();
            }
        }catch(Exception e){
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getLocalizedMessage(), null);
            faceContext.addMessage(null, message);
            faceContext.getExternalContext().getFlash().setKeepMessages(true);
            return beanIndex.DETALLES_LIBRO();
        }
        
        
    }

    
}
