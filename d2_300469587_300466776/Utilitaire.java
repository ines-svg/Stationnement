/**
 * Ceci est une classe utilitaire pour simplifier l’implémentation du devoir A1.
 * Vous n’êtes PAS censé modifier la classe Utilitaire. Vous êtes toutefois
 * encouragé à étudier les méthodes de cette classe à des fins de pratique.
 * 
 * @auteur Mehrdad Sabetzadeh, Université d'Ottawa
 */
public class Utilitaire {

	public static TypeVoiture getTypeVoitureParLibelle(String libelle) {

		if (libelle.equals("E"))
			return TypeVoiture.ELECTRIQUE;

		else if (libelle.equals("P"))
			return TypeVoiture.PETITE;

		else if (libelle.equals("R"))
			return TypeVoiture.REGULIERE;

		else if (libelle.equals("G"))
			return TypeVoiture.GRANDE;

		else
			return TypeVoiture.NA;
	}

	public static String getLibelleParTypeVoiture(TypeVoiture type) {

		if (type == TypeVoiture.ELECTRIQUE)
			return "E";

		else if (type == TypeVoiture.PETITE)
			return "P";

		else if (type == TypeVoiture.REGULIERE)
			return "R";

		else if (type == TypeVoiture.GRANDE)
			return "G";

		else
			return "N";
	}
}
