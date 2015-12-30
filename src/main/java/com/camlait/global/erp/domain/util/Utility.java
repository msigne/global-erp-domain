package com.camlait.global.erp.domain.util;

import com.camlait.global.erp.domain.Entite;
import com.camlait.global.erp.domain.bmq.Bmq;
import com.camlait.global.erp.domain.document.Document;
import com.camlait.global.erp.domain.document.commerciaux.vente.DocumentDeVente;
import com.camlait.global.erp.domain.document.commerciaux.vente.FactureClient;
import com.camlait.global.erp.domain.document.commerciaux.vente.FactureClientComptant;
import com.camlait.global.erp.domain.document.commerciaux.vente.FactureMarge;
import com.camlait.global.erp.domain.document.stock.entree.BonDeRetour;
import com.camlait.global.erp.domain.document.stock.entree.BonEntree;
import com.camlait.global.erp.domain.document.stock.entree.DocumentEntree;
import com.camlait.global.erp.domain.document.stock.sortie.Animation;
import com.camlait.global.erp.domain.document.stock.sortie.Avarie;
import com.camlait.global.erp.domain.document.stock.sortie.BonDeSortie;
import com.camlait.global.erp.domain.document.stock.sortie.DocumentDeSortie;
import com.camlait.global.erp.domain.document.stock.sortie.Don;
import com.camlait.global.erp.domain.document.stock.sortie.Echantillon;
import com.camlait.global.erp.domain.entrepot.Entrepot;
import com.camlait.global.erp.domain.entrepot.Magasin;
import com.camlait.global.erp.domain.entrepot.MagasinMobile;
import com.camlait.global.erp.domain.enumeration.AutreEnum;
import com.camlait.global.erp.domain.enumeration.EnumTypeEntite;
import com.camlait.global.erp.domain.enumeration.Portee;
import com.camlait.global.erp.domain.enumeration.TypeDocuments;
import com.camlait.global.erp.domain.enumeration.TypePartenaire;
import com.camlait.global.erp.domain.inventaire.Inventaire;
import com.camlait.global.erp.domain.organisation.Centre;
import com.camlait.global.erp.domain.organisation.Localisation;
import com.camlait.global.erp.domain.organisation.Region;
import com.camlait.global.erp.domain.organisation.Secteur;
import com.camlait.global.erp.domain.partenaire.Caissier;
import com.camlait.global.erp.domain.partenaire.Client;
import com.camlait.global.erp.domain.partenaire.ClientAmarge;
import com.camlait.global.erp.domain.partenaire.ClientComptant;
import com.camlait.global.erp.domain.partenaire.Employe;
import com.camlait.global.erp.domain.partenaire.Magasinier;
import com.camlait.global.erp.domain.partenaire.Partenaire;
import com.camlait.global.erp.domain.partenaire.Vendeur;
import com.camlait.global.erp.domain.produit.CategorieProduit;

public final class Utility {
    
    public static boolean isFactureClient(Document document) {
        return document instanceof FactureClient;
    }
    
    public static boolean isFactureComptant(Document document) {
        return document instanceof FactureClientComptant;
    }
    
    public static boolean isFactureMarge(Document document) {
        return document instanceof FactureMarge;
    }
    
    public static boolean isDocumentDeVente(Document document) {
        return document instanceof DocumentDeVente;
    }
    
    public static boolean isDetail(CategorieProduit categorie) {
        return categorie.getPortee() == Portee.DETAIL;
    }
    
    public static boolean isTotal(CategorieProduit categorie) {
        return categorie.getPortee() == Portee.TOTAL;
    }
    
    public static EnumTypeEntite obtenirPrefixe(Entite entite) {
        if (entite instanceof Document) {
            if (entite instanceof DocumentDeVente) {
                if (entite instanceof FactureClient)
                    return TypeDocuments.FACTURE_CLIENT;
                if (entite instanceof FactureClientComptant)
                    return TypeDocuments.FACTURE_COMPTANT;
            }
            
            if (entite instanceof DocumentDeSortie) {
                if (entite instanceof BonDeSortie)
                    return TypeDocuments.BON_DE_SORTIE;
                if (entite instanceof Echantillon)
                    return TypeDocuments.ECHANTILLON;
                if (entite instanceof Don)
                    return TypeDocuments.DON;
                if (entite instanceof Avarie)
                    return TypeDocuments.AVARIE;
                if (entite instanceof Animation)
                    return TypeDocuments.ANIMATION;
            }
            
            if (entite instanceof DocumentEntree) {
                if (entite instanceof BonEntree)
                    return TypeDocuments.BON_ENTREE;
                if (entite instanceof BonDeRetour)
                    return TypeDocuments.BON_DE_RETOUR;
            }
        }
        
        if (entite instanceof Partenaire) {
            if (entite instanceof Employe)
                return TypePartenaire.EMPLOYE;
            if (entite instanceof Client)
                return TypePartenaire.CLIENT;
            if (entite instanceof ClientAmarge)
                return TypePartenaire.CLIENT_A_MARGE;
            if (entite instanceof ClientComptant)
                return TypePartenaire.CLIENT_COMPTANT;
            if (entite instanceof Magasinier)
                return TypePartenaire.MAGASINIER;
            if (entite instanceof Vendeur)
                return TypePartenaire.VENDEUR;
            if (entite instanceof Caissier)
                return TypePartenaire.CAISSIER;
        }
        if (entite instanceof Bmq)
            return AutreEnum.BMQ;
        if (entite instanceof Localisation) {
            if (entite instanceof Centre) {
                return AutreEnum.CENTRE;
            } else if (entite instanceof Region) {
                return AutreEnum.REGION;
            } else if (entite instanceof Secteur) {
                return AutreEnum.SECTEUR;
            } else
                return AutreEnum.ZONE;
        }
        if (entite instanceof Inventaire)
            return AutreEnum.INVENTAIRE;
        else if (entite instanceof Magasin) {
            if (entite instanceof MagasinMobile) {
                return AutreEnum.MAGASIN_MOBILE;
            } else
                return AutreEnum.MAGASIN_FIXE;
        } else if (entite instanceof Entrepot) {
            return AutreEnum.ENTREPOT;
        } else
            throw new IllegalArgumentException("L'entit้ " + entite.getClass().getName() + " n'existe pas");
    }
    
    public static Long convertToLong(String num) {
        try {
            return Long.parseLong(num);
        } catch (Exception e) {
            throw e;
        }
    }
}
