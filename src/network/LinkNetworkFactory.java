package network;

public class LinkNetworkFactory extends DiffusionNetworkFactory {

	public static LinkNetwork getLinkNetwork(DiffusionNetwork dn) throws Exception {
		LinkNetwork ln = new LinkNetwork();	
		for(long e : dn.getEdges()) {
			if(ln.getUser(e) == null) {
				ln.addUser(new User(e));
			}
//			long dest = dn.getDest(e);
//			for(long de : dn.getOutEdges(dest)) {
//				Relation relation = RelationFactory.getRelation();
//				if(ln.getUser(de) == null) {
//					ln.addUser(new User(de));
//				}
//				ln.addEdge(relation.getId(), e, de);
//				ln.addRelation(relation);
//			}
			long src = dn.getSource(e);
			for(long se : dn.getInEdges(src)) {
				Relation relation = RelationFactory.getRelation();
				if(ln.getUser(se) == null) {
					ln.addUser(new User(se));
				}
				ln.addEdge(relation.getId(), se, e);
				ln.addRelation(relation);
			}
		}
		return ln;
	}
}
