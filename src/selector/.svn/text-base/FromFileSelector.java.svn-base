package selector;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FromFileSelector{
/**
 *	Get initial set from file.
 *	@param filePath file path
 * */
	public static List<Long> get( String filePath ) throws Exception{
		ArrayList<Long> list = new ArrayList<Long>();
		LineNumberReader lnrv = new LineNumberReader( new FileReader( filePath));
		String sv = null;
		while ((sv=lnrv.readLine()) != null) {
			
			try{
				long id = Long.parseLong(sv);
				list.add( id );
			}catch( Exception e){
				continue;
			}
		}				
		lnrv.close();
		return list ;
	} 
}
