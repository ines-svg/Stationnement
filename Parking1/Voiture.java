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
		return type;
	}

	/**
	 * Définit le type de la voiture
	 * 
	 * @param type est le type de la voiture
	 */

	public void setType(TypeVoiture type) {
		this.type = type;
	}

	/**
	 * @return le numéro de plaque
	 */

	public String getNumeroPlaque() {
		return numeroPlaque;
	}

	/**
	 * Définit le numéro de plaque de la voiture
	 * 
	 * @param numeroPlaque est le numéro de plaque de la voiture
	 */

	public void setNumeroPlaque(String numeroPlaque) {
		this.numeroPlaque = numeroPlaque;
	}

	/**
	 * Constructeur de la classe Voiture
	 * 
	 * @param type         est le type de la voiture
	 * @param numeroPlaque est le numéro de plaque de la voiture
	 */
	public Voiture(TypeVoiture type, String numeroPlaque) {
		this.type= type;
        this.numeroPlaque = numeroPlaque;
	}

	/**
	 * Retourne une représentation sous forme de chaîne de caractères de la voiture
	 */
	public String toString() {
		return Utilitaire.getLibelleParTypeVoiture(type) + '(' + numeroPlaque + ')';
	}
}
