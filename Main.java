package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    static boolean isClicked=false;
    static double x1, y1;
    static int counter=0, width=600, height=600, gravity=3, numberOfEverything=10000, mode=0,
            counterLeft = 0, counterRight = 0;
    static Canvas cnv = new Canvas(width, height);
    static GraphicsContext graph = cnv.getGraphicsContext2D();
    static int[] x = new int[numberOfEverything];
    static int[] stuckPerimeter = new int[width/5+1];
    static int[] stuckPerimeter2 = new int[width/5+1];
    static double[] y = new double[numberOfEverything];
    static double[] vy = new double[numberOfEverything];
    static boolean[] isStuck = new boolean[numberOfEverything];
    static int[] whichMode = new int[numberOfEverything];



    @Override
    public void start(Stage primaryStage) throws Exception{
        AnchorPane root = new AnchorPane();
        root.getChildren().add(cnv);
        cnv.setVisible(true);
        graph.setStroke(Color.SANDYBROWN);
        graph.setLineWidth(5);



        Scene scene = new Scene(root, width, height);
        scene.setFill(Color.BEIGE);

        scene.setOnMousePressed(event -> {
            isClicked=true;
            x1= event.getX();
            y1= event.getY();
        });

        scene.setOnMouseReleased(event -> isClicked=false);
        scene.setOnMouseDragged(event -> {
            x1= event.getX();
            y1= event.getY();
        });

        scene.setOnKeyPressed(event -> {
            if(event.getCode()== KeyCode.UP)
            {
                if(mode!=1) {
                    mode++;
                }
                else
                {
                    mode=0;
                }
            }
        });

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            graph.clearRect(0,0, cnv.getWidth(), cnv.getHeight());
            for(int i=0; i<counter; i++)
            {
                if(whichMode[i]==0) {
                    graph.setStroke(Color.SANDYBROWN);
                    if (!isStuck[i]) {
                        if (y[i] < cnv.getHeight() - 3 - stuckPerimeter[x[i] / 5]) {
                            vy[i] += gravity;
                            y[i] += vy[i];
                        }
                        if (y[i] > cnv.getHeight() - 3 - stuckPerimeter[x[i] / 5]) {
                            stuckPerimeter[x[i] / 5] += 5;
                            int number = x[i] / 5;
                            if (stuckPerimeter[x[i] / 5] - stuckPerimeter[x[i] / 5 - 1] > 5) {
                                while (stuckPerimeter[x[i] / 5] - stuckPerimeter[x[i] / 5 - 1] >= 5 && x[i] > 5) {
                                    x[i] -= 5;
                                }
                            } else if (stuckPerimeter[x[i] / 5] - stuckPerimeter[x[i] / 5 + 1] > 5) {
                                while (stuckPerimeter[x[i] / 5] - stuckPerimeter[x[i] / 5 + 1] >= 5 && x[i] < cnv.getWidth() - 6) {
                                    x[i] += 5;
                                }
                            }
                            stuckPerimeter[x[i] / 5] += 5;
                            if(stuckPerimeter2[x[i]/5]!=0)
                            {
                                stuckPerimeter2[x[i]/5]-=5;
                            }
                            stuckPerimeter[number] -= 5;
                            vy[i] = 0;
                            y[i] = cnv.getHeight() - 3 - stuckPerimeter[x[i] / 5];
                            isStuck[i] = true;

                        }
                    }
                }
                if(whichMode[i]==1) {
                    graph.setStroke(Color.AQUA);
                    if (!isStuck[i]) {
                        if (y[i] < cnv.getHeight() - (8 + stuckPerimeter[x[i] / 5]+stuckPerimeter2[x[i]/5])) {
                            vy[i] += gravity;
                            y[i] += vy[i];
                        }
                        if (y[i] > cnv.getHeight() - (8 + stuckPerimeter[x[i] / 5]+stuckPerimeter2[x[i]/5])) {
                            if (stuckPerimeter2[x[i] / 5] != 0) {
                                int number = x[i] / 5;
                                counterLeft = 0;
                                counterRight = 0;
                                for (int j = 1; j < number; j++) {
                                    if (stuckPerimeter2[number-(j-1)]+stuckPerimeter[number-(j-1)] == stuckPerimeter[number - j]+stuckPerimeter2[number-j]) {
                                        counterLeft++;
                                    }
                                    else if (stuckPerimeter2[number-j+1]+stuckPerimeter[number-j+1] < stuckPerimeter[number - j]+stuckPerimeter[number-j]
                                            || stuckPerimeter2[number-j+1]+stuckPerimeter[number-j+1]-(stuckPerimeter2[number-j]+stuckPerimeter[number - j]) >= 5) {
                                        break;
                                    }
                                    else if (j+1 == number) {
                                        counterLeft=0;
                                        break;
                                    }
                                }
                                for (int j = 1; j < width - number; j++) {
                                    if (j + 1 == width - number || number+j >120) {
                                        counterRight=0;
                                        break;
                                    }
                                    else if (stuckPerimeter2[number+j-1]+stuckPerimeter[number+j-1] == stuckPerimeter[number + j]+stuckPerimeter2[number + j]) {
                                        counterRight++;
                                    } else if (stuckPerimeter2[number+j-1]+stuckPerimeter[number+j-1] < stuckPerimeter[number + j]+stuckPerimeter[number+j]
                                            || stuckPerimeter2[number+j-1]+stuckPerimeter[number+j-1]-(stuckPerimeter2[number+j]+stuckPerimeter[number + j]) >= 5) {
                                        break;
                                    }
                                }
                                System.out.println(counterLeft + ",");
                                System.out.println(counterRight);

                                boolean hey=false;
                                int number2=x[i];
                                if (counterLeft <= counterRight) {
                                    while(!hey)
                                    {
                                        if(stuckPerimeter2[x[i]/5]+stuckPerimeter[x[i]/5]>=stuckPerimeter[x[i]/5-1]+stuckPerimeter2[x[i]/5-1]
                                        && x[i]>5)
                                        {
                                            x[i]-=5;
                                        }
                                        else
                                        {
                                            hey=true;
                                        }
                                    }
                                    while(!isStuck[i])
                                    {
                                        if(stuckPerimeter2[x[i]/5]+stuckPerimeter[x[i]/5]==stuckPerimeter2[x[i]/5+1]+stuckPerimeter[x[i]/5+1] && stuckPerimeter2[x[i]/5]>=stuckPerimeter[x[i]/5+1])
                                        {
                                            if(x[i]==number2)
                                            {
                                                isStuck[i]=true;
                                            }
                                            x[i]+=5;
                                        }
                                        else
                                        {
                                            isStuck[i]=true;
                                        }
                                    }
                                }
                                else {
                                    while(!hey)
                                    {
                                        if(stuckPerimeter2[x[i]/5]+stuckPerimeter[x[i]/5]>=stuckPerimeter[x[i]/5+1]+stuckPerimeter2[x[i]/5+1]
                                                && x[i]< cnv.getWidth()-5)
                                        {
                                            x[i]+=5;
                                        }
                                        else
                                        {
                                            hey=true;
                                        }
                                    }
                                    while(!isStuck[i])
                                    {
                                        if(stuckPerimeter2[x[i]/5]==stuckPerimeter2[x[i]/5-1] && stuckPerimeter2[x[i]/5]>=stuckPerimeter[x[i]/5-1])
                                        {
                                            if(x[i]==number2)
                                            {
                                            System.out.println("br");
                                            isStuck[i]=true;
                                            }
                                            x[i]-=5;
                                        }
                                        else
                                        {
                                            isStuck[i]=true;
                                        }
                                    }
                                }
                            }
                            vy[i] = 0;
                            y[i] = cnv.getHeight() - (8 + stuckPerimeter[x[i] / 5]+stuckPerimeter2[x[i]/5]);
                            stuckPerimeter2[x[i] / 5]+=5;
                            isStuck[i]=true;
                        }
                    }
                }
                graph.beginPath();
                graph.lineTo(x[i], y[i]);
                graph.stroke();
            }
            }));

        Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            if(isClicked && x1>5 && x1<cnv.getWidth()-5){
                if(mode==0)
                {
                    graph.setStroke(Color.SANDYBROWN);
                }
                else if (mode==1)
                {
                    graph.setStroke(Color.AQUA);
                }
                graph.beginPath();
                graph.lineTo(x1 -(x1 % 5), y1 - (y1 % 5));
                graph.stroke();
                x[counter]=(int)(x1 - (x1 % 5));
                y[counter]=(y1 - (y1 % 5));
                whichMode[counter]=mode;
                counter++;
                System.out.println(counter);
            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        timeline1.setCycleCount(Animation.INDEFINITE);
        timeline1.play();

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("sand");
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
