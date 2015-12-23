package com.camlait.global.erp.domain.operation.reglement.lettrage;

import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.camlait.global.erp.domain.Entite;
import com.camlait.global.erp.domain.config.GlobalAppConstants;
import com.camlait.global.erp.domain.document.vente.FactureClient;
import com.camlait.global.erp.domain.operation.reglement.Reglement;
import com.camlait.global.erp.domain.pk.PKFactureReglement;

@Entity
public class FactureReglement extends Entite {
    
    @EmbeddedId
    private PKFactureReglement factureReglementId;
    
    @ManyToOne
    @JoinColumn(name = GlobalAppConstants.AUTO_ID_NOTATION, updatable = false, insertable = false)
    private FactureClient facture;
    
    @ManyToOne
    @JoinColumn(name = GlobalAppConstants.AUTO_ID_NOTATION, insertable = false, updatable = false)
    private Reglement reglement;
    
    private Date dateDeVentilation;
    
    private Date dateDeCreation;
    
    private Date derniereMiseAJour;
    
    public PKFactureReglement getFactureReglementId() {
        return factureReglementId;
    }
    
    public void setFactureReglementId(PKFactureReglement factureReglementId) {
        this.factureReglementId = factureReglementId;
    }
    
    public FactureClient getFacture() {
        return facture;
    }
    
    public void setFacture(FactureClient facture) {
        this.facture = facture;
    }
    
    public Reglement getReglement() {
        return reglement;
    }
    
    public void setReglement(Reglement reglement) {
        this.reglement = reglement;
    }
    
    public Date getDateDeVentilation() {
        return dateDeVentilation;
    }
    
    public void setDateDeVentilation(Date dateDeVentilation) {
        this.dateDeVentilation = dateDeVentilation;
    }
    
    public Date getDateDeCreation() {
        return dateDeCreation;
    }
    
    public void setDateDeCreation(Date dateDeCreation) {
        this.dateDeCreation = dateDeCreation;
    }
    
    public Date getDerniereMiseAJour() {
        return derniereMiseAJour;
    }
    
    public void setDerniereMiseAJour(Date derniereMiseAJour) {
        this.derniereMiseAJour = derniereMiseAJour;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((factureReglementId == null) ? 0 : factureReglementId.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FactureReglement other = (FactureReglement) obj;
        if (factureReglementId == null) {
            if (other.factureReglementId != null)
                return false;
        } else if (!factureReglementId.equals(other.factureReglementId))
            return false;
        return true;
    }
    
}
