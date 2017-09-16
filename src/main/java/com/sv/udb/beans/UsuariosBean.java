/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.beans;

import com.sv.udb.controladores.UsuariosFacadeLocal;
import com.sv.udb.modelos.Usuarios;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author bernardo
 */
@Named(value = "usuariosBean")
@ViewScoped
public class UsuariosBean implements Serializable {

    @EJB
    private UsuariosFacadeLocal usuariosFacade;
    
    private boolean guardando;
    private Usuarios objeUsua;
    private List<Usuarios> listUsua;
    
    /**
     * Creates a new instance of UsuariosBean
     */
    public UsuariosBean() {
    }

    public UsuariosFacadeLocal getUsuariosFacade() {
        return usuariosFacade;
    }

    public void setUsuariosFacade(UsuariosFacadeLocal usuariosFacade) {
        this.usuariosFacade = usuariosFacade;
    }

    public boolean isGuardando() {
        return guardando;
    }

    public void setGuardando(boolean guardando) {
        this.guardando = guardando;
    }

    public Usuarios getObjeUsua() {
        return objeUsua;
    }

    public void setObjeUsua(Usuarios objeUsua) {
        this.objeUsua = objeUsua;
    }

    public List<Usuarios> getListUsua() {
        return listUsua;
    }

    @PostConstruct
    public void init()
    {
        this.objeUsua = new Usuarios();
        this.listUsua = this.usuariosFacade.findAll();
    }
    
}
