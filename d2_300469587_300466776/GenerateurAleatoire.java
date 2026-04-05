import java.util.Random;

/**
 * @auteur Mehrdad Sabetzadeh, Université d'Ottawa
 * 
 *         L’implémentation de cette classe est complète. Vous n’avez *pas*
 *         besoin
 *         de modifier cette classe dans ce devoir.
 * 
 */

public class GenerateurAleatoire {

	/**
	 * Tableau de caractères admissibles dans les chaînes générées aléatoirement
	 * (pour les plaques)
	 */
	private static final char[] ALPHANUM = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
			'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	/**
	 * Instance de la classe Random; toutes les quantités aléatoires de ce devoir
	 * seront
	 * générées à travers cette unique instance de Random
	 */
	private static Random alea = new Random();

	/**
	 * @param probabilite est la probabilité que l’événement d’intérêt se produise
	 *                    dans une SEULE unité de temps simulée. L’unité de temps
	 *                    simulée
	 *                    dans ce devoir est une seconde. Par exemple, si cette
	 *                    méthode
	 *                    est appelée avec une probabilité de 1/100, cela signifie
	 *                    que
	 *                    l’événement d’intérêt a 1% de probabilité de se produire
	 *                    durant
	 *                    cette seconde. Cette probabilité se traduirait par un taux
	 *                    d’arrivée (probabiliste) de 3600 * 1% = 36 voitures par
	 *                    heure.
	 * @return true si l’événement d’intérêt s’est produit dans l’unité de temps
	 *         courante,
	 *         false sinon
	 */
	public static boolean evenementSurvenu(Rationnel probabilite) {
		if (probabilite.numerateur() <= 0 || probabilite.denominateur() < probabilite.numerateur()) {
			return false;
		}

		int nombre = alea.nextInt(probabilite.denominateur());

		if (nombre < probabilite.numerateur())
			return true;

		return false;

	}

	/**
	 * @param longueur est la longueur de la chaîne aléatoire à générer
	 * @return une chaîne aléatoire de la longueur spécifiée
	 */
	public static String genererChaineAleatoire(int longueur) {

		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < longueur; i++) {
			buffer.append(ALPHANUM[alea.nextInt(ALPHANUM.length)]);
		}

		return buffer.toString();
	}

	/**
	 * @return Une instance de Voiture dont le type est décidé probabilistiquement,
	 *         selon des proportions prédéfinies
	 */
	public static Voiture genererVoitureAleatoire() {
		// Codé en dur :
		// ELECTRIQUE (prob : 5%),
		// PETITE (prob : 20%),
		// GRANDE (prob : 10%),
		// REGULIERE (par défaut).

		TypeVoiture type;
		int nombre = alea.nextInt(100);

		if (nombre < 5)
			type = TypeVoiture.ELECTRIQUE;
		else if (nombre >= 5 && nombre < 25)
			type = TypeVoiture.PETITE;
		else if (nombre >= 25 && nombre < 35)
			type = TypeVoiture.GRANDE;
		else
			type = TypeVoiture.REGULIERE;

		return new Voiture(type, genererChaineAleatoire(3));
	}
}
