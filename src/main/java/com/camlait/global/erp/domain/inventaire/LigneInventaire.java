package com.camlait.global.erp.domain.inventaire;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.camlait.global.erp.domain.Entite;
import com.camlait.global.erp.domain.produit.Produit;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class LigneInventaire extends Entite {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long ligneInventaireId;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "inventaireId")
	private Inventaire inventaire;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "produitId")
	private Produit produit;

	@Column(name = "quantiteReelle")
	private Long quantiteReelle;

	@Column(name = "quantiteAjustee")
	private Long quantiteAjustee;

	@Column(name = "prixUnitaireReelle")
	private double prixUnitaireReelle;

	@Column(name = "prixUnitaireAjustee")
	private double prixUnitaireAjustee;

	@Column(name = "dateDeCreation")
	private Date dateDeCreation;

	@Column(name = "derniereMiseAJour")
	private Date derniereMiseAJour;

	public Long getLigneInventaireId() {
		return ligneInventaireId;
	}

	public void setLigneInventaireId(Long ligneInventaireId) {
		this.ligneInventaireId = ligneInventaireId;
	}

	public Inventaire getInventaire() {
		return inventaire;
	}

	public void setInventaire(Inventaire inventaire) {
		this.inventaire = inventaire;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public Long getQuantiteReelle() {
		return quantiteReelle;
	}

	public void setQuantiteReelle(Long quantiteReelle) {
		this.quantiteReelle = quantiteReelle;
	}

	public Long getQuantiteAjustee() {
		return quantiteAjustee;
	}

	public void setQuantiteAjustee(Long quantiteAjustee) {
		this.quantiteAjustee = quantiteAjustee;
	}

	public double getPrixUnitaireReelle() {
		return prixUnitaireReelle;
	}

	public void setPrixUnitaireReelle(double prixUnitaireReelle) {
		this.prixUnitaireReelle = prixUnitaireReelle;
	}

	public double getPrixUnitaireAjustee() {
		return prixUnitaireAjustee;
	}

	public void setPrixUnitaireAjustee(double prixUnitaireAjustee) {
		this.prixUnitaireAjustee = prixUnitaireAjustee;
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
		result = prime * result + ((ligneInventaireId == null) ? 0 : ligneInventaireId.hashCode());
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
		LigneInventaire other = (LigneInventaire) obj;
		if (ligneInventaireId == null) {
			if (other.ligneInventaireId != null)
				return false;
		} else if (!ligneInventaireId.equals(other.ligneInventaireId))
			return false;
		return true;
	}

	public LigneInventaire() {
		setDateDeCreation(new Date());
		setDerniereMiseAJour(new Date());
	}

}
