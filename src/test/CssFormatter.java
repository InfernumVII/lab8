package test;

class CssFormatter {
    private static final String CSS_TEMPLATE = """
        .color1 {    -fx-fill: hsb(%d, 87.86%%25, 96.85%%25);}
        .color2 {    -fx-fill: hsb(%d, 88.31%%25, 96.88%%25);}
        .color3 {    -fx-fill: hsb(%d, 53.48%%25, 51.32%%25);}
        .color4 {    -fx-fill: hsb(%d, 80.17%%25, 39.56%%25);}
        .color5 {    -fx-fill: hsb(%d, 83.15%%25, 95.68%%25);}
        .color6 {    -fx-fill: hsb(%d, 85.38%%25, 93.35%%25);}
        .color7 {    -fx-fill: hsb(%d, 84.33%%25, 74.87%%25);}
        .color8 {    -fx-fill: hsb(%d, 80.67%%25, 60.84%%25);}
        .color9 {    -fx-fill: hsb(%d, 77.34%%25, 93.75%%25);}
        .color10 {    -fx-fill: hsb(%d, 74.52%%25, 98.82%%25);}
        """;

    public static String generateCss(int hueShift) {
        return String.format(CSS_TEMPLATE,
            (20 + hueShift) % 360,
            (33 + hueShift) % 360,
            (33 + hueShift) % 360,
            (353 + hueShift) % 360,
            (14 + hueShift) % 360,
            (352 + hueShift) % 360,
            (354 + hueShift) % 360,
            (352 + hueShift) % 360,
            (4 + hueShift) % 360,
            (36 + hueShift) % 360
        );
    }
}