/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sslsocketsserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JApplet;

/**
 *
 * @author karol
 */
public class FormularioCertificado extends JApplet {

    BufferedReader brRequest = new BufferedReader(new InputStreamReader(System.in));     
        
            
    /**
     * Initialization method that will be called after the applet is loaded into
     * the browser.
     */
    public void init() {
        try {
            // TODO start asynchronous download of heavy resources
            System.out.println("Nombre Completo");
            String nombre =  brRequest.readLine();
            System.out.println("Empresa");
            String empresa =  brRequest.readLine();
            System.out.println("Ciudad");
            String ciudad =  brRequest.readLine();
            System.out.println("Pais");
            String pais =  brRequest.readLine();
        } catch (IOException ex) {
            Logger.getLogger(FormularioCertificado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // TODO overwrite start(), stop() and destroy() methods
}
