package server.commands;

import shared.network.models.User;

public interface Command {
    Object execute(Object arg, User user); 
    String getDescription();
    boolean isHasArgs();
    default String stringArgument(){
        return "";
    }
    default boolean isHiddenCommand(){
        return false;
    }
}