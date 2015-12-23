package com.camlait.global.erp.domain.operation.caisse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.joda.time.DateTime;

import com.camlait.global.erp.domain.Entite;
import com.camlait.global.erp.domain.config.GlobalAppConstants;
import com.camlait.global.erp.domain.partenaire.Employe;

@Entity
public class Caisse extends Entite {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "codeCaisse", unique = true, nullable = false)
	private String codeCaisse;

	private String descriptionCaisse;

	@ManyToOne
	@JoinColumn(name = GlobalAppConstants.AUTO_ID_NOTATION,updatable=false,insertable=false)
	private Employe responsable;

	private DateTime dateDeCreation;

	private DateTime derniereMiseAJour;

	public Long getCaisseId() {
		return id;
	}

	public void setCaisseId(Long caisseId) {
		this.id = caisseId;
	}

	public String getCodeCaisse() {
		return codeCaisse;
	}

	public void setCodeCaisse(String codeCaisse) {
		this.codeCaisse = codeCaisse;
	}

	public String getDescriptionCaisse() {
		return descriptionCaisse;
	}

	public void setDescriptionCaisse(String descriptionCaisse) {
		this.descriptionCaisse = descriptionCaisse;
	}

	public Employe getResponsable() {
		return responsable;
	}

	public void setResponsable(Employe responsable) {
		this.responsable = responsable;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((codeCaisse == null) ? 0 : codeCaisse.hashCode());
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
		Caisse other = (Caisse) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (codeCaisse == null) {
			if (other.codeCaisse != null)
				return false;
		} else if (!codeCaisse.equals(other.codeCaisse))
			return false;
		return true;
	}

	public Caisse() {

	}
}
