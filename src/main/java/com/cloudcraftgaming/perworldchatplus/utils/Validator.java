package com.cloudcraftgaming.perworldchatplus.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by Nova Fox on 7/11/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 */
public class Validator {
	private static Pattern VALID_IPV4_PATTERN = null;
	private static Pattern VALID_IPV6_PATTERN = null;
	private static Pattern VALID_URL_PATTERN = null;
	private static final String ipv4Pattern = "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])";
	private static final String ipv6Pattern = "([0-9a-f]{1,4}:){7}([0-9a-f]){1,4}";
	private static final String urlPattern = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";

	static {
		try {
			VALID_IPV4_PATTERN = Pattern.compile(ipv4Pattern, Pattern.CASE_INSENSITIVE);
			VALID_IPV6_PATTERN = Pattern.compile(ipv6Pattern, Pattern.CASE_INSENSITIVE);
			VALID_URL_PATTERN = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
		} catch (PatternSyntaxException e) {
			//logger.severe("Unable to compile pattern", e);
		}
	}

	/**
	 * Determine if the given string is a valid IPv4 or IPv6 address.  This method
	 * uses pattern matching to see if the given string could be a valid IP address.
	 *
	 * @param ipAddress A string that is to be examined to verify whether or not
	 *                  it could be a valid IP address.
	 * @return <code>true</code> if the string is a value that is a valid IP address,
	 * <code>false</code> otherwise.
	 */
	public static boolean isIpAddress(String ipAddress) {
		try {
			Matcher m1 = Validator.VALID_IPV4_PATTERN.matcher(ipAddress);
			if (m1.matches()) {
				return true;
			}
			Matcher m2 = Validator.VALID_IPV6_PATTERN.matcher(ipAddress);
			if (m2.matches()) {
				return true;
			} else if (ipAddress.contains(":")) {
				String[] ipAddressSplit = ipAddress.split(":");
				Matcher m3 = Validator.VALID_IPV4_PATTERN.matcher(ipAddressSplit[0]);
				if (m3.matches()) {
					return true;
				}
				Matcher m4 = Validator.VALID_IPV6_PATTERN.matcher(ipAddressSplit[0]);
				if (m4.matches()) {
					return true;
				}
			}
		} catch (Exception e) {
			//Some issue, safe to ignore.
		}
		return false;
	}

	/**
	 * Determine if the given string is a valid URL. This method uses pattern
	 * matching to see if the given string could be a valid URL.
	 *
	 * @param url A string that is to be examined to verify whether or not it
	 *            it could be a valid URL.
	 * @return <code>true</code> if the string is a value that is a valid URL.
	 * <code>false</code> otherwise.
	 */
	public static boolean isURL(String url) {
		if (url.length() > 7) {
			Matcher m1 = Validator.VALID_URL_PATTERN.matcher(url);
			return m1.matches();
		}
		return false;
	}
}