package score;

import network.*;
import java.util.*;

public class SpeedScorer extends Scorer{
	Map<Long, Long> link2time ;
	int interval ;
	long sourceTime ;
	public SpeedScorer(){
		link2time = null;
		interval = 1;
		sourceTime = (long)0;
	}
	public void setInterval( int interval ){ this.interval = interval ;}
	public void setLink2time( Map<Long,Long> link2time) { this.link2time = link2time ;}
	public void setSourceTime( long sourceTime){ this.sourceTime = sourceTime ; }
	public double getScore(Network path  , Long sourceId ){
		if( link2time == null) {
			System.out.println("Please set 'link2time'.");
			return -1.0;
		} 
		return getSpeed( path, sourceId, this.sourceTime ,this.interval, this.link2time );
	}   	
}
