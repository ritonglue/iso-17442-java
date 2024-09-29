package org.edumdum.iso;

import java.util.regex.Pattern;

import org.edumdum.iso.exception.BadKeyRangeException;

public class Iso17442
{
    private static final String FORMAT_ISVALID = "^[0-9A-Z]{18}[0-9]{2}$";
    private static final String FORMAT_GENERATE = "^[0-9A-Z]{18}$";
    private static final Pattern PATTERN_ISVALID = Pattern.compile(FORMAT_ISVALID);
    private static final Pattern PATTERN_GENERATE = Pattern.compile(FORMAT_GENERATE);

	/**
	 * Check requirements.
	 * 
	 * @param rawValue
	 * 	- must be not `null`
	 * 	- must respect format `^[0-9A-Z]{18}[0-9]{2}$`
	 * @return If the check digit (last 2 characters) are valids 
	 * @throws IllegalArgumentException
	 */
	public static boolean isValid(String rawValue)
		throws IllegalArgumentException
	{                
		if (rawValue == null || !PATTERN_ISVALID.matcher(rawValue).matches())
		{
			throw new IllegalArgumentException(String.format("Invalid data format; expecting '%s', found: '%s'.", FORMAT_ISVALID, rawValue));
		}
		int n = Integer.parseInt(rawValue.substring(18, 20));
		if (!(2 <= n && n <= 98))
		{
			throw new BadKeyRangeException(rawValue);
		}

		return Iso7064.computeWithoutCheck(rawValue) == 1;
	}

	/**
	 * Check requirements.
	 * 
	 * @param rawValue
	 * 	- must be not `null`
	 * 	- must respect format `^[0-9A-Z]{18}$`
	 * @return rawValue with check digits appended at the end
     * @throws IllegalArgumentException
	 */
    public static String generate(String rawValue)
        throws IllegalArgumentException
    {                
        if (rawValue == null || !PATTERN_GENERATE.matcher(rawValue).matches())
        {
            throw new IllegalArgumentException(String.format("Invalid data format; expecting '%s', found: '%s'.", FORMAT_GENERATE, rawValue));
        }
        
        return rawValue + String.format("%02d", 98 - Iso7064.computeWithoutCheck(rawValue + "00"));
	}
}
