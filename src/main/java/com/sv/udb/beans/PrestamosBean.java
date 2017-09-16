/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.beans;

import com.sv.udb.controladores.LibrosFacadeLocal;
import com.sv.udb.controladores.PrestamosFacadeLocal;
import com.sv.udb.modelos.Prestamos;
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
@Named(value = "prestamosBean")
@ViewScoped
public class PrestamosBean implements Serializable {

    @EJB
    private LibrosFacadeLocal librosFacade;

    @EJB
    private PrestamosFacadeLocal prestamosFacade;
    
    private boolean guardando;
    private Prestamos objePres;
    private List<Prestamos> listPres;
    
    /**
     * Creates a new instance of PrestamosBean
     */
    public PrestamosBean() {
    }

    public PrestamosFacadeLocal getPrestamosFacade() {
        return prestamosFacade;
    }

    public void setPrestamosFacade(PrestamosFacadeLocal prestamosFacade) {
        this.prestamosFacade = prestamosFacade;
    }

    public boolean isGuardando() {
        return guardando;
    }

    public void setGuardando(boolean guardando) {
        this.guardando = guardando;
    }

    public Prestamos getObjePres() {
        return objePres;
    }

    public void setObjePres(Prestamos objePres) {
        this.objePres = objePres;
    }

    public List<Prestamos> getListPres() {
        return listPres;
    }
    
    @PostConstruct
    public void init()
    {
        this.objePres = new Prestamos();
        this.listPres = this.prestamosFacade.findAll();
    }
    
    public void nuev()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        this.objePres = new Prestamos();
        this.guardando = true;
        ctx.execute("$('#modaFormPres').modal('show')");
    }
    
    public void cons()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        Map<String, String> mapaPrms = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int codi = Integer.parseInt(mapaPrms.get("codiPres"));
        this.objePres = this.prestamosFacade.find(codi);
        this.guardando = false;
        ctx.execute("$('#modaFormPres').modal('show')");
    }
    
    public void guar()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            this.objePres.setFechPres(new java.util.Date());
            this.prestamosFacade.create(this.objePres);
            this.listPres.add(this.objePres);
            this.objePres.getCodiLibr().setEstaLibr(0);
            this.librosFacade.edit(this.objePres.getCodiLibr());
            Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
            LibrosBean librosBean = (LibrosBean) viewMap.get("librosBean");
            librosBean.refreshList();
            this.objePres = new Prestamos();
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
            this.objePres.setFechDevo(new java.util.Date());
            this.prestamosFacade.edit(this.objePres);
            this.setItem(this.objePres);
            this.objePres.getCodiLibr().setEstaLibr(1);
            this.librosFacade.edit(this.objePres.getCodiLibr());
            Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
            LibrosBean librosBean = (LibrosBean) viewMap.get("librosBean");
            librosBean.refreshList();
            this.objePres = new Prestamos();
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
            this.prestamosFacade.remove(this.objePres);
            this.listPres.remove(this.objePres);
            this.objePres = new Prestamos();
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
    
    private void setItem(Prestamos item)
    {
        int itemIndex = this.listPres.indexOf(item);
            if (itemIndex != -1) {
            this.listPres.set(itemIndex, item);
        }
    }
}

