package com.camlait.global.erp.domain.entrepot;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.joda.time.DateTime;

import com.camlait.global.erp.domain.Entite;
import com.camlait.global.erp.domain.config.ClePrimaires;

@Entity
public class Magasin extends Entite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int magasinId;

    @Column(unique = true, nullable = false)
    private String codeMagasin;

    private String descriptionMagasin;

    @ManyToOne
    @JoinColumn(name = ClePrimaires.ENTREPOT_ID)
    private Entrepot entrepot;

    private DateTime dateDeCreation;

    private DateTime derniereMiseAJour;

    public int getMagasinId() {
        return magasinId;
    }

    public void setMagasinId(int magasinId) {
        this.magasinId = magasinId;
    }

    public String getCodeMagasin() {
        return codeMagasin;
    }

    public void setCodeMagasin(String codeMagasin) {
        this.codeMagasin = codeMagasin;
    }

    public String getDescriptionMagasin() {
        return descriptionMagasin;
    }

    public void setDescriptionMagasin(String descriptionMagasin) {
        this.descriptionMagasin = descriptionMagasin;
    }

    public Entrepot getEntrepot() {
        return entrepot;
    }

    public void setEntrepot(Entrepot entrepot) {
        this.entrepot = entrepot;
    }

    public DateTime getDateDeCreation() {
        return dateDeCreation;
    }

    public void setDateDeCreation(DateTime dateDeCreation) {
        this.dateDeCreation = dateDeCreation;
    }

    public DateTime getDerniereMiseAJour() {
        return derniereMiseAJour;
    }

    public void setDerniereMiseAJour(DateTime derniereMiseAJour) {
        this.derniereMiseAJour = derniereMiseAJour;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codeMagasin == null) ? 0 : codeMagasin.hashCode());
        result = prime * result + magasinId;
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
        Magasin other = (Magasin) obj;
        if (codeMagasin == null) {
            if (other.codeMagasin != null)
                return false;
        }
        else if (!codeMagasin.equals(other.codeMagasin))
            return false;
        if (magasinId != other.magasinId)
            return false;
        return true;
    }

    public Magasin() {

    }
}
