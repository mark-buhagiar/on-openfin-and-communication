package com.scottlogic;

import com.scottlogic.services.IABService;

public class Main {

    public static void main(String args[]){
        IABService iabService = IABService.getInstance();
        iabService.send("Hello there!");
    }

}
