
package dao;

import java.util.List;
import modelo.Prestamo;

/**
 * WRAPPER para DAO en la tabla solicitud.
 * @author luis
 */
public class PrestamoDao {

    private final DAO<Prestamo> dao;
    
    public PrestamoDao() {
        dao = new DAO("Prestamo", "id_prestamo");
    }
    
    public void insertar (Prestamo obj){
        try {
            dao.insertar(obj);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void actualizar (Prestamo obj){
        try {
            dao.actualizar(obj);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void borrar (Prestamo obj){
        try {
            dao.borrar(obj);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Prestamo obtenerPorID(int id){
        Prestamo obj;
        try{
            obj = dao.obtenerPorID(id);
        }catch(Exception e){
            throw e;
        }
        return obj;
    }
    
    public List<Prestamo> obtenerPorEstado(boolean b){
        List<Prestamo> list = null;
        try{
            list = dao.buscarPorAtributo("p_activo", "" + b);
        }catch(Exception e){
            throw e;
        }
        return list;
    }
    
    public List<Prestamo> obtenerPorLibro(int id){
        List<Prestamo> list;
        try{
            list = dao.buscarPorAtributo("id_libro",""+id);
        }catch(Exception e){
            throw e;
        }
        return list;
    }
    
    public Prestamo obtenerPorPrestamo(String[] a, String[] b){
        List<Prestamo> list = null;
        try{
            list = dao.buscar(a,b);
        }catch(Exception e){
            throw e;
        }
        if(list != null){
            return list.get(0);
        }else{
            return null;
        }
    }
    
    public List<Prestamo> obtenerPorPrestamo1(String[] a, String[] b){
        List<Prestamo> list = null;
        try{
            list = dao.buscar(a,b);
        }catch(Exception e){
            throw e;
        }
        if(list != null){
            return list;
        }else{
            return null;
        }
    }
    
    
    
    public List<Prestamo> obtenerPorPrestador(int id){
        List<Prestamo> list = null;
        try{
            list = dao.buscarPorAtributo("id_usuario_prestador", "" + id);
        }catch(Exception e){
            throw e;
        }
        return list;
    }
    
    public List<Prestamo> obtenerPorSolicitante(int id){
        List<Prestamo> list = null;
        try{
            list = dao.buscarPorAtributo("id_usuario_solicitante", "" + id);
        }catch(Exception e){
            throw e;
        }
        return list;
    }
    
    public List<Prestamo> getAll(){
        List<Prestamo> list = null;
        try{
            list = dao.getAll();
        }catch(Exception e){
            throw e;
        }
        return list;
    }
    
}