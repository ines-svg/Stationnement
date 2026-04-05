/**
 * @auteur Mehrdad Sabetzadeh, Université d'Ottawa
 *
 */
public class Emplacement {
	private Voiture voiture;
	private int horodatage;

	public Voiture getVoiture() {

		// ÉCRIVEZ VOTRE CODE ICI !
		return voiture;

		//fini
	}

	public void setVoiture(Voiture voiture) {
	
		// ÉCRIVEZ VOTRE CODE ICI !
		this.voiture=voiture;
		//fini
	
	}

	public int getHorodatage() {
	
		// ÉCRIVEZ VOTRE CODE ICI !
		
		return horodatage;
		//fini

	}

	public void setHorodatage(int horodatage) {

		// ÉCRIVEZ VOTRE CODE ICI !
		this.horodatage=horodatage;
		//fini
		
	}

	public Emplacement(Voiture voiture, int horodatage) {

		// ÉCRIVEZ VOTRE CODE ICI !
		this.voiture= voiture;
		this.horodatage= horodatage;
		//fini
		
	}

	/**
	 * Retourne une représentation sous forme de chaîne de caractères de l’emplacement
	 * Cette méthode est complète; vous n’avez pas besoin de la modifier.
	 */
	public String toString() {
		return voiture + ", horodatage : " + horodatage;
	}
}
