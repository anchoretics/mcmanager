package io.github.anchoretics;

public class MsgType {

	/**
	 * 服务端消息类型
	 */
	public static String CHAT = "chat";
	public static String LOGIN = "login";
	public static String LOGOUT = "logout";
	public static String COMMAND = "command";
	public static String SERVER_START = "server_start";
	public static String ONLINEUSERS = "server_onlineusers";
	public static String EVENT_NAME = "serverMessage";
	
	/***
	 * 网站消息类型
	 */
	public static String WEB_USER_LOGIN = "web_user_login";
	public static String WEB_USER_LOGOFF = "web_user_logoff";
	public static String WEB_USER_CHAT = "web_user_chat";
	public static String WEB_OP_WHITELIST_ADD = "web_op_whitelist_add";
}
