package com.net.support;

import com.net.msg.MSGWrapper;

public interface IClientHandler {
	public void errorOnWrite( Exception e );
	public void errorOnRead( Exception e );
	public void onMessage( String message );
	public void onWrapper( MSGWrapper msgW );
}
