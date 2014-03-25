package tips4scut.utils;

public class Pair<T1, T2> {
	protected T1 first = null;
	protected T2 second = null;

	public Pair() {
	}

	public Pair(T1 a, T2 b) {
		this.first = a;
		this.second = b;
	}

	public static <T1, T2> Pair<T1, T2> newPair(T1 a, T2 b) {
		return new Pair(a, b);
	}

	public void setFirst(T1 a) {
		this.first = a;
	}

	public void setSecond(T2 b) {
		this.second = b;
	}

	public T1 getFirst() {
		return this.first;
	}

	public T2 getSecond() {
		return this.second;
	}

	private static boolean equals(Object x, Object y) {
		return (((x == null) && (y == null)) || ((x != null) && (x.equals(y))));
	}

	public boolean equals(Object other) {
		return ((other instanceof Pair)
				&& (equals(this.first, ((Pair) other).first)) && (equals(
					this.second, ((Pair) other).second)));
	}

	public int hashCode() {
		if (this.first == null)
			return ((this.second == null) ? 0 : this.second.hashCode() + 1);
		if (this.second == null) {
			return (this.first.hashCode() + 2);
		}
		return (this.first.hashCode() * 17 + this.second.hashCode());
	}

	public String toString() {
		return "{" + getFirst() + "," + getSecond() + "}";
	}
}