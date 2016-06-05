/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import modelo.Intercambio;

/**
 *
 * @author luis
 */
public class IntercambioDao {
    private final DAO<Intercambio> dao;
    
    public IntercambioDao() {
        dao = new DAO("Intercambio", "id_intercambio");
    }
    
    public void insertar (Intercambio obj){
        try {
            dao.insertar(obj);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void actualizar (Intercambio obj){
        try {
            dao.actualizar(obj);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void borrar (Intercambio obj){
        try {
            dao.borrar(obj);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Intercambio obtenerPorID(int id){
        Intercambio obj;
        try{
            obj = dao.obtenerPorID(id);
        }catch(Exception e){
            throw e;
        }
        return obj;
    }
    
    public List<Intercambio> obtenerPorLibro(int id){
        List<Intercambio> list = null;
        try{
            list = dao.buscarPorAtributo("id_libro", "" + id);
            if(list == null){
                return null;
            }else{
                return list;
            }
        }catch(Exception e){
            throw e;
        }
    }
    
    public Intercambio obtenerPorPrestamo(String[] a, String[] b){
        List<Intercambio> list = null;
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
    
    public List<Intercambio> obtenerPorPrestamo(int id){
        List<Intercambio> list = null;
        try{
            list = dao.buscarPorAtributo("id_prestamo", "" + id);
        }catch(Exception e){
            throw e;
        }
        if(list != null){
            return list;
        }else{
            return null;
        }
        
    }
    
    public List<Intercambio> getAll(){
        List<Intercambio> list = null;
        try{
            list = dao.getAll();
        }catch(Exception e){
            throw e;
        }
        return list;
    }
}
