package minDb.Connection;

import minDb.Core.Components.Data.IDataTable;

/**
 * ConnectionFeedback
 */
public class ConnectionFeedback implements IDataTable {

    private String _message; 
    
    public ConnectionFeedback(String message) {
        _message = message;
    }

	public void print() {
		System.out.println(_message);
	}
}