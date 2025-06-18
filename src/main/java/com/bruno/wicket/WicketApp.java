package com.bruno.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

public class WicketApp extends WebApplication {

    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }

    @Override
    public void init() {
        super.init();

        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
    }
}
