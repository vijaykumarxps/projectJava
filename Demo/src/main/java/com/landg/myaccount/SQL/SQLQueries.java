package com.landg.myaccount.SQL;

public class SQLQueries {

    public String user_count(String scheme,String table) {
        String SQL = "";
        SQL = "SELECT COUNT(DISINCT USERID) FROM ";
        SQL += scheme+"."+table;
        SQL += " WHERE PARTY_ID IN "+"(?)";
        return SQL;
    }
}
