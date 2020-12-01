package com.scottlogic.services;

import com.openfin.desktop.*;
import org.json.JSONObject;

import java.lang.System;
import java.util.Date;

public class IABService {

    private static IABService IABService;

    public static IABService getInstance() {
        if (IABService == null)
            IABService = new IABService();

        return IABService;
    }

    private IABService() {
    }

    public void send(String message){
        try {
            InterApplicationBus iab = OpenFinService.getInstance().getIAB();
            iab.send("openfin-IAB-javascript-demo", "demo-topic", message);
        } catch (DesktopException e) {
            e.printStackTrace();
        }
    }

    public void broadcast(String message) {
        try {
            OpenFinService.getInstance().getIAB().publish("demo-topic", message);
        } catch (DesktopException e) {
            e.printStackTrace();
        }
    }

    public void registerIABSubscriber(String iabSource, String iabTopic, BusListener newListener) {
        try {
            InterApplicationBus iab = OpenFinService.getInstance().getIAB();

            iab.subscribe(iabSource, iabTopic, newListener, new AckListener() {
                @Override
                public void onSuccess(Ack ack) {
                    if (ack.isSuccessful()) {
                        System.out.println(String.format("Registered IAB listener"));
                    } else {
                        System.out.println(String.format("Failed to register IAB listener %s", ack.getReason()));
                    }
                }

                @Override
                public void onError(Ack ack) {
                    System.out.println(String.format("Failed to register IAB listener %s", ack.getReason()));
                }
            });
        } catch (DesktopException e) {
            e.printStackTrace();
        }
    }
}
