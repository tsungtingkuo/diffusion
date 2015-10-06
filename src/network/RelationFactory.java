package network;

public class RelationFactory {
	
	// Current id
	static long currentID = 0;

	// Use this instead of create Relation directly
	public static Relation getRelation() {
		Relation r = new Relation(RelationFactory.currentID);
		RelationFactory.currentID++;
		return r;
	}
}
