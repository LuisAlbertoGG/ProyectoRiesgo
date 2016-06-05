
package bean;

import dao.LibroDao;
import dao.UsuarioDao;
import java.util.LinkedList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import modelo.Libro;
import modelo.Usuario;
import org.hibernate.context.spi.CurrentSessionContext;
import dao.IntercambioDao;
import dao.PrestamoDao;
import modelo.Intercambio;
import modelo.Prestamo;
/**
 *
 * @author luis
 */
@ManagedBean
@RequestScoped
public class beanPublicacion {
    
    private final String EDICION_INVALIDA = "No se ha definido la edición.";
    private final String EVALCONT_INVALIDA = "No se ha definido la evaluación del contenido.";
    private final String EVALREDAC_INVALIDA = "No se ha definido la evaluación de la redacción.";
    
    private final int id;
    private Libro libro;
    private String titulo;
    private String autor;
    private String editorial ;
    private String isbn;
    private String anho;
    private int edicion;
    private String sEdicion;
    private int evalCont;
    private String sEvalCont;
    private int evalRedac;
    private String sEvalRedac;
    private String resenha;
    private String palabrasClaves;
    private final LibroDao dao;
    private final UsuarioDao daoP;
    private final HttpServletRequest httpServletRequest;
    private final FacesContext faceContext;
    private FacesMessage message;
    private int idA;
    private final PrestamoDao PDao;
    private final IntercambioDao IDao;
    
