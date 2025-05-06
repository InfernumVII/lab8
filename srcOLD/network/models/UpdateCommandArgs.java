package network.models;

import java.io.Serializable;

import collection.Color;
import collection.DragonCharacter;
import collection.DragonType;

public record UpdateCommandArgs(int id, String name, long x, long y, Long age, Color color, DragonType type, DragonCharacter character, Float eyesCount) implements Serializable { }
