package fr.uvsq.cprog.collex;

import java.util.Objects;

/**
 * Classe représentant un item du DNS.
 */
public final class DnsItem {
  private final AdresseIP adresse;
  private final NomMachine nom;

  /**
   * Constructeur de la classe DnsItem.
   */
  public DnsItem(AdresseIP adresse, NomMachine nom) {
    if (adresse == null || nom == null) {
      throw new IllegalArgumentException("Adresse et nom ne peuvent pas être nuls");
    }
    this.adresse = adresse;
    this.nom = nom;
  }

  public AdresseIP getAdresse() {
    return adresse;
  }

  public NomMachine getNom() {
    return nom;
  }

  @Override
  public String toString() {
    return adresse.toString() + " " + nom.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DnsItem)) {
      return false;
    }
    DnsItem dnsItem = (DnsItem) o;
    return Objects.equals(adresse, dnsItem.adresse) && Objects.equals(nom, dnsItem.nom);
  }

  @Override
  public int hashCode() {
    return Objects.hash(adresse, nom);
  }
}
