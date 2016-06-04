/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import modelo.Libro;

/**
 *
 * @author luis
 */
public class LibroDao {
    private final DAO<Libro> dao;
    
    public LibroDao() {
        dao = new DAO("Libro", "id_Libro");
    }
    
    public void insertar (Libro obj){
        try {
            dao.insertar(obj);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void actualizar (Libro obj){
        try {
            dao.actualizar(obj);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void borrar (Libro obj){
        try {
            dao.borrar(obj);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Libro obtenerPorID(int id){
        Libro obj;
        try{
            obj = dao.obtenerPorID(id);
        }catch(Exception e){
            throw e;
        }
        return obj;
    }
    
    public List<Libro> obtenerPorUsuario(int id){
        List<Libro> list = null;
        try{
            list = dao.buscarPorAtributo("id_usuario", "" + id);
        }catch(Exception e){
            throw e;
        }
        return list;
    }
    
    public List<Libro> obtenerPorAutor(int id){
        List<Libro> list = null;
        try{
            list = dao.buscarPorAtributo("LAutor", "" + id);
        }catch(Exception e){
            throw e;
        }
        return list;
    }
    
    public List<Libro> obtenerPorIsbn(int id){
        List<Libro> list = null;
        try{
            list = dao.buscarPorAtributo("LIsbn", "" + id);
        }catch(Exception e){
            throw e;
        }
        return list;
    }
    
    public List<Libro> obtenerPorAnho(int id){
        List<Libro> list = null;
        try{
            list = dao.buscarPorAtributo("LAnho", "" + id);
        }catch(Exception e){
            throw e;
        }
        return list;
    }
    
    public List<Libro> obtenerLista(){
        List<Libro> list = null;
        try{
            list = dao.getAll();
        }catch(Exception e){
            throw e;
        }
        return list;
    }
}
