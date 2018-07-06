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
import static sslsocketsserver.Inscripcion.usuarios;
import static sslsocketsserver.LoginServer.DireccionArchivoUsuarios;

/**
 *
 * @author karol
 */
public class CambiarStatusServer {
    static String DireccionArchivoUsuarios = "src//sslsocketsserver//usuarios.jason";
    static ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
    static Security seguridad = Security.get_singlInstance();
    String respuesta = null;
    File f;
    private boolean cambio;
    
     public String cambiar(Usuario c, String status){         
        System.out.println("ENTRO A CAMBIAR STATUS");
        System.out.println("u: " + c.getNombre());
        System.out.println("st: " + c.getStatus());
        try {
 
		File file = new File(DireccionArchivoUsuarios);
                
		// Si el archivo no existe, lo crea
		if (!file.exists()) {
                    file.createNewFile();
                    f=file;
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
               for (int i=0; i< usuarios.size(); i++)
               {
                   if (usuarios.get(i).getNombre().compareTo(c.getNombre())==0){
                       System.out.println("Encontró el username");
                       usuarios.get(i).setStatus(status);
                       cambio = true;
                   }
                   else{
                       System.out.println("No Encontró");
                       respuesta = "Usuario Inválido";
                       cambio = false;
                   }                            
               }
               
               f=file;
               if (cambio)
                   guardarArchivo();
                    
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
     
     public void guardarArchivo(){
         try{
                          // Se procede a crear un archivo tipo JSON
                JSONObject usuarioJSONObject;
                JSONObject listUsuarioJSONObject = new JSONObject();
            
                String resultado = "";
            
                Usuario sk=null;
                for (Usuario user : usuarios) {
                
                    usuarioJSONObject = new JSONObject();
                    sk = user;
                    String encPass = seguridad.encryptPassword(sk.getPass());
                    usuarioJSONObject.put("nombre",sk.getNombre());
                    usuarioJSONObject.put("pass",encPass);
                    usuarioJSONObject.put("estatus",sk.getStatus());

                // A la lista de usuario en el array JSON se le adiciona un usuario
                // en particular
                listUsuarioJSONObject.put("Usuario", usuarioJSONObject);
            
                // Se crea un String con el formato de JSON creado
                String jsonText = JSONValue.toJSONString(listUsuarioJSONObject);
            
            /* Se verifica si habian usuarios previamente registrados para no
            /borrarlos. Solo ocurre cuando el servidor esta activo, si se cae 
            servidor se tiene que leer primero el archivo y concatenarlo */
            
                if (resultado.equals("")) {
                    resultado = "{\"Usuarios\" : ["+jsonText;
                }
                else {
                    resultado = resultado + " , "+jsonText;
                }
            }
            
            resultado = resultado + "]}";
 
            System.out.println(resultado);
            System.out.println("");
            System.out.flush();
            
            respuesta="GUARDADO";
            
            FileWriter fw = new FileWriter(f.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(resultado);
            bw.close();
         } catch (IOException e) {
			e.printStackTrace();
                        respuesta = "NO GUARDÖ";
            }
     }
}
