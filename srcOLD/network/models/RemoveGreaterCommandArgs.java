package network.models;

import java.io.Serializable;

public record RemoveGreaterCommandArgs(long x, long y) implements Serializable { }