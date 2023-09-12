package com.kucoin.sdk;

public class ProxySettings {
    private final String ip;
    private final String port;
    private final String login;
    private final String password;

    public ProxySettings(String ip, String port, String login, String password) {
        this.ip = ip;
        this.port = port;
        this.login = login;
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
