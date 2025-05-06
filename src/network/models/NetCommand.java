package network.models;

import java.io.Serializable;

public record NetCommand(String command, Object arg) implements Serializable { }