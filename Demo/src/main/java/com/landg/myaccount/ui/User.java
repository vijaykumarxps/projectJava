package com.landg.myaccount.ui;

public class User {
    private String PartyID;

    public User() {
    }

    public User(String partyID) {
        PartyID = partyID;
    }

    public void setPartyID(String partyID) {
        PartyID = partyID;
    }

    public String getPartyID() {
        return PartyID;
    }
}
