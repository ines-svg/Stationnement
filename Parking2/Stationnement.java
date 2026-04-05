import java.io.File;
import java.util.Scanner;

/**
 * @auteur Mehrdad Sabetzadeh, Université d'Ottawa
 */
public class Stationnement {
	/**
	 * Le délimiteur qui sépare les valeurs
	 */
	private static final String SEPARATEUR = ",";

	/**
	 * Variable d’instance pour stocker le nombre de rangées dans un stationnement
	 */
	private int nombreRangees;

	/**
	 * Variable d’instance pour stocker le nombre de places par rangée dans un stationnement
	 */
	private int nombrePlacesParRangee;

	/**
	 * Variable d’instance (tableau à deux dimensions) pour stocker la conception du stationnement
	 */
	private TypeVoiture[][] conceptionStationnement;

	/**
	 * Variable d’instance (tableau à deux dimensions) pour stocker l’information
	 * d’occupation des places dans le stationnement
	 */
	private Emplacement[][] occupation;

	/**
	 * Construit un stationnement en chargeant un fichier
	 * 
	 * @param nomFichier est le nom du fichier
	 */
	public Stationnement(String nomFichier) throws Exception {

		// ÉCRIVEZ VOTRE CODE ICI !
		if (nomFichier == null) {
			System.out.println("Le nom du fichier ne peut pas être nul.");
			return;

		}
		calculerDimensionsStationnement(nomFichier);
		conceptionStationnement = new TypeVoiture[nombreRangees][nombrePlacesParRangee];
		occupation = new Emplacement[nombreRangees][nombrePlacesParRangee];
		remplirConceptionDepuisFichier(nomFichier);
	}

	public int getNombreRangees() {
		return nombreRangees;
	}

	public int getNombrePlacesParRangee() {
		return nombrePlacesParRangee;
	}

	/**
	 * Stationne une voiture (v) à un emplacement donné (i, j) dans le stationnement.
	 * 
	 * @param i           est l’indice de la rangée de stationnement
	 * @param j           est l’indice de la place dans la rangée i
	 * @param v           est la voiture à stationner
	 * @param horodatage  est le temps (simulé) auquel la voiture est stationnée
	 */
	public void stationner(int i, int j, Voiture v, int horodatage) {

		// ÉCRIVEZ VOTRE CODE ICI !
		if (!peutStationnerA(i, j, v)) {
			System.out.println("La voiture " + v + " ne peut pas être garée à (" + i + "," + j + ")");
			return;
		}
		occupation[i][j] = new Emplacement(v, horodatage);

	}

	/**
	 * Retire la voiture stationnée à un emplacement donné (i, j) dans le stationnement
	 * 
	 * @param i est l’indice de la rangée de stationnement
	 * @param j est l’indice de la place dans la rangée i
	 * @return l’emplacement retiré; la méthode retourne null lorsque i ou j sont hors
	 *         limites, ou lorsqu’il n’y a aucune voiture stationnée à (i, j)
	 */
	public Emplacement retirer(int i, int j) {

		// ÉCRIVEZ VOTRE CODE ICI !

		if (i < 0 || i >= nombreRangees || j < 0 || j >= nombrePlacesParRangee) {
			return null;
		}


		if (occupation[i][j] == null) {
			return null;
		}

		Emplacement v = occupation[i][j];
		occupation[i][j] = null;
		return v;
		//fini

	}

	/**
	 * Retourne l’instance d’emplacement à une position donnée (i, j)
	 * 
	 * @param i est l’indice de la rangée de stationnement
	 * @param j est l’indice de la place dans la rangée i
	 * @return l’instance d’emplacement à la position (i, j)
	 */
	public Emplacement getEmplacementA(int i, int j) {

		// ÉCRIVEZ VOTRE CODE ICI !
		if (i>=nombreRangees || i<0|| j>=nombrePlacesParRangee||j<0){
			return null;
		}
		return occupation[i][j];


	}

	/**
	 * Vérifie si une voiture (ayant un certain type) est autorisée à stationner à
	 * l’emplacement (i, j)
	 *
	 * NOTE : Cette méthode est complète; vous n’avez pas besoin de la modifier.
	 * 
	 * @param i est l’indice de la rangée de stationnement
	 * @param j est l’indice de la place dans la rangée i
	 * @return true si la voiture v peut stationner à (i, j), false sinon
	 */
	public boolean peutStationnerA(int i, int j, Voiture v) {

		// ÉCRIVEZ VOTRE CODE ICI !
		if (v == null) return false;


		if (i < 0 || i >= nombreRangees || j < 0 || j >= nombrePlacesParRangee) {
			return false;
		}


		TypeVoiture typeEmplacement = conceptionStationnement[i][j];
		if (typeEmplacement == TypeVoiture.NA) {
			return false;
		}
		if (occupation[i][j] != null) {
			return false;
		}

		TypeVoiture typeVoiture = v.getType();
		if (typeVoiture == TypeVoiture.PETITE) {
			return (typeEmplacement == TypeVoiture.PETITE
					|| typeEmplacement == TypeVoiture.REGULIERE
					|| typeEmplacement == TypeVoiture.GRANDE);
		}

		if (typeVoiture == TypeVoiture.REGULIERE) {
			return (typeEmplacement == TypeVoiture.REGULIERE
					|| typeEmplacement == TypeVoiture.GRANDE);
		}

		if (typeVoiture == TypeVoiture.GRANDE) {
			return (typeEmplacement == TypeVoiture.GRANDE);
		}
		if (typeVoiture == TypeVoiture.ELECTRIQUE) {
			return true;
		}

		return false;

		

	}

