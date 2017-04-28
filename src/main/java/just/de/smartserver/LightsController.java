package just.de.smartserver;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;
import com.pi4j.io.gpio.RaspiPin;
import de.pi3g.pi.rcswitch.RCSwitch;
import just.de.smartserver.model.AlexaResponse;
import just.de.smartserver.model.LightResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.BitSet;


@RestController
@RequestMapping("/lights")
public class LightsController {

    private BitSet address = RCSwitch.getSwitchGroupAddress("11111");   // Hauscode
    private RCSwitch transmitter = new RCSwitch(RaspiPin.GPIO_17);

    public LightsController() {
        System.out.println("Using " + RaspiPin.GPIO_17);
    }

    @PostMapping(value = "/on", produces = "application/json")
    @ResponseBody
    public AlexaResponse turnOn(@RequestParam(value = "name") String name) throws IOException {
        transmitter.switchOn(address, 1); //switches the switch unit A on
        String callbatch = "/home/pi/SmartServer/turnLightOn.sh";
        System.out.println(callbatch);
        Runtime rt = Runtime.getRuntime();
        Process proc = rt.exec(callbatch);
        return new AlexaResponse(turnLightOn());
    }

    @PostMapping("/off")
    public String turnOff(@RequestParam(value = "name") String name) throws IOException {
        transmitter.switchOff(address, 1); //switches the switch unit A off
        String callbatch = "/home/pi/SmartServer/turnLightOff.sh";
        System.out.println(callbatch);
        Runtime rt = Runtime.getRuntime();
        Process proc = rt.exec(callbatch);
        return "Turn light off for " + name;
    }

    @PostMapping(value = "", produces = "application/json")
    public LightResponse index() {
        return new LightResponse("Index");
    }

    /**
     * Creates a {@code SpeechletResponse} for the hello intent.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse turnLightOn() {
        String speechText = "Hello world";

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("HelloWorld");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        SpeechletResponse speechletResponse = SpeechletResponse.newTellResponse(speech, card);
        System.out.println(speechletResponse);
        return speechletResponse;
    }
}
