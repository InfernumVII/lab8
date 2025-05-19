package client.view.message;

public enum MessageColor {
    DEFAULT("#ffffff"),
    ERROR("#dc143c");
    
    private final String hexCode;

    MessageColor(String hexCode) {
        this.hexCode = hexCode;
    }

    public String getHexCode() {
        return hexCode;
    }
}