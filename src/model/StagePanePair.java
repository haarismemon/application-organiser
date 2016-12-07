package model;

import javafx.scene.layout.Pane;

/**
 * Created by Haaris on 07/12/2016.
 */
public class StagePanePair {

    private ApplicationStage stage;
    private Pane pane;

    public StagePanePair(ApplicationStage stage, Pane pane) {
        this.stage = stage;
        this.pane = pane;
    }

    public ApplicationStage getStage() {
        return stage;
    }

    public void setStage(ApplicationStage stage) {
        this.stage = stage;
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public void clear() {
        stage = null;
        pane = null;
    }

    public boolean isEmpty() {
        if(stage == null && pane == null) {
            return true;
        } else return false;
     }

     public void put(ApplicationStage stage, Pane pane) {
         this.stage = stage;
         this.pane = pane;
     }
}
