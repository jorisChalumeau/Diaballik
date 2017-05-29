package modele;

import java.util.function.Function;

/**
 * C'est une position de coordonnées (ligne ; colonne)
 */
public class Point {
	private int row;
	private int column;

	public Point(int x, int y) {
		this.setRow(x);
		this.setColumn(y);
	}

	public int getRow() {
		return row;
	}

	public void setRow(int x) {
		this.row = x;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int y) {
		this.column = y;
	}

	/**
	 * 
	 * @param rowMovement
	 * @return un nouveau Point décalé de rowMovement lignes par rapport à ce
	 *         Point
	 */
	public Point changeRow(int rowMovement) {
		return new Point(row + rowMovement, column);
	}

	/**
	 * 
	 * @param columnMovement
	 * @return un nouveau Point décalé de columnMovement colonnes par rapport à
	 *         ce Point
	 */
	public Point changeColumn(int columnMovement) {
		return new Point(row, columnMovement + column);
	}

	/**
	 * 
	 * @param otherPosition
	 * @return true si otherPosition est un Point adjascent à ce Point ; false sinon
	 */
	public boolean estVoisin(Point otherPosition) {
		if (this.row == otherPosition.row
				&& (this.column + 1 == otherPosition.column || this.column - 1 == otherPosition.column)) {
			return true;
		}
		if (this.column == otherPosition.column
				&& (this.row + 1 == otherPosition.row || this.row - 1 == otherPosition.row)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Point)) {
			return false;
		}
		Point other = (Point) object;
		if (this.row == other.row && this.column == other.column) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param to
	 * @return true si to est un Point situé en diagonale par rapport à ce point
	 */
	public boolean isOnDiagonal(Point to) {
		if (this.row > to.row && this.column > to.column) {
			return checkDiagonal((from) -> {
				return from.column >= 0;
			}, to, this, (from) -> {
				return from.changeRow(-1).changeColumn(-1);
			});
		}
		if (this.row > to.row && this.column < to.column) {
			return checkDiagonal((from) -> {
				return from.column <= 6;
			}, to, this, (from) -> {
				return from.changeRow(-1).changeColumn(1);
			});
		}
		if (this.row < to.row && this.column > to.column) {
			return checkDiagonal((from) -> {
				return from.column >= 0;
			}, to, this, (from) -> {
				return from.changeRow(1).changeColumn(-1);
			});
		}
		if (this.row < to.row && this.column < to.column) {
			return checkDiagonal((from) -> {
				return from.column <= 6;
			}, to, this, (from) -> {
				return from.changeRow(1).changeColumn(1);
			});
		}
		return false;
	}

	private boolean checkDiagonal(Function<Point, Boolean> limiter, Point to, Point from,
			Function<Point, Point> incrementer) {
		while (limiter.apply(from)) {
			if (from.equals(to)) {
				return true;
			}
			from = incrementer.apply(from);
		}
		return false;
	}
}
