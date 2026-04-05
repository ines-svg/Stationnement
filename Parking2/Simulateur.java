/**
 * @auteur Mehrdad Sabetzadeh, Université d'Ottawa
 *
 */
public class Simulateur {

	/**
	 * Durée maximale pendant laquelle une voiture peut être stationnée dans le stationnement
	 */
	public static final int DUREE_MAX_STATIONNEMENT = 8 * 3600;

	/**
	 * Durée totale de la simulation en secondes (simulées)
	 */
	public static final int DUREE_SIMULATION = 24 * 3600;

	/**
	 * La distribution de probabilité pour le départ d’une voiture du stationnement en fonction
	 * de la durée pendant laquelle la voiture a été stationnée dans le stationnement
	 */
	public static final DistributionTriangulaire pdfDepart = new DistributionTriangulaire(
			0,
			DUREE_MAX_STATIONNEMENT / 2,
			DUREE_MAX_STATIONNEMENT
	);

	/**
	 * La probabilité qu’une voiture arrive à une seconde (simulée) donnée.
	 * Cette probabilité est calculée dans le constructeur à partir de la valeur perHourArrivalRate
	 * passée au constructeur.
	 */
	private Rationnel probabiliteArriveeParSeconde;

	/**
	 * L’horloge de simulation. Initialement, l’horloge doit être réglée à zéro; ensuite,
	 * elle doit être incrémentée d’une unité après chaque seconde (simulée).
	 */
	private int horloge;

	/**
	 * Nombre total d’étapes (secondes simulées) pendant lesquelles la simulation doit s’exécuter.
	 * Cette valeur est fixée au début de la simulation. La boucle de simulation doit être exécutée
	 * tant que horloge < etapes. Lorsque horloge == etapes, la simulation est terminée.
	 */
	private int etapes;

	/**
	 * Instance du stationnement simulé.
	 */
	private Stationnement stationnement;

	/**
	 * File pour les voitures voulant entrer dans le stationnement
	 */
	private File<Emplacement> fileEntrante;

	/**
	 * File pour les voitures voulant sortir du stationnement
	 */
	private File<Emplacement> fileSortante;

	/**
	 * @param stationnement        est le stationnement à simuler
	 * @param tauxArriveeParHeure  est le taux HORAIRE auquel les voitures se présentent devant le stationnement
	 * @param etapes               est le nombre total d’étapes de la simulation
	 */
	public Simulateur(Stationnement stationnement, int tauxArriveeParHeure, int etapes) {

		this.stationnement = stationnement;

		this.etapes = etapes;

		this.horloge = 0;
		
		// VOTRE CODE ICI ! VOUS DEVEZ SIMPLEMENT COMPLÉTER LES LIGNES CI-DESSOUS :

		// Par quoi faut-il remplacer les deux points d’interrogation ?
		// Indice : on vous donne un tauxArriveeParHeure.
		// Tout ce que vous devez faire est de convertir ce taux horaire
		// en un taux par seconde (probabilité).
		
		this.probabiliteArriveeParSeconde = new Rationnel(tauxArriveeParHeure, 3600);

		
		// Finalement, vous devez initialiser les files entrante et sortante

		fileEntrante = new FileChainee<Emplacement>();
		fileSortante = new FileChainee<Emplacement>();

	}

