package shared.network.models;

import java.io.Serializable;

import shared.collection.Color;
import shared.collection.DragonCharacter;
import shared.collection.DragonType;

public record UpdateCommandArgs(int id, String name, long x, long y, Long age, Color color, DragonType type, DragonCharacter character, Float eyesCount) implements Serializable { }
