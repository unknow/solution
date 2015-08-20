package core;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ParserSolution {

	public static void main( String[] args ) throws IOException {
		InputStream gameLogger = FindActions.class.getResourceAsStream( "/games.log" );
		byte[] bytes = new byte[gameLogger.available()];
		gameLogger.read( bytes );

		String logerContent = new String( bytes );

		Map<Integer, String> playerNameByNumber = new HashMap<>();
		Map<Integer, String> killsByPlayerNumber = new HashMap<>();

	}

}
