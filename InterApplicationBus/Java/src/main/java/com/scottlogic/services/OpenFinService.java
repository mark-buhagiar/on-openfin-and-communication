package com.scottlogic.services;

import com.openfin.desktop.*;
import java.io.IOException;
import java.lang.System;

public class OpenFinService implements DesktopStateListener {

    public static OpenFinService OpenFinService;

    private DesktopConnection desktopConnection;
    protected OpenFinRuntime openFinSystem;

    private final String APPUUID = "openfin-IAB-java-demo";

    public synchronized static OpenFinService getInstance() {
        if (OpenFinService == null) {
            OpenFinService = new OpenFinService();
        }
        return OpenFinService;
    }

    private OpenFinService() {
        try {
            LaunchOpenFinAndWait();
        } catch (DesktopIOException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DesktopException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void LaunchOpenFinAndWait() throws InterruptedException, IOException, DesktopException, DesktopIOException {
        launchOpenFin();
        System.out.println("Initializing OpenFin");
        wait();
        System.out.println("OpenFin initialization complete");
    }

    void launchOpenFin() throws DesktopIOException, IOException, DesktopException {
        RuntimeConfiguration runtimeConfiguration = new RuntimeConfiguration();
        runtimeConfiguration.setRuntimeVersion("beta");
        runtimeConfiguration.setAdditionalRuntimeArguments("--v=1");
        this.desktopConnection = new DesktopConnection(APPUUID);
        this.desktopConnection.connect(runtimeConfiguration, this, 60);
    }

    public InterApplicationBus getIAB() {
        return this.desktopConnection.getInterApplicationBus();
    }

    @Override
    public synchronized void onReady() {
        openFinSystem = new OpenFinRuntime(desktopConnection);
        try {
            openFinSystem.addEventListener("application-platform-api-ready", e -> {
                System.out.println("application-platform-api-ready: " + e.getEventObject());
                System.out.println(e.getEventObject().getString("uuid"));
            }, null);

            notifyAll();
        } catch (DesktopException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(String error) {
        System.exit(0);
    }

    @Override
    public void onError(String reason) {
    }

    @Override
    public void onMessage(String message) {
    }

    @Override
    public void onOutgoingMessage(String message) {
    }
}

