package core;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindActions {

	public static void main( String[] args ) throws IOException {
		InputStream gameLogger = FindActions.class.getResourceAsStream( "/games.log" );
		byte[] bytes = new byte[gameLogger.available()];
		gameLogger.read( bytes );

		String logerContent = new String( bytes );

		Pattern pattern = Pattern.compile( " \\d{2}:\\d{2}\\s(\\w+):.*" );
		Matcher matcher = pattern.matcher( logerContent );

		Set<String> actions = new HashSet<>();

		while ( matcher.find() ) {
			actions.add( matcher.group( 1 ) );
		}

		for ( String action : actions ) {
			System.out.println( action );
		}
	}

}
