package com.camlait.global.erp.domain.partenaire;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.camlait.global.erp.domain.Entite;
import com.camlait.global.erp.domain.document.commerciaux.vente.DocumentDeVente;
import com.camlait.global.erp.domain.enumeration.AutreEnum;
import com.camlait.global.erp.domain.immobilisation.PartenaireImmobilisation;
import com.camlait.global.erp.domain.localisation.Centre;
import com.camlait.global.erp.domain.operation.Operation;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Partenaire extends Entite {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long paretenaireId;

	@Column(name = "codePartenaire", nullable = false, unique = true)
	private String codePartenaire;

	private String adresse;

	private String telephone;

	private Date dateDeCreation;

	private Date derniereMiseAJour;

	@Enumerated(EnumType.STRING)
	private AutreEnum typePartenaire;

	@ManyToOne
	@JoinColumn(name = "centreId")
	private Centre centre;

	@OneToMany(mappedBy = "client")
	private Collection<DocumentDeVente> documents;

	@OneToMany(mappedBy = "immobilisation")
	private Collection<PartenaireImmobilisation> partenaireImmobilisations;

	@OneToMany(mappedBy = "partenaire")
	private Collection<Operation> operations;

	public Long getPartenaireId() {
		return paretenaireId;
	}

	public void setPartenaireId(Long partenaireId) {
		this.paretenaireId = partenaireId;
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

	public Centre getCentre() {
		return centre;
	}

	public void setCentre(Centre centre) {
		this.centre = centre;
	}

	public Collection<DocumentDeVente> getDocuments() {
		return documents;
	}

	public void setDocuments(Collection<DocumentDeVente> documents) {
		this.documents = documents;
	}

	public Collection<PartenaireImmobilisation> getPartenaireImmobilisations() {
		return partenaireImmobilisations;
	}

	public void setPartenaireImmobilisations(Collection<PartenaireImmobilisation> partenaireImmobilisations) {
		this.partenaireImmobilisations = partenaireImmobilisations;
	}

	public Collection<Operation> getOperations() {
		return operations;
	}

	public void setOperations(Collection<Operation> operations) {
		this.operations = operations;
	}

	public Long getParetenaireId() {
		return paretenaireId;
	}

	public void setParetenaireId(Long paretenaireId) {
		this.paretenaireId = paretenaireId;
	}

	public AutreEnum getTypePartenaire() {
		return typePartenaire;
	}

	public void setTypePartenaire(AutreEnum typePartenaire) {
		this.typePartenaire = typePartenaire;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codePartenaire == null) ? 0 : codePartenaire.hashCode());
		result = prime * result + ((paretenaireId == null) ? 0 : paretenaireId.hashCode());
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
		} else if (!codePartenaire.equals(other.codePartenaire))
			return false;
		if (paretenaireId == null) {
			if (other.paretenaireId != null)
				return false;
		} else if (!paretenaireId.equals(other.paretenaireId))
			return false;
		return true;
	}

	public Partenaire() {
		setDateDeCreation(new Date());
		setDerniereMiseAJour(new Date());
	}
}
