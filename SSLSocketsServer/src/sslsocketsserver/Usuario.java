/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sslsocketsserver;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author karol
 */
public class Usuario implements Serializable{
    String _nombre;
    String _pass;
    String _status;
    ArrayList<Usuario> _listaClientes;

    public Usuario() {        
    }

    public Usuario(String _nombre, String _pass, String _estatus,ArrayList<Usuario> _listaClientes) {
        this._nombre = _nombre;
        this._pass = _pass;
        this._status = _estatus;
        this._listaClientes = _listaClientes;
    }

    public String getNombre() {
        return _nombre;
    }

    public void setNombre(String _nombre) {
        this._nombre = _nombre;
    }

    public String getPass() {
        return _pass;
    }

    public void setPass(String _pass) {
        this._pass = _pass;
    }

    public String getStatus() {
        return _status;
    }

    public void setStatus(String _status) {
        this._status = _status;
    }

    
    
     /**
     * Retorna la lista de Clientes
     */
    public ArrayList<Usuario> getListaClientes() {
        return _listaClientes;
    }

     /**
     * Asigna la lista de Clientes
     */
    public void setListaClientes(ArrayList<Usuario> _listaClientes) {
        this._listaClientes = _listaClientes;
    }   
    
    /**
     * Imprime la lista de Clientes
     */
    public void imprimirLista(){
        for (Usuario nodoActual : this._listaClientes)
        {
            System.out.println("Nombre: " + nodoActual.getNombre());
            System.out.println("Pass: "+ nodoActual.getPass());
            System.out.println("Estatus: "+ nodoActual.getStatus());
        }
    }
}
