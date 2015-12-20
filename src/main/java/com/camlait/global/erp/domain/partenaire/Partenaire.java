package com.camlait.global.erp.domain.partenaire;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.joda.time.DateTime;

import com.camlait.global.erp.domain.Entite;
import com.camlait.global.erp.domain.config.ClePrimaires;
import com.camlait.global.erp.domain.localisation.Centre;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Partenaire extends Entite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int partenaireId;

    @Column(nullable = false, unique = true)
    private String codePartenaire;

    private String adresse;

    private String telephone;

    private DateTime dateDeCreation;

    private DateTime derniereMiseAJour;

    @ManyToOne
    @JoinColumn(name = ClePrimaires.LOCALISATION_ID)
    private Centre centre;

    public int getPartenaireId() {
        return partenaireId;
    }

    public void setPartenaireId(int partenaireId) {
        this.partenaireId = partenaireId;
    }

    public String getCodePartenaire() {
        return codePartenaire;
    }

    public void setCodePartenaire(String codePartenaire) {
        this.codePartenaire = codePartenaire;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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

    public Centre getCentre() {
        return centre;
    }

    public void setCentre(Centre centre) {
        this.centre = centre;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codePartenaire == null) ? 0 : codePartenaire.hashCode());
        result = prime * result + partenaireId;
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
        Partenaire other = (Partenaire) obj;
        if (codePartenaire == null) {
            if (other.codePartenaire != null)
                return false;
        }
        else if (!codePartenaire.equals(other.codePartenaire))
            return false;
        if (partenaireId != other.partenaireId)
            return false;
        return true;
    }

    public Partenaire() {

    }
}
