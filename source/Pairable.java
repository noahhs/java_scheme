public abstract class Pairable {
	public Pair cons (Pairable item) {
		return new Pair(item, this);
	}
}
