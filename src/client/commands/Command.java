package client.commands;

public interface Command {
    Object execute(Object arg);
    boolean isHasArgs();
}