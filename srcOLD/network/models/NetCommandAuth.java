package network.models;

import java.io.Serializable;

import server.psql.auth.User;

public record NetCommandAuth(String command, Object arg, User user) implements Serializable { }