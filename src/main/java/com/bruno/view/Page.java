package com.bruno.view;

import java.util.Map;

public interface Page {

    String render(Map<String, Object> parameters);

}
