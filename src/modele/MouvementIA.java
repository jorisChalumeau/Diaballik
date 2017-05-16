package modele;

public class MouvementIA {

	public Case caseSrc;
	public Point src;
	public Point dest;
	public TypeMouvement type;

	public MouvementIA(Point x, Point y) {
		src = x;
		dest = y;
	}

	public MouvementIA(Point x, Point y, TypeMouvement type, Case caseSrc) {
		src = x;
		dest = y;
		this.type = type;
		this.caseSrc = caseSrc;
	}

	public void revenir() {
		Point tmp = new Point(src.getRow(), src.getColumn());
		src = dest;
		dest = tmp;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		MouvementIA move = (MouvementIA) o;

		if (src != null ? !src.equals(move.src) : move.src != null)
			return false;
		return dest != null ? dest.equals(move.dest) : move.dest == null;

	}

	@Override
	public int hashCode() {
		int result = src != null ? src.hashCode() : 0;
		result = 31 * result + (dest != null ? dest.hashCode() : 0);
		return result;
	}

}
