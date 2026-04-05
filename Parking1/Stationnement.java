import java.io.File;
import java.util.Scanner;

public class Stationnement {
	/**
	 * Le délimiteur qui sépare les valeurs
	 */
	private static final String SEPARATEUR = ",";

	/**
	 * Le délimiteur qui sépare la section décrivant la conception du stationnement
	 * de la section contenant les données des voitures stationnées
	 */
	private static final String SECTIONNEUR = "###";

	/**
	 * Variable d’instance pour stocker le nombre de rangées dans un stationnement
	 */
	private int nombreRangees;

	/**
	 * Variable d’instance pour stocker le nombre de places par rangée dans un
	 * stationnement
	 */
	private int nombrePlacesParRangee;

	/**
	 * Variable d’instance (tableau à deux dimensions) pour stocker la conception du
	 * stationnement
	 */
	private TypeVoiture[][] conceptionStationnement;

	/**
	 * Variable d’instance (tableau à deux dimensions) pour stocker l’information
	 * d’occupation des places dans le stationnement
	 */
	private Voiture[][] occupation;

	/**
	 * Construit un stationnement en chargeant un fichier
	 * * @param nomFichier est le nom du fichier
	 */
	public Stationnement(String nomFichier) throws Exception {

		if (nomFichier == null) {
			System.out.println("Le nom du fichier ne peut pas être nul.");
			return;
		}

		// 1. Déterminer les dimensions (Première passe)
		calculerDimensionsStationnement(nomFichier);

		// 2. Instancier les tableaux avec les dimensions trouvées
		this.conceptionStationnement = new TypeVoiture[nombreRangees][nombrePlacesParRangee];
		this.occupation = new Voiture[nombreRangees][nombrePlacesParRangee];

		// 3. Remplir les données (Seconde passe)
		remplirDepuisFichier(nomFichier);
	}


	/**
	 * Stationne une voiture (v) à un emplacement donné (i, j) dans le stationnement.
	 * * @param i est l’indice de la rangée de stationnement
	 * @param j est l’indice de la place dans la rangée i
	 * @param v est la voiture à stationner
	 */
	public void stationner(int i, int j, Voiture v) {
		if (peutStationnerA(i, j, v)) {
			occupation[i][j] = v;
		} else {
			// Optionnel : affichage d'erreur comme dans la capture Figure 5 si nécessaire,
			// mais le devoir ne demande pas explicitement de System.out ici, juste de ne pas stationner.
		}
	}

	/**
	 * Retire la voiture stationnée à un emplacement donné (i, j) dans le stationnement
	 * * @param i est l’indice de la rangée de stationnement
	 * @param j est l’indice de la place dans la rangée i
	 * @return la voiture retirée; null si hors limites ou vide
	 */
	public Voiture retirer(int i, int j) {
		if (i < 0 || i >= nombreRangees || j < 0 || j >= nombrePlacesParRangee) {
			return null;
		}

		if (occupation[i][j] != null) {
			Voiture v = occupation[i][j];
			occupation[i][j] = null;
			return v;
		}

		return null;
	}

	/**
	 * Vérifie si une voiture (ayant un certain type) est autorisée à stationner à
	 * l’emplacement (i, j)
	 * * @param i est l’indice de la rangée de stationnement
	 * @param j est l’indice de la place dans la rangée i
	 * @return true si la voiture v peut stationner à (i, j) et false sinon
	 */
	public boolean peutStationnerA(int i, int j, Voiture v) {
		// 1. Vérification des limites
		if (i < 0 || i >= nombreRangees || j < 0 || j >= nombrePlacesParRangee) {
			return false;
		}

		// 2. Vérification si l'emplacement est déjà occupé
		if (occupation[i][j] != null) {
			return false;
		}

		// 3. Vérification du type d'emplacement
		TypeVoiture typePlace = conceptionStationnement[i][j];

		// Aucune voiture ne peut stationner sur un emplacement NA
		if (typePlace == TypeVoiture.NA) {
			return false;
		}

		TypeVoiture typeVoiture = v.getType();

		// Règles de stationnement (Figure 4/5)
		if (typeVoiture == TypeVoiture.ELECTRIQUE) {
			// Une voiture électrique peut stationner sur tout emplacement valide
			return true; 
		} else if (typeVoiture == TypeVoiture.PETITE) {
			// Peut stationner sur PETITE, REGULIERE ou GRANDE
			return typePlace == TypeVoiture.PETITE || typePlace == TypeVoiture.REGULIERE || typePlace == TypeVoiture.GRANDE;
		} else if (typeVoiture == TypeVoiture.REGULIERE) {
			// Peut stationner sur REGULIERE ou GRANDE
			return typePlace == TypeVoiture.REGULIERE || typePlace == TypeVoiture.GRANDE;
		} else if (typeVoiture == TypeVoiture.GRANDE) {
			// Peut stationner uniquement sur GRANDE
			return typePlace == TypeVoiture.GRANDE;
		}

		return false;
	}

	/**
	 * @return la capacité totale du stationnement en excluant les places NA
	 */
	public int getCapaciteTotale() {
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
		int compte = 0;
		for (int i = 0; i < nombreRangees; i++) {
			for (int j = 0; j < nombrePlacesParRangee; j++) {
				if (occupation[i][j] != null) {
					compte++;
				}
			}
		}
		return compte;
	}

