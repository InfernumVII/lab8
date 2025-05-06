package server.psql.exceptions;

public class BlankCreds extends Exception {
    public BlankCreds(){
        super("You must specify username and password");
    }
}
