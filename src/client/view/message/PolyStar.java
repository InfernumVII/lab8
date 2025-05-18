package client.view.message;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class PolyStar extends Group { //https://stackoverflow.com/questions/40663087/java-drawing-a-star-and-connecting-points-w-drawing-panel
    public PolyStar(int RADIUS, int nSpikes, double SPIKINESS, Color col){
        int ind = 0;
        int nPoints = nSpikes * 2 + 1;
        double[] points = new double[nPoints * 2];
        for (int i = 0; i < nPoints ; i++) {
            double iRadius = (i % 2 == 0) ? RADIUS : (RADIUS * SPIKINESS);
            double angle = (i * 360.0) / (2 * nSpikes);
            points[ind] = iRadius * Math.cos(Math.toRadians(angle - 90));
            points[ind + 1] = iRadius * Math.sin(Math.toRadians(angle - 90));
            ind += 2;
        }
        Polygon StarPoly = new Polygon(points);
        StarPoly.setFill(col);
        getChildren().add(StarPoly);
    }
}