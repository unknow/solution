package solution;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;

import core.FindActions;

public class ParserSolutionTest {

	private String getLoggerContent() throws IOException {
		InputStream gameLogger = FindActions.class.getResourceAsStream( "/games.log" );
		byte[] bytes = new byte[gameLogger.available()];
		gameLogger.read( bytes );

		return new String( bytes );
	}

	public void actionRegexTest() throws IOException {

		String logerContent = getLoggerContent();

		Pattern pattern = Pattern.compile( " \\d{2}:\\d{2}\\s(\\w+):.*" );
		Matcher matcher = pattern.matcher( logerContent );

		Set<String> actions = new HashSet<>();

		while ( matcher.find() ) {
			actions.add( matcher.group( 1 ) );
		}

		Assert.assertTrue( !actions.isEmpty() );

	}

}
