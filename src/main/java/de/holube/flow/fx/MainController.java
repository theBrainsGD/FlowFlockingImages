package de.holube.flow.fx;

import de.holube.flow.fx.util.PlatformExt;
import de.holube.flow.model.Boid;
import de.holube.flow.model.Field;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class MainController {

    @FXML
    private AnchorPane parent;

    @FXML
    private Pane canvasParent;
    @FXML
    private Canvas canvas;
    @FXML
    private Canvas debugCanvas;

    private double canvasRatio;


    @FXML
    private Text fps;

    public void setCanvasSize(int width, int height) {
        // TODO make this option available to the user
        canvas.setWidth(width);
        canvas.setHeight(height);
        debugCanvas.setWidth(width);
        debugCanvas.setHeight(height);
        canvasRatio = canvas.getWidth() / canvas.getHeight();
    }

    @FXML
    private void initialize() {
        canvasRatio = canvas.getWidth() / canvas.getHeight();

        canvasParent.widthProperty().addListener((observable, oldValue, newValue) -> resizeCanvas());
        canvasParent.heightProperty().addListener((observable, oldValue, newValue) -> resizeCanvas());
    }

    public void update(Field field, int fps) {
        PlatformExt.runAndWait(() -> {
            PixelWriter pw = canvas.getGraphicsContext2D().getPixelWriter();
            for (Boid boid : field.getBoids()) {
                pw.setColor((int) boid.getPosition().getX(), (int) boid.getPosition().getY(), DrawingConfiguration.getBoidColor(boid));
            }

            GraphicsContext gc = debugCanvas.getGraphicsContext2D();
            gc.setFill(Color.TRANSPARENT);
            gc.clearRect(0, 0, debugCanvas.getWidth(), debugCanvas.getHeight());
            field.drawDebug(gc, 2);

            this.fps.setText(String.format("%d FPS", fps));
        });
    }

    private void resizeCanvas() {
        double calculatedWidth;
        double calculatedHeight;
        double calculatedScaleX;
        double calculatedScaleY;

        if (canvasParent.getWidth() / canvasParent.getHeight() < canvasRatio) {
            calculatedWidth = canvasParent.getWidth();
            calculatedHeight = canvasParent.getWidth() / canvasRatio;
        } else {
            calculatedWidth = canvasParent.getHeight() * canvasRatio;
            calculatedHeight = canvasParent.getHeight();
        }

        calculatedScaleX = calculatedWidth / canvas.getWidth();
        calculatedScaleY = calculatedHeight / canvas.getHeight();

        canvas.setScaleX(calculatedScaleX);
        canvas.setScaleY(calculatedScaleY);
        canvas.setLayoutX((calculatedWidth - canvas.getWidth()) / 2);
        canvas.setLayoutY((calculatedHeight - canvas.getHeight()) / 2);

        debugCanvas.setScaleX(calculatedScaleX);
        debugCanvas.setScaleY(calculatedScaleY);
        debugCanvas.setLayoutX((calculatedWidth - debugCanvas.getWidth()) / 2);
        debugCanvas.setLayoutY((calculatedHeight - debugCanvas.getHeight()) / 2);
    }

}