package score;

import network.*;

public class IFScorer extends Scorer{
	public double getScore(Network path  , Long sourceId ){
		return getInfluenceScore( path, sourceId );
	}     	
}
