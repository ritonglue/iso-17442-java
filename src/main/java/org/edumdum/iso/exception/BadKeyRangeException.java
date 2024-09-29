package org.edumdum.iso.exception;

public class BadKeyRangeException extends IllegalArgumentException
{
	private static final long serialVersionUID = 1L;

	public BadKeyRangeException(String rawValue)
	{
		super(String.format("Key must be in range [02 - 98]. Found '%s'", rawValue));
	}
}
