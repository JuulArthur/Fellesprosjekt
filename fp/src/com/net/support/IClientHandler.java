package com.net.support;

public interface IClientHandler {
	public void errorOnWrite( Exception e );
	public void errorOnRead( Exception e );
	public void onMessage( String message );
}
