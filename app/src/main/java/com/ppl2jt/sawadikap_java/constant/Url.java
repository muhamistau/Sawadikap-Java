package com.ppl2jt.sawadikap_java.constant;

public class Url {

    // POST
    public static final String BASE = "http://sawadikap-endpoint.herokuapp.com/api/";
    public static final String LOGIN = BASE + "login";
    public static final String SIGNUP = BASE + "signup";
    public static final String INPUT = BASE + "input";
    public static final String SEDEKAH = BASE + "sedekah";

    // GET
    public static String userPakaian(int id) {
        return BASE + "pakaian/" + Integer.toString(id);
    }

    public static String userRequest(int id) {
        return BASE + "request/" + Integer.toString(id);
    }
}
