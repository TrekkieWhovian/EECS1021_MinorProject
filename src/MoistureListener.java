import org.firmata4j.Pin;
import org.firmata4j.ssd1306.SSD1306;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MoistureListener extends TimerTask {
    private final SSD1306 theOledObject;
    private final Pin moisturePin;
    private final Pin pumpPin;
    private final Timer timer;
    private final Pin button;
    private final Pin ledPin;
    // constructor
    public MoistureListener(Timer timer, SSD1306 theOledObject, Pin pumpPin, Pin moisturePin, Pin button, Pin ledPin) {
        this.moisturePin = moisturePin;
        this.theOledObject = theOledObject;
        this.pumpPin = pumpPin;
        this.timer = timer;
        this.button = button;
        this.ledPin = ledPin;
    }

    @Override
    public void run() {
        try {
            while(this.button.getValue() != 1){
                this.theOledObject.getCanvas().clear();
                theOledObject.getCanvas().setTextsize(2);

                if (this.moisturePin.getValue() >= 600) {
                    this.pumpPin.setValue(1);
                    this.ledPin.setValue(1);
                    this.theOledObject.getCanvas().drawString(0,0,("Moisture  Sensor: \n" + (this.moisturePin.getValue())+"\nPump's on"));
                }
                else {
                    this.pumpPin.setValue(0);
                    this.ledPin.setValue(0);
                    this.theOledObject.getCanvas().drawString(0,0,("Moisture  Sensor: \n" + (this.moisturePin.getValue())+"\nPump's off"));
                }
                this.theOledObject.display();
            }
            this.theOledObject.getCanvas().clear();
            this.theOledObject.display();
            this.ledPin.setValue(0);
            this.pumpPin.setValue(0);
            cancel();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}