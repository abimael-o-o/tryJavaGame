import java.util.ArrayList;
import java.awt.Color;

public class DataOfSquare {
    ArrayList<Color> C = new ArrayList<Color>();
    int color = 0;
    SquarePanel squarePanel;

    public DataOfSquare(int col){
        C.add(Color.darkGray);
        C.add(Color.green);
        C.add(Color.white);
        color = col;
        squarePanel = new SquarePanel(C.get(color));
    }
    public void lightMeUp(int c){
        squarePanel.ChangeColor(C.get(c));
    }
}
