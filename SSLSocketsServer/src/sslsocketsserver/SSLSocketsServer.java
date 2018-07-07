/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sslsocketsserver;

import java.io.IOException;
import java.net.ServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

/**
 *
 * @author karol
 */
public class SSLSocketsServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, Exception {
        // TODO code application logic here
        
        //Indico donde se encuntran los certificados antes de iniciar el socket
        System.setProperty("javax.net.ssl.keyStoreType", "JKS");
        System.setProperty("javax.net.ssl.keyStore", "src//sslsocketsserver//certs//server//certjess//serverkey.jks");
        System.setProperty("javax.net.ssl.keyStorePassword","PreguntA1234");
        System.setProperty("javax.net.ssl.trustStore", "src//sslsocketsserver//certs//server//certjess//serverTrustedCerts.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "PreguntA1234");
        
        
       /* SSLServerSocketFactory serverFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        ServerSocket serverSocket = serverFactory.createServerSocket(8888);*/
        RunServer server = new RunServer();
        server.runServer();
    }
    
}
