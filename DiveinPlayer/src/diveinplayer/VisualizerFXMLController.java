/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diveinplayer;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class VisualizerFXMLController implements Initializable {

    @FXML
    private Pane visualPane;
    public Pane pane;
    HBox hbox = new HBox();
    String path = "E:\\songs\\Against The Current- Roses.mp3";
    Media media = new Media(new File(path).toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(media);
    Line line = new Line();
    final int bands = mediaPlayer.getAudioSpectrumNumBands();
    final Rectangle[] rects = new Rectangle[bands];
    final Rectangle[] border = new Rectangle[bands - 1];
    int w = mediaPlayer.getMedia().getWidth();
    int h = mediaPlayer.getMedia().getHeight();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dshape();
        mediaPlayer.setAudioSpectrumListener(new AudioSpectrumListener(){
            @Override
            public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
                
                for(int i =0; i < rects.length;i++){
                    double h = magnitudes[i]+60;
                    if(h>2){
                        rects[i].setHeight(h);
                    }
                }
            }
        });
    }

    public void dshape() {
        
        pane.getChildren().add(hbox);
        int x = 1;
        for(int i = 0 ; i<rects.length;i++){
            rects[i] = new Rectangle();
            rects[i].setX(x*(i+1));
            rects[i].setFill(Color.BLACK);
            //hbox.getChildren().add(rects[i]);
            
        }

        double bandwidth = pane.getWidth()/rects.length;
        double s = bandwidth;
        int i = 1;
        System.out.println(bandwidth);
        for(int j = 0; j < rects.length/8; j++){
            rects[j].setWidth(7);
            rects[j].setHeight(2);
            i+=5;
        }
        hbox.getChildren().addAll(Arrays.asList(rects));
        for(Node node : pane.getChildren()){
            System.out.println(node.toString());
        }
        for(Node n : hbox.getChildren()){
            System.out.println(n.toString());
        }
    }    
    
}
