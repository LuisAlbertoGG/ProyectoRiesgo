
package dao;

import java.util.LinkedList;
import java.util.List;
import modelo.Usuario;


/**
 * WRAPPER para DAO en la tabla alumno.
 * @author luis
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
        valores[1] = cifrar(obj.getUContrasenha());
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
    
    public static String cifrar(String contra){
        int longi = contra.length();
        String resultado = "";
        String[] deaqui = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","ñ","o","p","q","r","s","t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9"};
        String[] aaqui = {"z","y","x","w","v","u","t","s","r","q","p","o","ñ","n","m","l","k","j","i","h","g","f","e","d","c","b","a","9","8","7","6","5","4","3","2","1","0"};
        for(int i = 0; i<longi; i++){
            String tmp = ""+contra.charAt(i);
            for(int e = 0; e<37; e++){
                if(tmp.equals(deaqui[e])){
                    resultado = resultado+aaqui[e];
                }
            }
        }
        return resultado;
        
    }
    
}