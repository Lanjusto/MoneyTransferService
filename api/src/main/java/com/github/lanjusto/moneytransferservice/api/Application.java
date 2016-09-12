package com.github.lanjusto.moneytransferservice.api;

import com.github.lanjusto.moneytransferservice.core.preparer.DataPreparer;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.restlet.Component;
import org.restlet.data.Protocol;

import javax.inject.Inject;


public class Application {
    private final MoneyTransferService moneyTransferService;
    private final DataPreparer dataPreparer;

    private final Component component;

    @Inject
    private Application(MoneyTransferService moneyTransferService, DataPreparer dataPreparer) {
        this.moneyTransferService = moneyTransferService;
        this.dataPreparer = dataPreparer;
        this.component = new Component();
    }

    public void startService() throws Exception {
        dataPreparer.prepareData();

        component.getServers().add(Protocol.HTTP);
        component.getClients().add(Protocol.FILE);

        // Attach the application to the component and start it
        component.getDefaultHost().attach(moneyTransferService);
        component.start();
    }

    public void stopService() throws Exception {
        component.stop();
    }

    public static void main(String[] args) throws Exception {
        final Weld weld = new Weld();
        final WeldContainer weldContainer = weld.initialize();

        final Application application = weldContainer.select(Application.class).get();

        application.startService();
    }
}
