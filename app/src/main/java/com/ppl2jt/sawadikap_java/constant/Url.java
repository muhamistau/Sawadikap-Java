package com.ppl2jt.sawadikap_java.constant;

public class Url {

    // POST
//    public static final String BASE = "http://sawadikap-endpoint.herokuapp.com/api/";
    public static final String BASE = "http://sawadikap.himatif.org/api/";
    public static final String LOGIN = BASE + "login";
    public static final String SIGNUP = BASE + "signup";
    public static final String INPUT = BASE + "input";
    public static final String SEDEKAH = BASE + "sedekah";
    public static final String UPDATE_USER = BASE + "update";

    // GET
    public static String userPakaian(int id) {
        return BASE + "pakaian/" + id;
    }

    public static String userRequest(int id) {
        return BASE + "request/" + id;
    }

    public static String userNumberOfSedekah(int id) {
        return BASE + "banyakSedekah/" + id;
    }

    public static String getUserCredentials(int id) {
        return BASE + "getUser/" + id;
    }

    // DELETE
    public static String deletePakaian(int id) {
        return BASE + "delete/" + id;
    }
}
