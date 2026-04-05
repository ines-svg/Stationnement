// Cette classe est empruntée de Computer Science: An Interdisciplinary Approach
// par Robert Sedgewick et Kevin Wayne
// Code source : https://introcs.cs.princeton.edu/java/92symbolic/Rational.java.html

/**
 * L’implémentation de cette classe est complète. Vous n’avez *pas* besoin de
 * modifier cette classe dans ce devoir.
 */

/******************************************************************************
 * Type de données abstrait (ADT) immuable pour les nombres rationnels.
 * 
 * Invariants
 * ----------
 * - pgcd(num, den) = 1, c.-à-d. que le nombre rationnel est sous forme réduite
 * - den >= 1, le dénominateur est toujours un entier positif
 * - 0/1 est la représentation unique de 0
 ******************************************************************************/

public class Rationnel {
	public static Rationnel zero = new Rationnel(0, 1);

	private int num; // le numérateur
	private int den; // le dénominateur

	// crée et initialise un nouvel objet Rationnel
	public Rationnel(int numerateur, int denominateur) {

		if (denominateur == 0) {
			throw new ArithmeticException("le dénominateur est zéro");
		}

		// réduire la fraction
		int g = pgcd(numerateur, denominateur);
		num = numerateur / g;
		den = denominateur / g;

		// nécessaire uniquement pour les nombres négatifs
		if (den < 0) {
			den = -den;
			num = -num;
		}
	}

	// retourne le numérateur et le dénominateur de (this)
	public int numerateur() {
		return num;
	}

	public int denominateur() {
		return den;
	}

	// retourne la représentation en double précision de (this)
	public double enDouble() {
		return (double) num / den;
	}

	// retourne la représentation sous forme de chaîne de caractères de (this)
	public String toString() {
		if (den == 1)
			return num + "";
		else
			return num + "/" + den;
	}

	// retourne { -1, 0, +1 } si a < b, a = b, ou a > b
	public int comparerA(Rationnel b) {
		Rationnel a = this;
		int gauche = a.num * b.den;
		int droite = a.den * b.num;
		if (gauche < droite)
			return -1;
		if (gauche > droite)
			return +1;
		return 0;
	}

	// indique si cet objet Rationnel est égal à y
	public boolean equals(Object y) {
		if (y == null)
			return false;
		if (y.getClass() != this.getClass())
			return false;
		Rationnel b = (Rationnel) y;
		return comparerA(b) == 0;
	}

	// hashCode cohérent avec equals() et comparerA()
	// (il est préférable de hacher le numérateur et le dénominateur et de les combiner)
	public int hashCode() {
		return this.toString().hashCode();
	}

	// crée et retourne un nouveau rationnel (r.num + s.num) / (r.den + s.den)
	public static Rationnel mediant(Rationnel r, Rationnel s) {
		return new Rationnel(r.num + s.num, r.den + s.den);
	}

	// retourne pgcd(|m|, |n|)
	private static int pgcd(int m, int n) {
		if (m < 0)
			m = -m;
		if (n < 0)
			n = -n;
		if (0 == n)
			return m;
		else
			return pgcd(n, m % n);
	}

	// retourne ppcm(|m|, |n|)
	private static int ppcm(int m, int n) {
		if (m < 0)
			m = -m;
		if (n < 0)
			n = -n;
		return m * (n / pgcd(m, n)); // parenthèses importantes pour éviter le débordement
	}

	// retourne a * b, en évitant le débordement autant que possible par annulation croisée
	public Rationnel fois(Rationnel b) {
		Rationnel a = this;

		// réduire p1/q2 et p2/q1, puis multiplier, où a = p1/q1 et b = p2/q2
		Rationnel c = new Rationnel(a.num, b.den);
		Rationnel d = new Rationnel(b.num, a.den);
		return new Rationnel(c.num * d.num, c.den * d.den);
	}

	// retourne a + b, en évitant le débordement
	public Rationnel plus(Rationnel b) {
		Rationnel a = this;

		// cas particuliers
		if (a.comparerA(zero) == 0)
			return b;
		if (b.comparerA(zero) == 0)
			return a;

		// trouver le pgcd des numérateurs et des dénominateurs
		int f = pgcd(a.num, b.num);
		int g = pgcd(a.den, b.den);

		// addition des termes croisés pour le numérateur
		Rationnel s = new Rationnel(
				(a.num / f) * (b.den / g) + (b.num / f) * (a.den / g),
				ppcm(a.den, b.den)
		);

		// multiplication inverse
		s.num *= f;
		return s;
	}

	// retourne -a
	public Rationnel opposer() {
		return new Rationnel(-num, den);
	}

	// retourne |a|
	public Rationnel abs() {
		if (num >= 0)
			return this;
		else
			return opposer();
	}

	// retourne a - b
	public Rationnel moins(Rationnel b) {
		Rationnel a = this;
		return a.plus(b.opposer());
	}

	public Rationnel reciproque() {
		return new Rationnel(den, num);
	}

	// retourne a / b
	public Rationnel diviser(Rationnel b) {
		Rationnel a = this;
		return a.fois(b.reciproque());
	}
}
