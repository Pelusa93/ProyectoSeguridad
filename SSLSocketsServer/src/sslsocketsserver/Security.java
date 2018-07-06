/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sslsocketsserver;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author karol
 */
public final class Security {

    private static Security _singlInstance;

    /**
     * Fabrica de la clase Seguridad.
     * @return Devuelve la instanciacion de la clase.
     */
    public static Security get_singlInstance(){
        if (_singlInstance==null)
            _singlInstance = new Security();
        return _singlInstance;
    }


    private static String DEFAULT_ENCODING1="UTF-8";
    /**
     * Funcion encargada de realizar la encriptación de un password
     * @param password El password a ser encriptado
     * @return String encriptado con BASE64
     */
    public static String encryptPassword(String password) {
        //Instanciamos un encriptador BASE64
        BASE64Encoder enc = new BASE64Encoder();
        try {
            //Utilizando la codificacion por defecto (UTF-8) encriptamos el string
            return enc.encode(password.getBytes(DEFAULT_ENCODING1));
        } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            return null;
        }
    }

    /**
     * Funcion encargada de realizar la desencriptacion de un password
     * @param password El password a desencriptar
     * @return String con el contenido original antes de ser encriptado
     */
    public static String decryptPassword(String password) {
        //Instanciamos un decodificador de BASE64
        BASE64Decoder dec = new BASE64Decoder();
        try {
            //Realizamos la decodificacion mediante el proceso inverso de la encriptacion
            return new String(dec.decodeBuffer(password), DEFAULT_ENCODING1);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

