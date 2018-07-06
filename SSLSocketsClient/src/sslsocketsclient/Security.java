package sslsocketsclient;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author karol
 */
public final class Security {
    private static Security security;
    
    public static Security getInstance(){
        if (security==null)
            security = new Security();
        return security;
    }
    
    
    public boolean validarPassSize(String pass){
        if (pass.length()<8)
            return false;
        else 
            return true;        
    }
    
    public int[] validarMay(String password){
        int [] resp = new int[2]; 
         //1 mayuscula, 1 minuscula, 1 numero minimo
       char clave;
       byte  contNumero = 0, contLetraMay = 0, contLetraMin=0;
       for (byte i = 0; i < password.length(); i++) {
                clave = password.charAt(i);
               String passValue = String.valueOf(clave);
                if (passValue.matches("[A-Z]")) {
                    contLetraMay++;
                } else if (passValue.matches("[a-z]")) {
                    contLetraMin++;
                } else if (passValue.matches("[0-9]")) {
                    contNumero++;
                }
        }
    /*    System.out.println("Cantidad de letras mayusculas:"+contLetraMay);
        System.out.println("Cantidad de letras minusculas:"+contLetraMin);
        System.out.println("Cantidad de numeros:"+contNumero);*/
        
        if (contLetraMay < 1)
            resp[0]=0;
        else
            resp[0]=1;
        
        if (contNumero < 1)
            resp[1]=0;
        else
            resp[1]=1;
        return resp;
    }
}
