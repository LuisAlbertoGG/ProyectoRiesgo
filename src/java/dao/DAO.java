
package dao;

import java.util.List;
import logica.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Clase WRAPPED para la implementacion de los dao.
 * @author luis
 * @param <E> - Tabla asociada.
 */
public class DAO<E> {

    private  Session session;
    private String tabla;
    private String id;
    
    private DAO(){
        session = HibernateUtil.getSessionFactory().openSession();
    }
    
    public DAO(String tabla, String id) {
        this();
        this.tabla = tabla;
        this.id = id;
    }
    
    public List<E> getAll(){
        List<E> l = null;
        String hql;
        Query query; 
        try{
            session.getTransaction().begin();
            hql = "from " + tabla;
            query = session.createQuery(hql);
            if(!query.list().isEmpty()){
                l = (List<E>)query.list();
            }  
            session.getTransaction().commit();
        }catch(Exception e){
            session.getTransaction().rollback();
            throw e;
        }
        return l;
    }
    
    public List<E> buscar(String[] atributos,String[] valores){
        List<E> l = null;
        String hql;
        Query query; 
        try{
            session.getTransaction().begin();
            hql = "from " + tabla + " where " + atributos[0] + " = '" + valores[0] + "'";
            for(int i = 1; i < atributos.length; i++){
                hql +=  " and " + atributos[i] + "= '" + valores[i] + "'";
            }
            query = session.createQuery(hql);
            if(!query.list().isEmpty()){
                l = (List<E>)query.list();
            }  
            session.getTransaction().commit();
        }catch(Exception e){
            session.getTransaction().rollback();
            throw e;
        }
        return l;
    }
    
    public List<E> buscarPorAtributo(String atributo,String valor){
        List<E> list = null;
        String[] atributos,valores;
        atributos = new String[1];
        valores = new String[1];
        atributos[0] = atributo;
        valores[0] = valor;
        try{
            list = this.buscar(atributos, valores);
        }catch(Exception e){
            throw e;
        }
        return list;
    }
    
    public boolean verificarExistencia(String atributos[], String valores[]){ 
        List<E> l = buscar(atributos, valores);
        if(l == null){
            return false;
        }
        return !l.isEmpty();
    }
    
    public void insertar (E obj){
        try {
            session.getTransaction().begin();
            session.save(obj);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            throw e;
        }
    }
    
    public void actualizar (E obj){
        try {
            session.getTransaction().begin();
            session.update(obj);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }
    
    public void borrar (E obj){
        try {
            session.getTransaction().begin();
            session.delete(obj);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }
    
    public E obtenerPorID(int id){
        E obj = null;
        List<E> list = null;
        String[] atributos = new String[1];
        String[] valores = new String[1];
        atributos[0] = this.id;
        valores[0] = "" + id;
        try{
            list = buscar(atributos, valores);
            if(!list.isEmpty()){
                obj = list.get(0);
            }
        }catch(Exception e){
            throw e;
        }
        return obj;
    }
    
}
