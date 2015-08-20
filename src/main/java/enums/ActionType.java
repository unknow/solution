package enums;

public enum ActionType {

	INIT_GAME( "InitGame" ),
	KILL( "Kill" ),
	SHUTDOWN_GAME( "ShutdownGame" );

	private String description;

	private ActionType( String description ) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public static ActionType getByDescription( String description ) {
		ActionType action = null;
		ActionType[] actions = ActionType.values();

		for ( ActionType actionType : actions ) {
			if ( actionType.getDescription().equalsIgnoreCase( description ) ) {
				action = actionType;
				break;
			}
		}
		return action;
	}

}
