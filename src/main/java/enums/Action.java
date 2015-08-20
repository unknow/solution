package enums;

public enum Action {

	INIT_GAME( "InitGame" ),
	CLIENT_CONNECT( "ClientConnect" ),
	CLIENT_BEGIN( "ClientBegin" ),
	KILL( "Kill" ),
	CLIENT_DISCONNECT( "ClientDisconnect" ),
	SHUTDOWN_GAME( "ShutdownGame" );

	private String description;

	private Action( String description ) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