    public beanPublicacion() {
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest)faceContext.getExternalContext().getRequest();
        dao = new LibroDao();
        daoP = new UsuarioDao();
        sEdicion = EDICION_INVALIDA;
        sEvalCont = EVALCONT_INVALIDA;
        sEvalRedac = EVALREDAC_INVALIDA;
        id = (int)httpServletRequest.getSession().getAttribute("id");
        PDao = new PrestamoDao();
        IDao = new IntercambioDao();
        definirActividad();
    }
    
    private void definirActividad(){
        
        try{
            if(libro == null){
                idA = (int)httpServletRequest.getSession().getAttribute("sesionLibro");
                libro = dao.obtenerPorID(idA);
                titulo = libro.getLTitulo();
                autor = libro.getLAutor();
                editorial = libro.getLEditorial();
                isbn = libro.getLIsbn();
                anho = libro.getLAnho();                
                sEdicion = ""+libro.getNEdicion();
                sEvalCont = ""+libro.getLEvalucionContenido();
                sEvalRedac = ""+libro.getLEvaluacionRedaccion();
                resenha = libro.getLResehna();
                palabrasClaves = libro.getLPablasClave();
            }
        }catch(Exception e){
        
        }
    }
    public String eliminarLibSes(){
        try{
            httpServletRequest.getSession().removeAttribute("sesionLibro");
            return beanIndex.MIS_PUBLICACIONES();
        }catch(Exception e){
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getLocalizedMessage(), null);
            faceContext.addMessage(null, message);
            faceContext.getExternalContext().getFlash().setKeepMessages(true);
            return beanIndex.DETALLES_MI_LIBRO();
        }
        
    }
    public String eliminarLibSes1(){
        try{
            httpServletRequest.getSession().removeAttribute("sesionLibro");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Solicitud guardada", null);
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
    
    public String guardarPublicacion(){
        String error;
        Usuario usuario;
        error = checkCampos();
        try{
            if(error.equals("")){

                usuario = daoP.obtenerPorID(id);
                libro = new Libro();
                libro.setLTitulo(titulo);
                libro.setLAutor(autor);
                libro.setLEditorial(editorial);
                libro.setLIsbn(isbn);
                libro.setLAnho(anho);
                libro.setNEdicion(edicion);
                libro.setLEvalucionContenido(evalCont);
                libro.setLEvaluacionRedaccion(evalRedac);
                libro.setLResehna(resenha);
                libro.setLPablasClave(palabrasClaves);
                libro.setUsuario(usuario);
                libro.setLOferta(true);
                dao.insertar(libro);
                message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Libro publicado exitosamente.", null);
                faceContext.addMessage(null, message);
                faceContext.getExternalContext().getFlash().setKeepMessages(true);
                return beanIndex.INICIO();
            }else{
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR,error, null);
                faceContext.addMessage(null, message);
                faceContext.getExternalContext().getFlash().setKeepMessages(true);
                return beanIndex.PUBLICAR();
            }
        }catch(Exception e){
            System.out.println(e.toString());
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getLocalizedMessage(), null);
            faceContext.addMessage(null, message);
            faceContext.getExternalContext().getFlash().setKeepMessages(true);
            return beanIndex.PUBLICAR();
        }
    }
    
    public String actualizarPublicacion(){
        String error;
        Usuario usuario;
        error = checkCampos();
        try{
            if(error.equals("")){
                usuario = daoP.obtenerPorID(id);
                libro.setLTitulo(titulo);
                dao.actualizar(libro);
                message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Libro actualizado exitosamente.", null);
                faceContext.addMessage(null, message);
                faceContext.getExternalContext().getFlash().setKeepMessages(true);
                return beanIndex.DETALLES_MI_LIBRO;
            }else{
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR,error, null);
                faceContext.addMessage(null, message);
                faceContext.getExternalContext().getFlash().setKeepMessages(true);
                return beanIndex.DETALLES_MI_LIBRO;
            }
        }catch(Exception e){
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getLocalizedMessage(), null);
            faceContext.addMessage(null, message);
            faceContext.getExternalContext().getFlash().setKeepMessages(true);
            return beanIndex.DETALLES_MI_LIBRO;
        }
    }
    
    
    public String borrarPublicacion(){
        try{
            List<Prestamo> pres;
            List<Intercambio> inter;
            List<Intercambio> inter1;
            Prestamo tmp1;
            Intercambio tmp2;
            int id1;
            pres = PDao.obtenerPorLibro(libro.getIdLibro());
            if(pres != null){
                for(int i = 0; i<pres.size();i++){
                    tmp1 = pres.get(i);
                    inter = IDao.obtenerPorPrestamo(tmp1.getIdPrestamo());
                    if(inter != null){
                        for(int e = 0; e<inter.size();e++){
                            tmp2 = inter.get(e);
                            IDao.borrar(tmp2);
                        }
                    }
                    PDao.borrar(tmp1);
                }
            }
            inter = IDao.obtenerPorLibro(libro.getIdLibro());
            if(inter != null){
                for(int i = 0; i<inter.size();i++){
                    id1 = inter.get(i).getPrestamo().getIdPrestamo();
                    inter1 = IDao.obtenerPorPrestamo(id1);
                    if(inter1 != null){
                        for(int e = 0; e < inter1.size(); e++){
                            IDao.borrar(inter1.get(e));
                        }
                    }
                    PDao.borrar(PDao.obtenerPorID(id1));
                }
            }
            dao.borrar(libro);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Libro borrado exitosamente.", null);
            faceContext.addMessage(null, message);
            faceContext.getExternalContext().getFlash().setKeepMessages(true);
            return beanIndex.MIS_PUBLICACIONES;
        }catch(Exception e){
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getLocalizedMessage(), null);
            faceContext.addMessage(null, message);
            faceContext.getExternalContext().getFlash().setKeepMessages(true);
            return beanIndex.DETALLES_MI_LIBRO;
        }
    }
      
    
    public List<Libro> mostrarMisPublicaciones(int id){
        List<Libro> resultado;
        try{
            resultado = dao.obtenerPorUsuario(id);
        }catch(Exception e){
            resultado = new LinkedList<>();
        }
        return resultado;
        
    }
    
    public List<Libro> mostrarPublicaciones(){
        List<Libro> resultado;
        try{
            resultado = dao.obtenerMostrar("l_oferta", "true");
        }catch(Exception e){
            resultado = new LinkedList<>();
        }
        return resultado;
    }
    
    private String checkCampos(){
        try{
            evalCont =  Integer.parseInt(sEvalCont);
            evalRedac = Integer.parseInt(sEvalRedac);
            edicion = Integer.parseInt(sEdicion);
            if(evalCont < 1 || evalCont >10){
                return "Evaluación de contenido no valida.(Debe ser entre 1 y  10)";
            }
            if(evalRedac < 1 || evalRedac >10){
                return "Evaluación de redacción no valida.(Debe ser entre 1 y  10)";
            }
            if(edicion < 1){
                return "Edición no valida.";
            }
            if(titulo.isEmpty()){
                return "Titulo vacio.";
            }
            if(autor.isEmpty()){
                return "Autor vacio.";
            }
            if(isbn.isEmpty()){
                return "Isbn vacio.";
            }
            if(editorial.isEmpty()){
                return "Editorial vacia.";
            }
            if(anho.isEmpty()){
                return "Año vacio.";
            }
            if(resenha.isEmpty()){
                return "Reseña vacia.";
            }
            if(palabrasClaves.isEmpty()){
                return "Palabras Claves vacias.";
            }
        }catch(Exception e){
            
        }
        return "";
    }
    
    public String definirActividad1(Libro libro,boolean actualizar){
        if(libro == null){
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Libro invalido.", null);
            faceContext.addMessage(null, message);
            faceContext.getExternalContext().getFlash().setKeepMessages(true);
            return beanIndex.VER_PUBLICACIONES;
        }else{
            httpServletRequest.getSession().setAttribute("sesionLibro", libro.getIdLibro());
            definirActividad();
            if(actualizar){
                return beanIndex.DETALLES_MI_LIBRO;
            }else{
                return beanIndex.DETALLES_LIBRO;
            }
        }
    }
    

