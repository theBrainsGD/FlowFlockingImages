package de.holube.flow.fx;

import de.holube.flow.model.Boid;
import javafx.scene.paint.Color;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DrawingConfiguration {

    public static Color getBoidColor(Boid boid) {
        return Color.BLUE;
    }

}
