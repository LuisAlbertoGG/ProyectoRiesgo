
package dao;

import java.util.LinkedList;
import java.util.List;
import modelo.Usuario;


/**
 * WRAPPER para DAO en la tabla alumno.
 * @author esmeralda
 */
public class UsuarioDao {

    private DAO<Usuario> dao;
    
    public UsuarioDao() {
        dao = new DAO("Usuario", "id_usuario");
    }
    
    public void insertar (Usuario obj){
        try {
            dao.insertar(obj);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void actualizar (Usuario obj){
        try {
            dao.actualizar(obj);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void borrar (Usuario obj){
        try {
            dao.borrar(obj);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Usuario obtenerPorID(int id){
        Usuario obj;
        try{
            obj = dao.obtenerPorID(id);
        }catch(Exception e){
            throw e;
        }
        return obj;
    }
    
    public Usuario validarCorreo(Usuario obj){
        String[] atributos, valores;
        List<Usuario> list;
        Usuario aux = null;
        atributos = new String[1];
        valores = new String[1];
        atributos[0] = "u_correo";
        valores[0] = obj.getUCorreo();
      
        try {
            list = dao.buscar(atributos, valores);
            if (list != null) {
                if (!list.isEmpty()) {
                    aux = list.get(0);
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return aux;
    
    }
    
    public boolean existeCorreo(String correo){
        String[] atributos,valores;
        boolean existe;
        atributos = new String[1];
        valores = new String[1];
        atributos[0] = "U_Correo";
        valores[0] = correo;
        try{
            existe = dao.verificarExistencia(atributos, valores);
        }catch(Exception e){
            throw e;
        }
        return existe;
    }
    
    public Usuario verificarUsuario(Usuario obj){
        String[] atributos,valores;
        List<Usuario> list;
        Usuario aux = null;
        atributos = new String[2];
        valores = new String[2];
        atributos[0] = "U_Correo";
        valores[0] = obj.getUCorreo();
        atributos[1] = "U_Contrasenha";
        valores[1] = obj.getUContrasenha();
        try{
            list = dao.buscar(atributos, valores);
            if(list != null){
                if(!list.isEmpty()){
                    aux = list.get(0);
                }
            }
        }catch(Exception e){
            throw e;
        }
        return aux;
    }
    
//    public List<Alumno> recuperarNombres(List<Solicitud> lista){
//        List<Alumno> resultado = new LinkedList<Alumno>();
//        Solicitud temporal = new Solicitud();
//        Alumno temp = new Alumno();
//        int i = 0;
//        int tamaño = lista.size();
////        resultado = aux(lista, temporal, temp);
//        while(i < tamaño && lista.get(i)!= null ){
//            temporal = lista.get(i);
//            temp = temporal.getAlumno();
//            System.out.println(temp.getSNombre());
//            resultado.add(temp);
//            System.out.println("Entre "+ i + " veces");
//            i++;
//        }
//        return resultado;
//    }
//    
//    public List<Alumno> aux(List<Solicitud> list, Solicitud temporal, Alumno tempo){
//        System.out.println("Entre aquí otra vez");
//        List<Alumno> resultado = new LinkedList<>();
//        int i = 0;
//        int tamaño = list.size();
//        while(i<tamaño){
//            temporal = list.get(i);
//            //list.add(i, temporal);
//            tempo = temporal.getAlumno();
//            System.out.println(tempo.getSNombre());
//            resultado.add(tempo);
//            System.out.println("Entre "+ i + " veces");
//            i++;
//        }
//        
//        return resultado;
//    }
}