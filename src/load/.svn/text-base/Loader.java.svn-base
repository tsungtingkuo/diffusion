package load;
import java.io.*; 
import network.*;
import java.util.*;

public class Loader {
	public static Network getPath( String pathFile , Map<Long,Long> link2time  ) throws Exception{
		Network n = new Network();
		LineNumberReader lnre =  new LineNumberReader( new FileReader( pathFile));
		String se = null;
		while ((se=lnre.readLine()) != null) {
			Relation r = RelationFactory.getRelation();
			String[] t = se.split("\t");
			long sourceId = Long.parseLong(t[0]);
			long destinationId = Long.parseLong(t[1]);
			long timeStamp = Long.parseLong(t[2]);
			//add node
			if( n.getUser( sourceId)== null){
				User u = UserFactory.getUser( sourceId );
				n.addUser( u );
				n.addVertex( sourceId );	
			} 
			if( n.getUser( destinationId ) == null){
				User u = UserFactory.getUser( destinationId );
				n.addUser( u );
				n.addVertex( destinationId );
			}
			if( link2time != null){
				link2time.put( r.getId() , timeStamp );	
			}	
			n.addEdge(r.getId(), sourceId, destinationId);
			n.addRelation(r);
		}
		lnre.close();

		return n;
	} 
}
