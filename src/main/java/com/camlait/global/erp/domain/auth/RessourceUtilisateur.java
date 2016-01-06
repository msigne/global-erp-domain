package com.camlait.global.erp.domain.auth;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.camlait.global.erp.domain.Entite;
import com.camlait.global.erp.domain.enumeration.Etat;

@Entity
public class RessourceUtilisateur extends Entite {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long ressourceUtilisateurId;

	@ManyToOne
	@JoinColumn(name = "utilisateurId")
	private Utilisateur utilisateur;

	@ManyToOne
	@JoinColumn(name = "ressourceId")
	private Ressource ressource;

	@Enumerated(EnumType.STRING)
	private Etat etat;

	public Long getRessourceUtilisateurId() {
		return ressourceUtilisateurId;
	}

	public void setRessourceUtilisateurId(Long menuUtilisateurId) {
		this.ressourceUtilisateurId = menuUtilisateurId;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Ressource getRessource() {
		return ressource;
	}

	public void setRessource(Ressource ressource) {
		this.ressource = ressource;
	}

	public Etat getEtat() {
		return etat;
	}

	public void setEtat(Etat etat) {
		this.etat = etat;
	}

	public RessourceUtilisateur(Utilisateur utilisateur, Ressource ressource, Etat etat) {
		super();
		this.utilisateur = utilisateur;
		this.ressource = ressource;
		this.etat = etat;
	}

	public RessourceUtilisateur() {
		super();
	}
}