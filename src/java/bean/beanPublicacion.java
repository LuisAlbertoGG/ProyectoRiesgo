
package bean;

import dao.LibroDao;
import dao.UsuarioDao;
import java.util.LinkedList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import modelo.Libro;
import modelo.Usuario;
import sun.font.TrueTypeFont;

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
    
    public beanPublicacion() {
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        dao = new LibroDao();
        daoP = new UsuarioDao();
        sEdicion = EDICION_INVALIDA;
        sEvalCont = EVALCONT_INVALIDA;
        sEvalRedac = EVALREDAC_INVALIDA;
        id = (int)httpServletRequest.getSession().getAttribute("id");
        definirActividad();
    }
    
    private void definirActividad(){
        int idA;
        try{
            if(libro == null){
                idA = (int)httpServletRequest.getSession().getAttribute("sesionActividad");
                libro = dao.obtenerPorID(idA);
                titulo = libro.getLTitulo();
                autor = libro.getLAutor();
                editorial = libro.getLEditorial();
                isbn = libro.getLIsbn();
                anho = libro.getLAnho();
                sEdicion = ""+edicion;
                sEvalCont = ""+evalCont;
                sEvalRedac = ""+evalRedac;
                resenha = libro.getLResehna();
                palabrasClaves = libro.getLPablasClave();
            }
        }catch(Exception e){
        
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
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getLocalizedMessage(), null);
            faceContext.addMessage(null, message);
            faceContext.getExternalContext().getFlash().setKeepMessages(true);
            return beanIndex.PUBLICAR();
        }
    }
    
//    public String actualizarPublicacion(){
//        String error;
//        Usuario profesor;
//        error = checkCampos(false);
//        try{
//            if(error.equals("")){
//                profesor = daoP.obtenerPorID(id);
//                libro.setICupomaximo(cupoMaximo);
//                libro.setSDescripciom(descripcion);
//                if(area != null){
//                    actividad.setArea(area);
//                }
//                if(tipo != null){
//                    actividad.setTipo(tipo);
//                }
//                actividad.setProfesor(profesor);
//                dao.actualizar(actividad);
//                message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Actividad actualizada exitosamente.", null);
//                faceContext.addMessage(null, message);
//                faceContext.getExternalContext().getFlash().setKeepMessages(true);
//                return beanIndex.ACTUALIZAR_PUBLICACION;
//            }else{
//                message = new FacesMessage(FacesMessage.SEVERITY_ERROR,error, null);
//                faceContext.addMessage(null, message);
//                faceContext.getExternalContext().getFlash().setKeepMessages(true);
//                return beanIndex.ACTUALIZAR_PUBLICACION;
//            }
//        }catch(Exception e){
//            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getLocalizedMessage(), null);
//            faceContext.addMessage(null, message);
//            faceContext.getExternalContext().getFlash().setKeepMessages(true);
//            return beanIndex.ACTUALIZAR_PUBLICACION;
//        }
//    }
//    
//    public String borrarPublicacion(){
//        try{
//            dao.borrar(actividad);
//            message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Actividad borrada exitosamente.", null);
//            faceContext.addMessage(null, message);
//            faceContext.getExternalContext().getFlash().setKeepMessages(true);
//            return beanIndex.BORRAR_PUBLICACION;
//        }catch(Exception e){
//            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getLocalizedMessage(), null);
//            faceContext.addMessage(null, message);
//            faceContext.getExternalContext().getFlash().setKeepMessages(true);
//            return beanIndex.BORRAR_PUBLICACION;
//        }
//    }
    
    public List<Libro> mostrarMisPublicaciones(){
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
            resultado = dao.obtenerLista();
        }catch(Exception e){
            resultado = new LinkedList<>();
        }
        return resultado;
    }

//    private List<Area> mostrarAreas(){
//        List<Area> list;
//        try{
//            list = daoA.obtenerLista();
//        }catch(Exception e){
//            list = new LinkedList<>();
//        }
//        return list;
//    }
    
//    private List<Tipo> mostrarTipos(){
//        List<Tipo> list;
//        try{
//            list = daoT.obtenerLista();
//        }catch(Exception e){
//            list = new LinkedList<>();
//        }
//        return list;
//    }

    
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
    
//    public String definirActividad(Actividad actividad,boolean actualizar){
//        if(actividad == null){
//            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Actividad invalida.", null);
//            faceContext.addMessage(null, message);
//            faceContext.getExternalContext().getFlash().setKeepMessages(true);
//            return beanIndex.MIS_ACTIVIDADES;
//        }else{
//            httpServletRequest.getSession().setAttribute("sesionActividad", actividad.getIdActividad());
//            if(actualizar){
//                return beanIndex.ACTUALIZAR_PUBLICACION;
//            }else{
//                return beanIndex.BORRAR_PUBLICACION;
//            }
//        }
//    }
    
    public Libro getActividad() {
        return libro;
    }

    public void setActividad(Libro libro) {
        this.libro = libro;
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
//    
//    public Area getArea() {
//        return area;
//    }
//
//    public void setArea(Area area) {
//        if(area != null){
//            this.area = area;
//        }
//    }
//
//    public Tipo getTipo() {
//        return tipo;
//    }
//
//    public void setTipo(Tipo tipo) {
//        if(tipo != null){
//            this.tipo = tipo;
//        }
//    }
//
//    public int getCupoMaximo() {
//        return cupoMaximo;
//    }
//
//    public void setCupoMaximo(int cupoMaximo) {
//        this.cupoMaximo = cupoMaximo;
//    }
//
//    public String getDescripcion() {
//        return descripcion;
//    }
//
//    public void setDescripcion(String descripcion) {
//        this.descripcion = descripcion;
//    }
//
//    public String getCupo() {
//        return cupo;
//    }
//
//    public void setCupo(String cupo) {
//        this.cupo = cupo;
//    }
//
//    public String getMensajeCupo() {
//        return mensajeCupo;
//    }
//
//    public void setMensajeCupo(String mensajeCupo) {
//        this.mensajeCupo = mensajeCupo;
//    }
//
//    public List<Area> getAreas() {
//        return areas;
//    }
//
//    public void setAreas(List<Area> areas) {
//        this.areas = areas;
//    }
//
//    public List<Tipo> getTipos() {
//        return tipos;
//    }
//
//    public void setTipos(List<Tipo> tipos) {
//        this.tipos = tipos;
//    }
//    
//    public int disponibles(int id){
//        List<Solicitud> resultado;
//        int respuesta;
//        resultado = daoS.obtenerPorActividad(id);
//        if(resultado == null){
//            respuesta = 0;
//        }else{
//            
//            respuesta = resultado.size();
//        }
//        
//        return respuesta;
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
    
}
