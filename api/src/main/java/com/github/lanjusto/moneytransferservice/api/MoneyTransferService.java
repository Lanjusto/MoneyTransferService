package com.github.lanjusto.moneytransferservice.api;

import com.github.lanjusto.moneytransferservice.api.resource.account.entity.AccountServerEntityResource;
import com.github.lanjusto.moneytransferservice.api.resource.account.list.AccountListServerResource;
import com.github.lanjusto.moneytransferservice.api.resource.transaction.entity.TransactionServerEntityResource;
import com.github.lanjusto.moneytransferservice.api.resource.transaction.list.TransactionListServerResource;
import com.github.lanjusto.moneytransferservice.api.url.APIUrl;
import com.github.lanjusto.moneytransferservice.api.url.APIUrlProvider;
import org.jetbrains.annotations.NotNull;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.resource.ServerResource;
import org.restlet.routing.Router;

import javax.inject.Inject;

public class MoneyTransferService extends Application {
    public static final String INJECTION_POINT = "InjectionPoint";

    private final InjectionPoint injectionPoint;

    @Inject
    private MoneyTransferService(InjectionPoint injectionPoint) {
        this.injectionPoint = injectionPoint;
    }

    @Override
    public Restlet createInboundRoot() {
        final Router router = new Router(getContext());

        router.getContext().getAttributes().put(INJECTION_POINT, injectionPoint);

        attach(router, APIUrlProvider.getAccountEntityUrl(), AccountServerEntityResource.class);
        attach(router, APIUrlProvider.getAccountListUrl(), AccountListServerResource.class);
        attach(router, APIUrlProvider.getTransactionEntityUrl(), TransactionServerEntityResource.class);
        attach(router, APIUrlProvider.getTransactionListUrl(), TransactionListServerResource.class);

        return router;
    }

    private void attach(@NotNull Router router, @NotNull APIUrl url, @NotNull Class<? extends ServerResource> targetClass) {
        router.attach(url.getRelative(), targetClass);
        router.attach(url.getRelative() + APIUrl.DELIMITER, targetClass);
    }
}
