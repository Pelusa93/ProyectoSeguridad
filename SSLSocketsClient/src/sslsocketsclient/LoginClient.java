/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sslsocketsclient;

/**
 *
 * 
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * Manda por socket ssl al servidor la informacion recogida para que inicia sesion 
 * @author elberg
 */

class LoginClient {
  public LoginClient(String nombre, String pass, String respuesta) {
    
      try {
          SSLSocketsClient ssc = new SSLSocketsClient();
          Usuario u = new Usuario(nombre);
          if (ssc.contador_estatus < 2 ){
            SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket socket = (SSLSocket) socketFactory.createSocket(SSLSocketsClient.IP, 7070);
            PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            output.println(nombre);      
            output.println(pass);
            output.println("2");
            output.println("null");
            output.flush();
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = input.readLine();
            System.out.println(response);
            respuesta=response;
            if (respuesta.contains("Respuesta Servidor: CONTRASEÑA INVÁLIDA")){
                ssc.contador_estatus++;
                System.out.println("Intento de loggeo num:"+ssc.contador_estatus+"\n");
            }
            output.close();
            input.close();
            socket.close();
          }
          else{
          System.out.println("Excedio el numero de intentos, desbloquee antes de iniciar sesion\n");
            new CambiarStatusClient(u,"bloqueado");
          }
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }/* finally {
      System.exit(0);
    }*/
  }
}