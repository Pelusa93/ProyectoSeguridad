/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sslsocketsserver;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
/**
 *
 * @author karol
 */
class LoginServer {
    static String DireccionArchivoUsuarios = "src//sslsocketsserver//usuarios.jason";
    static ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
    static Security seguridad = Security.get_singlInstance();

    public String login(String username, String password){
        String respuesta = null;
        System.out.println("ENTRO A LOGIN");
        System.out.println("u: " + username);
        System.out.println("c: " + password);
        try {
 
		File file = new File(DireccionArchivoUsuarios);
 
		// Si el archivo no existe, lo crea
		if (!file.exists()) {
                    file.createNewFile();
		}else{ // Si el archivo existe lo decodifico
                    BufferedReader br = null;
                    String jsonADecodificar = "";
                    try {
                        String sCurrentLine;
                        br = new BufferedReader(new FileReader(DireccionArchivoUsuarios));                                 
 
                        while ((sCurrentLine = br.readLine()) != null) {
                            jsonADecodificar = sCurrentLine;
                        }
                        
                        JSONObject obj = (JSONObject)JSONValue.parse(jsonADecodificar);
                        JSONArray dropped = (JSONArray)obj.get("Usuarios");
 
                        for (Object object : dropped) {
            
                            JSONObject auxiliar = (JSONObject) object;
            
                            JSONObject usuario =  (JSONObject) auxiliar.get("Usuario");
          
                            String nombre = (String) usuario.get("nombre");
                            
                            String pass = (String) usuario.get("pass");
                            
                            String estatus = (String) usuario.get("estatus");
                            
                            //String decpass = seguridad.decryptPassword(pass);
                            
                            //System.out.println("PASS "+decpass);
   
                            
                            //Lista de la base de datos usuarios
                            System.out.println("Leyendo lista");
                            Usuario aux = new Usuario(nombre,pass,estatus,null);
                            if (!(usuarios.contains(aux))){
                                  usuarios.add(aux);
                                  System.out.println("AUX " + aux.getNombre());
                                  System.out.println("AUX " + aux.getPass());
                                  System.out.println("AUX " + aux.getStatus());
                                  
                            }                            
                        } // End for
                        
                        Usuario aux = new Usuario(null,null,null,usuarios);
                        aux.imprimirLista();
 
                    } catch (IOException e) {
                        e.printStackTrace();
                        return respuesta = "ERROR DE ARCHIVO";
                        
                    } finally {
                        try {
                            if (br != null)br.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            return respuesta = "ERROR DE ARCHIVO";
                            
                            
                        }
                    }
                } // end if else existencia del archivo
            
                // Analizando la lista de Usuarios regitrados
               for (Usuario nodoActual : usuarios)
               {
                   if (nodoActual.getNombre().compareTo(username)==0){
                       System.out.println("Encontró el username");
                       if (nodoActual._pass.compareTo(seguridad.encryptPassword(password))==0){
                           System.out.println("Clave Correcta");
                           if (nodoActual._status.compareTo("activo")==0){
                               System.out.println("Estatus Activo");
                               return respuesta="ACCEDIÓ";
                           }else{
                               System.out.println("Estatus bloqueado");
                               return respuesta="USUARIO SE ENCUENTRA BLOQUEDADO. COMUNICAR ADMIN";
                           }
                       }else{
                           System.out.println("Username bueno clave inválida");
                           return respuesta="CONTRASEÑA INVÁLIDA";
                        }
                   }
                   else{
                       System.out.println("No Encontró");
                       respuesta = "Usuario Inválido";
                   }                            
               }
                    
            /*    if (!(usuarios.contains(c))){
                      usuarios.add(c);
                      System.out.println("Agrega");
                      respuesta = "INSCRITO EXITOSO";
                }else {
                    respuesta = "ERROR: YA SE ENCUENTRA INSCRITO";
                } 
*/
            } catch (IOException e) {
			e.printStackTrace();
                        return respuesta = "ERROR DE ARCHIVO";
            }
        return respuesta="NO ENTRÓ EN NADA"; 
    }
  
}

