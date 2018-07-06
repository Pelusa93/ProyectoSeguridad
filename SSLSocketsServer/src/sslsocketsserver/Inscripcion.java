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
public class Inscripcion {
 

    static String DireccionArchivoUsuarios = "src//sslsocketsserver//usuarios.jason";
    static ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
    static Security seguridad = Security.get_singlInstance();
    
    public static String registro(Usuario c){
        String respuesta;
        c.setStatus("activo");
        System.out.println("ENTRO A REGSITRO");
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
                            
                            pass = seguridad.decryptPassword(pass);
                            
                            System.out.println("PASS "+pass);
   
                            //Lista de la base de datos usuarios
                            Usuario aux = new Usuario(nombre,pass,estatus,null);
                            if (!(usuarios.contains(aux))){
                                  usuarios.add(aux);
                                  
                                  System.out.println("Leyendo lista");
                            }                            
                        } // End for
 
                    } catch (IOException e) {
                        respuesta = "NO GUARDÖ IO";
                        e.printStackTrace();
                        
                    } finally {
                        try {
                            if (br != null)br.close();
                        } catch (IOException ex) {
                            respuesta = "NO GUARDÖ BR";
                            ex.printStackTrace();
                            
                        }
                    }
                } // end if else existencia del archivo
            
                // Se agrega el nuevo usuario a la base de datos de usuarios si
                // no existe
                if (!(usuarios.contains(c))){
                      usuarios.add(c);
                      System.out.println("Agrega");
                      respuesta = "INSCRITO EXITOSO";
                }else {
                    respuesta = "ERROR: YA SE ENCUENTRA INSCRITO";
                } 
 
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
            
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(resultado);
            bw.close();
 
            } catch (IOException e) {
			e.printStackTrace();
                        respuesta = "NO GUARDÖ";
            }
        return respuesta;       
    }       
}
