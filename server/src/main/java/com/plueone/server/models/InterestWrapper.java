package com.plueone.server.models;

import java.io.Serializable;
import java.util.List;

public class InterestWrapper {

    private List<Interest> interests;
    private List<Subinterest> subInterests;

    public List<Interest> getInterests() {
        return interests;
    }

    public void setInterests(List<Interest> interests) {
        this.interests = interests;
    }

    public List<Subinterest> getSubInterests() {
        return subInterests;
    }

    public void setSubInterests(List<Subinterest> subInterests) {
        this.subInterests = subInterests;
    }

}
