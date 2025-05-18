package shared.network.models;

import java.io.Serializable;
import java.time.LocalDate;

public record Info(String collectionType, LocalDate initTime, int size) implements Serializable {};
