package shared.network.models;

import java.io.Serializable;

public record NetCommandAuth(String command, Object arg, User user) implements Serializable { }