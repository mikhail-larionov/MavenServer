package com.company.Server;

import java.util.HashMap;
import java.util.Map;

public class Hangars {
    Map<Integer, String> hangars = new HashMap<>();

    public Hangars() {
    }
    public Hangars(Map<Integer, String> hangars) {
        this.hangars = hangars;
    }

    public void setHangars(Map<Integer, String> hangars) {
        this.hangars = hangars;
    }

    void addOrUpdate (int numberOfHangar, String trainValue){
        hangars.put(numberOfHangar, trainValue);
    }
    void removeHangar(int numberOfHangar){
        hangars.remove(numberOfHangar);
    }

    public Map<Integer, String> getHangars() {
        return hangars;
    }
}
