public abstract class Pairable {
	public Pair cons (Pairable item) {
		return new Pair(item, this);
	}
	
	public Boolean satisfies (Class dass) {
		return dass.isAssignableFrom(this.class);
	}
}
