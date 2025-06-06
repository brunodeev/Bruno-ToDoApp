package com.bruno.model;

public record TaskJdbc(Integer id, String name, boolean completed) implements Task {

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }
}
