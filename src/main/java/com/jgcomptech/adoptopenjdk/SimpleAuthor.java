package com.jgcomptech.adoptopenjdk;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SimpleAuthor {
    private String login;
    private int id;
    private String url;

    public String getLogin() {
        return login;
    }

    public SimpleAuthor setLogin(final String login) {
        this.login = login;
        return this;
    }

    public int getId() {
        return id;
    }

    public SimpleAuthor setId(final int id) {
        this.id = id;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public SimpleAuthor setUrl(final String url) {
        this.url = url;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("login", login)
                .append("id", id)
                .append("url", url)
                .toString();
    }
}
