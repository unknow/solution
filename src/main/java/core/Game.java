package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {

	public static int globalGameIdentifier = 0;

	private static final int PLAYER_WORLD_ID = 1022;
	private static final String PLAYER_WORLD_NAME = "<world>";

	// armazenara os nomes dos jogadores vinculados ao seu identificador no
	// game
	private Map<Integer, String> playerNamesByPlayerId;

	// armazenara a quantidade de kills dos jogadores vinculados pelo seu
	// identificador no game
	private Map<Integer, Integer> killsByPlayerId;

	// armazenara o tipo de morte vinculado pelo seu identificador no game
	private Map<Integer, String> deathTypesByDeathTypeId;

	// armazenara a quantidade de morte pelo seu respectivo identifcador no
	// game
	private Map<Integer, Integer> deathsByDeathTypeId;

	private int gameId;

	public Game() {
		this.gameId = ++globalGameIdentifier;
		this.playerNamesByPlayerId = new HashMap<>();
		this.killsByPlayerId = new HashMap<>();
		this.deathTypesByDeathTypeId = new HashMap<>();
		this.deathsByDeathTypeId = new HashMap<>();

		// adiciona o player world a lista de players
		postConstruction();
	}

	private void postConstruction() {
		this.playerNamesByPlayerId.put( PLAYER_WORLD_ID, PLAYER_WORLD_NAME );
	}

	public void addPlayer( int id, String name ) {
		if ( !playerNamesByPlayerId.containsKey( id ) ) {
			playerNamesByPlayerId.put( id, name );
		}
	}

	public void addKill( int playerKillerId, int playerDeathId, int causeDeathId ) {

		int kills = 0;
		if ( killsByPlayerId.containsKey( playerKillerId ) ) {
			kills = killsByPlayerId.get( playerKillerId );
		}
		killsByPlayerId.put( playerKillerId, ++kills );

		kills = 0;
		// se o player que matou foi <world>, adiciona -1 kill ao player morto.
		if ( playerKillerId == PLAYER_WORLD_ID ) {
			if ( killsByPlayerId.containsKey( playerDeathId ) ) {
				kills = killsByPlayerId.get( playerDeathId );
			}
			killsByPlayerId.put( playerDeathId, --kills );
		}

		int deaths = 0;
		if ( deathsByDeathTypeId.containsKey( causeDeathId ) ) {
			deaths = deathsByDeathTypeId.get( causeDeathId );
		}
		deathsByDeathTypeId.put( causeDeathId, ++deaths );
	}

	public void addDeathCause( int causeDeathId, String description ) {
		if ( !deathTypesByDeathTypeId.containsKey( causeDeathId ) ) {
			deathTypesByDeathTypeId.put( causeDeathId, description );
		}
	}

	public int getGameId() {
		return gameId;
	}

	public int getTotalKills() {
		int totalKills = 0;
		for ( int playerId : killsByPlayerId.keySet() ) {
			int kills = killsByPlayerId.get( playerId );
			if ( playerId == PLAYER_WORLD_ID ) {
				kills = kills * 2;
			}
			totalKills += kills;
		}

		return totalKills;
	}

	public String[] getPlayerNames() {
		List<String> playerNames = new ArrayList<>();
		for ( int playerId : playerNamesByPlayerId.keySet() ) {
			if ( playerId != PLAYER_WORLD_ID ) {
				String playerName = playerNamesByPlayerId.get( playerId );
				playerNames.add( String.format( "\"%s\"", playerName ) );
			}
		}
		Collections.sort( playerNames );
		return playerNames.toArray( new String[] {} );
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append( String.format( "%s%s: {\n", "game_", getGameId() ) );
		sb.append( String.format( "\ttotal_kills: %s;\n", getTotalKills() ) );
		sb.append( String.format( "\tplayers: %s\n", Arrays.toString( getPlayerNames() ) ) );
		sb.append( "\tkills: {\n" );

		int cont = 0;
		for ( Integer playerId : playerNamesByPlayerId.keySet() ) {
			++cont;
			// se não for o player world
			if ( playerId != PLAYER_WORLD_ID ) {
				Integer kills = killsByPlayerId.get( playerId );

				// se o jogador não tiver matado, mostrar 0 (zero) na lista de
				// detalhes
				if ( kills == null )
					kills = 0;
				sb.append( String.format( "\t\t\"%s\": %s", playerNamesByPlayerId.get( playerId ), kills ) );

				// se não for o ultimo
				if ( cont != playerNamesByPlayerId.size() - 1 )
					sb.append( ",\n" );
				else
					sb.append( "\n" );
			}

		}

		sb.append( "\t}\n" );
		sb.append( "}\n\n" );

		return sb.toString();
	}

}
