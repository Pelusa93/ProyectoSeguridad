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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
 
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
 
/**
 * Esta clase genera el captcha
 * @author elberg
 */
 
public class CaptchaGenerator {
 private static JFrame capFrame = new JFrame();
 private static JLabel capIm = new JLabel();
    
    
 // Defining Character Array you can change accordingly 
 private static final char[] chars = { '1', 'A', 'a', 'B', 'b', 'C',
 'c', '2', 'D', 'd', 'E', 'e', 'F', 'f', '3', 'G', 'g', 'H', 'h',
 'I', 'i', 'J', 'j', 'K', 'k', 'L', 'l', '4', 'M', 'm', 'N', 'n',
 'O', 'o', '5', 'P', 'p', 'Q', 'q', 'R', 'r', 'S', 's', 'T', 't',
 '6', '7', 'U', 'u', 'V', 'v', 'U', 'u', 'W', 'w', '8', 'X', 'x',
 'Y', 'y', 'Z', 'z', '9' };
 
 private static final Color[] colors = {Color.red, Color.black, Color.blue};
 
 // Method for generating the Captcha Code
 public static String generateCaptchaText() {
 
 String randomStrValue = "";
 
 final int LENGTH = 6; // Character Length
 
 StringBuffer sb = new StringBuffer();
 
 int index = 0;
 
 for (int i = 0; i < LENGTH; i++) {
 // Getting Random Number with in range(ie: 60 total character present)
 index = (int) (Math.random() * (chars.length - 1));
 sb.append(chars[index]); // Appending the character using StringBuffer
 }
 
 randomStrValue = String.valueOf(sb); // Assigning the Generated Password to String variable
 
 return randomStrValue;
 }
 
 /**
  * Metodo que se usa para renderar el captcha
  * @param value 
  */
 public static void renderImage(String value) {
 
 if(value != null && !value.isEmpty()) {
 
 BufferedImage image = null;
 
 try {
 
 image = ImageIO.read(new File("src//sslsocketsclient//background.jpg")); // Background Image
 
 } catch (IOException e) {
 
 System.out.println(e.getMessage());
 e.printStackTrace();
 }
 
     Graphics g = image.getGraphics();
    
     g.setFont(g.getFont().deriveFont(30f));
     
     char[] c = value.toCharArray();
     
     int x = 20;
     int y = 50;
 
     for(int i=0;i<c.length;i++) {
      x = x+ 30;     
      g.setColor(colors[(int)(Math.random() * 3)]);
      g.drawString(String.valueOf(c[i]), x, y);
     }
    
     g.dispose();
     
     try {
 
      ImageIO.write(image, "png", new File("Output.png")); // Output Image
      System.out.println("Introduzca el Texto de la Imamagen Captcha luego presione Aceptar y Enter");
      //capFrame.setVisible(true);
      //capFrame.setSize(250, 80);
      capIm.setIcon(new ImageIcon(image));
      capFrame.add(capIm);
      JOptionPane.showMessageDialog(null, capIm, "Captcha", JOptionPane.PLAIN_MESSAGE, null);
 } catch (IOException e) {
 
 System.out.println(e.getMessage());
 e.printStackTrace();
 }
 }
 }
 
 
}