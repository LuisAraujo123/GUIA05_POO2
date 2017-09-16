/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.beans;

import com.sv.udb.controladores.LibrosFacadeLocal;
import com.sv.udb.modelos.Libros;
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
@Named(value = "librosBean")
@ViewScoped
public class LibrosBean implements Serializable {

    @EJB
    private LibrosFacadeLocal librosFacade;
    
    private boolean guardando;
    private Libros objeLibr;
    private List<Libros> listLibr;
    private List<Libros> listLibrFilt;
    
    /**
     * Creates a new instance of LibrosBean
     */
    public LibrosBean() {
    }

    public LibrosFacadeLocal getLibrosFacade() {
        return librosFacade;
    }

    public void setLibrosFacade(LibrosFacadeLocal librosFacade) {
        this.librosFacade = librosFacade;
    }

    public boolean isGuardando() {
        return guardando;
    }

    public void setGuardando(boolean guardando) {
        this.guardando = guardando;
    }

    public Libros getObjeLibr() {
        return objeLibr;
    }

    public void setObjeLibr(Libros objeLibr) {
        this.objeLibr = objeLibr;
    }

    public List<Libros> getListLibr() {
        return listLibr;
    }

    public List<Libros> getListLibrFilt() {
        return listLibrFilt;
    }
    
    @PostConstruct
    public void init()
    {
        this.objeLibr = new Libros();
        this.listLibr = this.librosFacade.findAll();
        this.listLibrFilt = this.librosFacade.findByEstaLibr();
    }
    
    public void refreshList()
    {
        this.listLibrFilt = this.librosFacade.findByEstaLibr();
    }
    
    public void nuev()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        this.objeLibr = new Libros();
        this.guardando = true;
        ctx.execute("$('#modaFormLibr').modal('show')");
    }
    
    public void cons()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        Map<String, String> mapaPrms = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int codi = Integer.parseInt(mapaPrms.get("codiLibr"));
        this.objeLibr = this.librosFacade.find(codi);
        this.guardando = false;
        ctx.execute("$('#modaFormLibr').modal('show')");
    }
    
    public void guar()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            this.librosFacade.create(this.objeLibr);
            this.listLibr.add(this.objeLibr);
            this.objeLibr = new Libros();
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos guardados')");
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al guardar ')");
        }
        finally
        {
            
        }
    }
    
    public void edit()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            this.librosFacade.edit(this.objeLibr);
            this.setItem(this.objeLibr);
            this.objeLibr = new Libros();
            this.guardando = true;
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos modificados')");
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'No se modificó')");
        }
        finally
        {
            
        }
    }
    
    public void elim()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            this.librosFacade.remove(this.objeLibr);
            this.listLibr.remove(this.objeLibr);
            this.objeLibr = new Libros();
            this.guardando = true;
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos eliminados')");
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'No se eliminó')");
        }
        finally
        {
            
        }
    }
    
    private void setItem(Libros item)
    {
        int itemIndex = this.listLibr.indexOf(item);
            if (itemIndex != -1) {
            this.listLibr.set(itemIndex, item);
        }
    }
}
