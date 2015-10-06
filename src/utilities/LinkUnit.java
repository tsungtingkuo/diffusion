package utilities;

public class LinkUnit{
	Long from;
	Long to;
	double value;
	public LinkUnit( Long from , Long to, double value ){ 
		this.from = from; this.to= to ; this.value = value ;}
	public Long getFrom(){ return from;}
	public Long getTo(){ return to;}
	public double getValue(){ return value ;}
}
