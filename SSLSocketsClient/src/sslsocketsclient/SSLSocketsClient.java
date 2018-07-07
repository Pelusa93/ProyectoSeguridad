/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sslsocketsclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.net.ssl.SSLSocketFactory;
import javax.servlet.http.HttpServletRequest;


/**
 *
 * @author karol
 */
public class SSLSocketsClient {
    public String status_actulizado ;
    public static String IP ="localhost";
    public static int contador_estatus;
    private static String nombre;
    private static String pass;
    private static Usuario u;
    private static Security sc = Security.getInstance();
    private static String text;
    private static CaptchaGenerator cap = new CaptchaGenerator();
    private static String respuesta="";

    /**
     * @param args the command line arguments
     */
    
    
    
    public SSLSocketsClient() {
  }

    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        //Indico donde se encuntran los certificados antes de iniciar el socket
        contador_estatus =0;
        System.setProperty("javax.net.ssl.keyStoreType", "JKS");
        System.setProperty("javax.net.ssl.keyStore", "src//sslsocketsclient//certs//client//certjess//clientkey.jks");
        System.setProperty("javax.net.ssl.keyStorePassword","PreguntA1234");
        System.setProperty("javax.net.ssl.trustStore", "src//sslsocketsclient//certs//client//certjess//clientTrustedCerts.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "PreguntA1234");
        
        
        /*SSLSocketFactory clientFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        Socket client = clientFactory.createSocket("192.168.4.42", 7070);
        */String opc="0";        
        
        while (opc.compareTo("5")!=0){
           
            System.out.println("1 REGISTRARSE");
            System.out.println("2 INICIAR SESIÓN");
            System.out.println("3 SOLICITAR DESBLOQUEO");
            System.out.println("4 SOLICITAR CERTIFICADO");
            System.out.println("5 SALIR");
            
            BufferedReader brRequest = new BufferedReader(new InputStreamReader(System.in));
            
            opc = brRequest.readLine();
            
            switch (opc){
                case "1":
                    System.out.println("Nombre");
                    nombre = brRequest.readLine();
                    System.out.println("PASS");
                    pass = brRequest.readLine();
                    
                    while (!sc.validarPassSize(pass)
                            || (sc.validarMay(pass)[0]==0)
                            || (sc.validarMay(pass)[1]==0)){
                        
                        if (!sc.validarPassSize(pass))
                            System.out.println("PASS DEBE SER MAYOR A 8 CARACTERES");
                        if (sc.validarMay(pass)[0]==0)
                            System.out.println("PASS DE TENER UNA MAYÚSCULA AL MENOS");
                        if (sc.validarMay(pass)[1]==0)
                            System.out.println("PASS DE TENER UN NÚMERO AL MENOS");
                        System.out.println("ESCRIBA NUEVAMENTE PASS");
                        pass = brRequest.readLine();
                    }
                    
                    u = new Usuario(nombre, pass, "activo",null);
                    
                    text = cap.generateCaptchaText();
                    cap.renderImage(text);
                   
                    String captcha = brRequest.readLine();
            
                    int cont=0;
                    while(captcha.compareTo(text)!=0){
                        cont++;
                        text = cap.generateCaptchaText();
                        cap.renderImage(text);
                        captcha = brRequest.readLine();
                        if (cont==3){
                            System.err.println("LO HA INTENTADO DEMASIADAS VECES");
                            System.err.println("SALIENDO DE LA APLICACIÓN");
                            System.exit(0);
                        }
                    }
                    
                    new InscripcionClient(u);
                    break;
                    
                case "2":
                    System.out.println("Nombre");
                    nombre = brRequest.readLine();
                    System.out.println("PASS");
                    pass = brRequest.readLine();                                
                    new LoginClient(nombre,pass,respuesta);
                    
                    System.out.println("RRR "+ respuesta);
                    //if (respuesta.compareTo("ACCEDIÓ")==0)
                    u = new Usuario(nombre, pass, null,null);
                    break;
                    
                case "3":
                    // if (respuesta.compareTo("ACCEDIÓ")==0){
                    Usuario usu=new Usuario(null,null);
                    System.out.println("Nombre del usuario a desbloquear");
                    nombre = brRequest.readLine();
                    usu.setNombre(nombre);
                    System.out.println("Password anterior");
                    pass = brRequest.readLine();
                    usu.setPass(pass);
                    new CambiarStatusClient(usu,"activo");
                    //}
                    //else
                    //  System.out.println("Debe estar iniciada la sesión");                        
                    
                    break;
                    
                case "4":
                    new CrearCert(u.getNombre(),u._pass);
                    break;
                    
                case "5":
                    System.exit(0);
                    break;
            }
        }
    }
}
