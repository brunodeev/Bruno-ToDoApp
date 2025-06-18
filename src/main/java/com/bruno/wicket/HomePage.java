package com.bruno.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class HomePage extends WebPage {

    public HomePage() {
        add(new Label("hello", "Olá, este é um teste usando Wicket!"));
    }
}