//    public String getTipoSeleccionado(){
//        definirActividad();
//        if(tipo == null){
//            return "No se ha seleccionado tipo.";
//        }else{
//            return tipo.toString();
//        }
//    }
//    
//    public String getAreaSeleccionada(){
//        definirActividad();
//        if(area == null){
//            return "No se ha seleccionado area.";
//        }else{
//            return area.toString();
//        }
//    }
    
//    public void listenerArea(ValueChangeEvent e){
//        Area aux;
//        try{
//            String a = e.getNewValue().toString();
//            for(int i = 0; i < areas.size(); i++){
//                aux = areas.get(id);
//                if(aux.getSArea().equals(a)){
//                    area = aux;
//                }
//            }
//        }catch(Exception ex){
//        
//        }
//    }
//    
//    public void listenerTipo(ValueChangeEvent e){
//        Tipo aux;
//        try{
//            String a = e.getNewValue().toString();
//            for(int i = 0; i < tipos.size(); i++){
//                aux = tipos.get(id);
//                if(aux.getSTipo().equals(a)){
//                    tipo = aux;
//                }
//            }
//        }catch(Exception ex){
//        
//        }
//    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAnho() {
        return anho;
    }

    public void setAnho(String anho) {
        this.anho = anho;
    }

    public int getEdicion() {
        return edicion;
    }

    public void setEdicion(int edicion) {
        this.edicion = edicion;
    }

    public String getsEdicion() {
        return sEdicion;
    }

    public void setsEdicion(String sEdicion) {
        this.sEdicion = sEdicion;
    }

    public int getEvalCont() {
        return evalCont;
    }

    public void setEvalCont(int evalCont) {
        this.evalCont = evalCont;
    }

    public String getsEvalCont() {
        return sEvalCont;
    }

    public void setsEvalCont(String sEvalCont) {
        this.sEvalCont = sEvalCont;
    }

    public int getEvalRedac() {
        return evalRedac;
    }

    public void setEvalRedac(int evalRedac) {
        this.evalRedac = evalRedac;
    }

    public String getsEvalRedac() {
        return sEvalRedac;
    }

    public void setsEvalRedac(String sEvalRedac) {
        this.sEvalRedac = sEvalRedac;
    }

    public String getResenha() {
        return resenha;
    }

    public void setResenha(String reseña) {
        this.resenha = reseña;
    }

    public String getPalabrasClaves() {
        return palabrasClaves;
    }

    public void setPalabrasClaves(String palabrasClaves) {
        this.palabrasClaves = palabrasClaves;
    }

    public FacesMessage getMessage() {
        return message;
    }

    public void setMessage(FacesMessage message) {
        this.message = message;
    }

    public int getIdA() {
        return idA;
    }

    public void setIdA(int idA) {
        this.idA = idA;
    }
    
}
