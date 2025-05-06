package network;
public abstract class Settings {
    private String ip;
    private int port;
    private final String ipDefault = "0.0.0.0";
    private final int portDefault = 1111;


    public Settings(String ip, int port){
        this.ip = ip;
        this.port = port;
    }
    public Settings(String ip){
        this.ip = ip;
        this.port = portDefault;
    }
    public Settings(int port){
        this.port = port;
        this.ip = ipDefault;
    }
    public Settings(){
        this.ip = ipDefault;
        this.port = portDefault;
    }

    
    public String getIp() {
        return ip;
    }
    public int getPort() {
        return port;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public void setPort(int port) {
        this.port = port;
    }
    
}