	private void calculerDimensionsStationnement(String nomFichier) throws Exception {

		Scanner scanner = new Scanner(new File(nomFichier));

		int numRows = 0;
		int numColumns = 0;
		boolean firstline = true;

		while (scanner.hasNext()) {
			String str = scanner.nextLine().trim();

			if (str.isEmpty()){
				continue;
			}

			else if (str.equals("###")){
				break;
			}

			numRows++;

			if (firstline){
				String[] tableau =str.split(",");
				numColumns = tableau.length;

				firstline = false;
			}
			
		}

		scanner.close();
	}

	private void remplirDepuisFichier(String nomFichier) throws Exception {

		Scanner scanner = new Scanner(new File(nomFichier));

		int numRows = 0;
		int numColumns = 0;
		boolean firstline = true;

		TypeVoiture type;
		String license;

		String[] tableau = null;

		// boucle while pour lire la conception du stationnement
		while (scanner.hasNext()) {
			String str = scanner.nextLine().trim();

			if (str.isEmpty()){
				continue;
			}

			else if (str.equals("###")){
				break;
			}

			tableau = str.split(",");

			if (firstline){
				numColumns = tableau.length;
				firstline = false;
			}

			for (int j = 0; j< tableau.length; j++){
				
				type = conversion(tableau[j]);

				conceptionStationnement[numRows][j] = type;		

			}

			numRows++;
		}

	
		

		// boucle while pour lire les données d’occupation
		while (scanner.hasNext()) {
			String str = scanner.nextLine().trim();

			if (str.isEmpty()){
				continue;
			}
			
			String[] part = str.split(",");

		
			int row = Integer.valueOf(part[0].trim());
			int col = Integer.valueOf(part[1].trim());
			type = conversion(part [2].trim());
			license = part[3].trim();
				
		

			boolean valid = true;

			if (row < 0 || row >=  numRows || col < 0 || col >=  numColumns){
				valid = false;
				System.out.println(" Attention: Vous ne pouvez pas stationner votre voiture ici ");
			}else {
				TypeVoiture spaceType = conceptionStationnement[row][col];
			
				if (spaceType == TypeVoiture.NA){
					valid = false;
					System.out.println(" Attention: Vous ne pouvez pas stationner votre voiture ici, espace non-valide ");
				}

				else if (spaceType != TypeVoiture.NA){
					valid = true;
					System.out.println(" Si votre voiture est ELECTRIQUE vous pouvez vous stationner ");
				}

				else if (!(spaceType == TypeVoiture.PETITE ||spaceType == TypeVoiture.REGULIERE || spaceType == TypeVoiture.GRANDE)){
					valid = false;
					System.out.println(" Attention: Vous ne pouvez pas stationner votre voiture ici  ");
				}

				else if (type == TypeVoiture.REGULIERE){
					if (!(spaceType == TypeVoiture.REGULIERE || spaceType == TypeVoiture.GRANDE)){
						valid = false;
						System.out.println(" Attention: Vous ne pouvez pas stationner votre voiture ici  ");
					}
				}
			

				else if (type == TypeVoiture.GRANDE){
					if (!(spaceType == TypeVoiture.GRANDE)){
						valid = false;
						System.out.println(" Attention: Vous ne pouvez pas stationner votre voiture ici  ");
					}
				}

			if (valid){
				occupation[row][col] = new Voiture(type, license);

			}else{
				System.out.println("Warning: Cannot park car " + license + " at ( " + numRows +" , " + numColumns + ")");
			}
		}

	}


	scanner.close();

}

	

	/**
	 * Produit une représentation sous forme de chaîne de caractères du
	 * stationnement
	 * 
	 * @return Chaîne de caractères contenant l’information du stationnement
	 */
	public String toString() {
		// NOTE : L’implémentation de cette méthode est complète. Vous n’avez PAS besoin
		// de
		// la modifier pour le devoir.
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

		buffer.append(System.lineSeparator()).append("==== Occupation du stationnement ====")
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

	/**
	 * <b>main</b> de l’application. La méthode lit d’abord depuis l’entrée standard
	 * le nom du fichier à traiter. Ensuite, elle crée une instance de
	 * Stationnement.
	 * Finalement, elle affiche sur la sortie standard des informations à propos de
	 * l’instance de Stationnement qui vient d’être créée.
	 * 
	 * @param args paramètres de ligne de commande (non utilisés dans le corps de la
	 *             méthode)
	 * @throws Exception
	 */

	public static void main(String args[]) throws Exception {

		InfoEtudiant.afficher();

		System.out.print("Veuillez entrer le nom du fichier à traiter : ");

		Scanner scanner = new Scanner(System.in);

		String nomFichier = scanner.nextLine();

		Stationnement stationnement = new Stationnement(nomFichier);

		System.out.println("Nombre total de places de stationnement utilisables (capacité) : "
				+ stationnement.getCapaciteTotale());

		System.out.println("Nombre de voitures actuellement stationnées dans le stationnement : "
				+ stationnement.getOccupationTotale());

		System.out.print(stationnement);

}

}

