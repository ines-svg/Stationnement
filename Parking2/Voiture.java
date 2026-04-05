/**
 * @auteur Mehrdad Sabetzadeh, Université d'Ottawa
 */
public class Voiture {

	/**
	 * Variable d’instance pour stocker le type de la voiture
	 */
	private TypeVoiture type;

	/**
	 * Variable d’instance pour stocker le numéro de plaque de la voiture
	 */
	private String numeroPlaque;

	/**
	 * @return le type de cette voiture
	 */
	public TypeVoiture getType() {
		// ÉCRIVEZ VOTRE CODE ICI !

		return type;
	}

	/**
	 * Définit le type de la voiture
	 * 
	 * @param type est le type de la voiture
	 */
	public void setType(TypeVoiture type) {
		// ÉCRIVEZ VOTRE CODE ICI !
		this.type=type;
	}

	/**
	 * @return le numéro de plaque
	 */
	public String getNumeroPlaque() {
		// ÉCRIVEZ VOTRE CODE ICI !
		return numeroPlaque;
	}

	/**
	 * Définit le numéro de plaque de la voiture
	 * 
	 * @param numeroPlaque est le numéro de plaque de la voiture
	 */
	public void setNumeroPlaque(String numeroPlaque) {
		// ÉCRIVEZ VOTRE CODE ICI !
		this.numeroPlaque = numeroPlaque;
	}

	/**
	 * Constructeur de la classe Voiture
	 * 
	 * @param type         est le type de la voiture
	 * @param numeroPlaque est le numéro de plaque de la voiture
	 */
	public Voiture(TypeVoiture type, String numeroPlaque) {
		// ÉCRIVEZ VOTRE CODE ICI !
		this.type=type;
		this.numeroPlaque= numeroPlaque;
	}

	/**
	 * Retourne une représentation sous forme de chaîne de caractères de la voiture
	 */
	public String toString() {
		// NOTE : L’implémentation de cette méthode est complète. Vous n’avez PAS besoin de
		// la modifier pour le devoir.
		return Utilitaire.getLibelleParTypeVoiture(type) + '(' + numeroPlaque + ')';
	}
}
