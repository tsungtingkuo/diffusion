package runflow;

import model.*;
import network.*;
import score.*;
import java.util.*;

public interface Runflow{
	public void run( Model m, Network n  , Collection<Long> oldA0  , Collection<Long> A0 , Scorer scorer , Map<Long, Double> scores ) throws Exception;

}