	/**
	 * Simule le stationnement pendant le nombre d’étapes spécifié par la variable
	 * d’instance etapes.
	 * Dans cette méthode, vous allez implémenter l’algorithme montré à la Figure 3 de la description de A2.
	 */
	public void simuler() {
	
		// Des variables locales peuvent être définies ici.
		Emplacement attenteEntree = null;


		this.horloge = 0;
		// Notez que pour les besoins spécifiques de A2, horloge aurait pu être
		// définie comme une variable locale aussi.

		while (horloge < etapes) {
	
			// ÉCRIVEZ VOTRE CODE ICI !
			if (GenerateurAleatoire.evenementSurvenu(probabiliteArriveeParSeconde)) {
				Voiture v = GenerateurAleatoire.genererVoitureAleatoire();
				Emplacement e = new Emplacement(v, horloge);
				fileEntrante.enfiler(e);
			}
			for (int i=0; i<stationnement.getNombreRangees(); i++){
				for (int j=0; j< stationnement.getNombrePlacesParRangee(); j++){
					Emplacement e = stationnement.getEmplacementA(i,j );
					if (e==null) {
						continue;
					}
					int duration = horloge - e.getHorodatage();
					Rationnel pLeave = pdfDepart.pdf(duration);
					if (GenerateurAleatoire.evenementSurvenu(pLeave) || duration>=DUREE_MAX_STATIONNEMENT) {
						Emplacement k = stationnement.retirer(i, j);
						if (k != null) {
							fileSortante.enfiler(k);
						}
					}


				}

			}


			if (attenteEntree==null && !fileEntrante.estVide()){
				Emplacement j = fileEntrante.defiler();
				attenteEntree = j;


			}
			if ( attenteEntree!=null && stationnement.tenterStationnement(attenteEntree.getVoiture(), horloge)){
				System.out.println(attenteEntree.getVoiture() + " est ENTRÉE à l’instant " + horloge +
						"; l’occupation est maintenant de " +
						stationnement.getOccupationTotale());
				attenteEntree=null;

			}
			if (!fileSortante.estVide()) {

				Emplacement e = fileSortante.defiler();

				System.out.println(
						e.getVoiture() + " est SORTIE à l’instant "
								+ horloge + "; l’occupation est maintenant de "
								+ stationnement.getOccupationTotale()
				);
			}



			horloge++;
		}
	}

	/**
	 * <b>main</b> de l’application. La méthode lit d’abord depuis l’entrée standard
	 * le nom de la conception du stationnement. Ensuite, elle simule le stationnement
	 * pendant un certain nombre d’étapes (ce nombre est spécifié par le paramètre etapes).
	 * À la fin, la méthode affiche sur la sortie standard des informations à propos de
	 * l’instance de Stationnement qui vient d’être créée.
	 * 
	 * @param args paramètres de ligne de commande (non utilisés dans le corps de la méthode)
	 * @throws Exception
	 */

	public static void main(String args[]) throws Exception {

		InfoEtudiant.afficher();
		
		if (args.length < 2) {
			System.out.println("Utilisation : java Simulateur <nom_fichier_conception_stationnement> <taux_horaire_d_arrivee>");
			System.out.println("Exemple : java Simulateur parking.inf 11");
			return;
		}

		if (!args[1].matches("\\d+")) {
			System.out.println("Le taux horaire d’arrivée doit être un entier positif !");
			return;
		}

		Stationnement stationnement = new Stationnement(args[0]);

		System.out.println("Nombre total de places de stationnement utilisables (capacité) : " + stationnement.getCapaciteTotale());

		Simulateur sim = new Simulateur(stationnement, Integer.parseInt(args[1]), DUREE_SIMULATION);

		long debut, fin;

		System.out.println("=== DÉBUT DE LA SIMULATION ===");
		debut = System.currentTimeMillis();
		sim.simuler();
		fin = System.currentTimeMillis();
		System.out.println("=== FIN DE LA SIMULATION ===");

		System.out.println();

		System.out.println("La simulation a pris " + (fin - debut) + "ms.");

		System.out.println();

		int compteur = 0;

		// L’interface File ne fournit pas de méthode pour obtenir la taille de la file.
		// Nous devons donc défiler tous les éléments pour compter combien d’éléments la file contient !
		
		while (!sim.fileEntrante.estVide()) {
			sim.fileEntrante.defiler();
			compteur++;
		}

		System.out.println("Longueur de la file de voitures à l’entrée à la fin de la simulation : " + compteur);
	}
}
