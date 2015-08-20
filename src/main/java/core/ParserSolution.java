package core;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import enums.ActionType;

public class ParserSolution {

	public static void main( String[] args ) throws IOException {
		InputStream gameLogger = FindActions.class.getResourceAsStream( "/games.log" );
		byte[] bytes = new byte[gameLogger.available()];
		gameLogger.read( bytes );

		String logerContent = new String( bytes );

		String actionRegex = " \\d{1,2}:\\d{2}\\s(\\w+):(.*)";

		Pattern pattern = Pattern.compile( actionRegex );
		Matcher matcher = pattern.matcher( logerContent );

		String action = null;
		Game game = null;

		List<Game> games = new ArrayList<>();

		while ( matcher.find() ) {
			action = matcher.group( 1 );
			ActionType actionType = ActionType.getByDescription( action );

			// se for uma ação válida para resolução do problema
			if ( actionType != null ) {

				// de acordo com a ação efetuar operações
				switch ( actionType ) {
					case INIT_GAME:

						game = new Game();
						games.add( game );
						break;

					case KILL:
						if ( game != null ) {

							String moreInfos = matcher.group( 2 );

							Pattern patternMoreInfos = Pattern.compile( "(\\d+)\\s(\\d+)\\s(\\d+):\\s(.+)\\skilled\\s(.+)?\\sby\\s(.+)" );
							Matcher matcherMoreInfos = patternMoreInfos.matcher( moreInfos );

							if ( matcherMoreInfos.find() ) {

								int playerKillerId = Integer.valueOf( matcherMoreInfos.group( 1 ) );
								int playerDeathId = Integer.valueOf( matcherMoreInfos.group( 2 ) );
								int causeDeathId = Integer.valueOf( matcherMoreInfos.group( 3 ) );

								String playerKillerName = matcherMoreInfos.group( 4 );
								String playerDeathName = matcherMoreInfos.group( 5 );
								String causeDeathDescription = matcherMoreInfos.group( 6 );

								// adiciona jogador que matou
								game.addPlayer( playerKillerId, playerKillerName );

								// adiciona jogador que morreu
								game.addPlayer( playerDeathId, playerDeathName );

								// adicionar causa da morte
								game.addDeathCause( causeDeathId, causeDeathDescription );

								// contabiliza a
								game.addKill( playerKillerId, playerDeathId, causeDeathId );
							}
						} else {
							throw new IllegalArgumentException( "Game não inicializado !!!" );
						}

						break;

					case SHUTDOWN_GAME:
						// há jogos que não estão finalizados no log, optei por
						// não utilizar esta ação
						break;

					default:
						break;
				}
			}
		}

		for ( Game currentGame : games ) {
			System.out.println( currentGame.toString() );
		}

	}

}