	/**
	 * Tente de stationner une voiture dans le stationnement. Le stationnement est
	 * réussi si un emplacement approprié est disponible. Si un tel emplacement est
	 * trouvé (n’importe où dans le stationnement), la voiture est stationnée à cet
	 * emplacement avec l’horodatage indiqué et la méthode retourne « true ».
	 * Si aucun emplacement approprié n’est trouvé, aucune action n’est effectuée
	 * et la méthode retourne simplement « false ».
	 * 
	 * @param v           est la voiture à stationner
	 * @param horodatage  est le temps de simulation auquel le stationnement est tenté
	 * @return true si la voiture est stationnée avec succès quelque part dans le
	 *         stationnement, false sinon
	 */
	public boolean tenterStationnement(Voiture v, int horodatage) {

		// ÉCRIVEZ VOTRE CODE ICI !
		for (int i=0; i<nombreRangees; i++){
			for (int j=0; j<nombrePlacesParRangee; j++){
				if (peutStationnerA(i,j,v)){
					stationner(i,j,v,horodatage);
					return true;
				}
			}
		}
		return false;

	}

	/**
	 * @return la capacité totale du stationnement en excluant les emplacements qui
	 *         ne peuvent pas être utilisés pour stationner
	 *         (c.-à-d. les emplacements associés à TypeVoiture.NA)
	 */
	public int getCapaciteTotale() {

		// ÉCRIVEZ VOTRE CODE ICI !

		int capacite = 0;

		for (int i = 0; i < nombreRangees; i++) {
			for (int j = 0; j < nombrePlacesParRangee; j++) {

				if (conceptionStationnement[i][j] != TypeVoiture.NA) {
					capacite++;
				}
			}
		}

		return capacite;
	
	}

	/**
	 * @return l’occupation totale du stationnement
	 */
	public int getOccupationTotale() {

		// ÉCRIVEZ VOTRE CODE ICI !
		int total = 0;

		for (int i = 0; i < nombreRangees; i++) {
			for (int j = 0; j < nombrePlacesParRangee; j++) {

				if (occupation[i][j] != null) {
					total++;
				}
			}
		}

		return total;

	}

	private void calculerDimensionsStationnement(String nomFichier) throws Exception {

		// ÉCRIVEZ VOTRE CODE ICI !
		nombreRangees = 0;
		nombrePlacesParRangee = 0;

		Scanner scanner = new Scanner(new File(nomFichier));

		while (scanner.hasNext()) {
			String str = scanner.nextLine();
			// ÉCRIVEZ VOTRE CODE ICI !
			str=str.replaceAll("\\s+", "");

			if (str.length()==0){
				continue;
			}

			if (nombreRangees == 0) {
				String[] cases = str.split(SEPARATEUR);
				nombrePlacesParRangee = cases.length;
			}

			nombreRangees++;

		}

		scanner.close();


	}

	private void remplirConceptionDepuisFichier(String nomFichier) throws Exception {
		Scanner scanner = new Scanner(new File(nomFichier));

		// ÉCRIVEZ VOTRE CODE ICI !
		int i=0;
		// boucle while pour lire la conception du stationnement
		while (scanner.hasNext()) {
			String str = scanner.nextLine();
			// ÉCRIVEZ VOTRE CODE ICI !
			str = str.replaceAll("\\s+", "");
			if (str.length() == 0) {
				continue; }

			String[] align= str.split(SEPARATEUR);
			for (int j = 0; j < align.length; j++) {

				conceptionStationnement[i][j] = Utilitaire.getTypeVoitureParLibelle(align[j]);

			}
			i++;
		}
		scanner.close();


	}

	/**
	 * NOTE : Cette méthode est complète; vous n’avez pas besoin de la modifier.
	 * @return Chaîne de caractères contenant les informations du stationnement
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("==== Conception du stationnement ====").append(System.lineSeparator());

		for (int i = 0; i < conceptionStationnement.length; i++) {
			for (int j = 0; j < conceptionStationnement[0].length; j++) {
				buffer.append((conceptionStationnement[i][j] != null)
						? Utilitaire.getLibelleParTypeVoiture(conceptionStationnement[i][j])
						: Utilitaire.getLibelleParTypeVoiture(TypeVoiture.NA));
				if (j < nombrePlacesParRangee - 1) {
					buffer.append(", ");
				}
			}
			buffer.append(System.lineSeparator());
		}

		buffer.append(System.lineSeparator())
		      .append("==== Occupation du stationnement ====")
		      .append(System.lineSeparator());

		for (int i = 0; i < occupation.length; i++) {
			for (int j = 0; j < occupation[0].length; j++) {
				buffer.append("(" + i + ", " + j + "): "
						+ ((occupation[i][j] != null) ? occupation[i][j] : "Inoccupé"));
				buffer.append(System.lineSeparator());
			}

		}
		return buffer.toString();
	}
}
