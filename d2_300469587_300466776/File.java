/**
 * @auteur Marcel Turcotte, Guy-Vincent Jourdan et Mehrdad Sabetzadeh
 *         (Université d'Ottawa)
 * 
 *         La déclaration de cette interface est complète. Vous n’avez *pas* besoin
 *         de modifier cette interface dans ce devoir.
 * 
 */

public interface File<E> {
	boolean estVide();

	void enfiler(E nouvelElement);

	E defiler();
}
