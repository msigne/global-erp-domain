package com.camlait.global.erp.domain.auth.user;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import com.camlait.global.erp.domain.Entite;
import com.camlait.global.erp.domain.util.Utility;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.common.collect.Sets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor(suppressConstructorProperties = true)
public class Ressource extends Entite {

	@Id
	private String ressourceId;

	@Transient
	private String ressourceParentId;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "ressourceParentId")
	private Ressource ressourceParent;

	private String title;

	private Date dateDeCreation;

	private Date derniereMiseAJour;

	private String icon;

	private String sref;

	private String href;

	private Integer ordre;

	@JsonManagedReference
	@OneToMany(mappedBy = "ressourceParent", fetch = FetchType.EAGER)
	private Collection<Ressource> items = Sets.newHashSet();

	@JsonManagedReference
	@ManyToMany(mappedBy = "ressources", cascade = CascadeType.ALL)
	@JoinTable(name = "ressource_groupe", 
	joinColumns = @JoinColumn(name = "ressourceId", referencedColumnName = "groupeId"), 
	inverseJoinColumns = @JoinColumn(name = "groupeId", referencedColumnName = "ressourceeId"))
	private Collection<Groupe> groupes = Sets.newHashSet();

	@JsonManagedReference
	@ManyToMany(mappedBy = "ressources", cascade = CascadeType.ALL)
	@JoinTable(name = "ressource_utilisateur", 
	joinColumns = @JoinColumn(name = "ressourceId", referencedColumnName = "utilisateurId"), 
	inverseJoinColumns = @JoinColumn(name = "utilisateurId", referencedColumnName = "ressourceId"))
	private Collection<Utilisateur> utilisateurs = Sets.newHashSet();

	public Ressource() {
		setDateDeCreation(new Date());
		setDerniereMiseAJour(new Date());
	}

	public Ressource(String descriptionMenu) {
		this.title = descriptionMenu;
	}

	public Ressource(Collection<Groupe> groupes, Collection<Utilisateur> utilisateurs) {
		this.utilisateurs = utilisateurs;
		this.groupes = groupes;
	}

	public Ressource(String descriptionMenu, Ressource menuParent) {
		super();
		this.title = descriptionMenu;
		this.ressourceParent = menuParent;
	}

	public void setRessourceParentId() {
		setRessourceParentId(getRessourceParent().getRessourceId());
	}

	public boolean possedeDetails() {
		return (!this.getItems().isEmpty());
	}

	@PrePersist
	private void setKey() {
		setRessourceId(Utility.getUid());
	}

	@Override
	public void postConstructOperation() {
		setRessourceParentId(ressourceParent.getRessourceId());
	}
}
