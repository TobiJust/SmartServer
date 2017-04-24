package just.de.smartserver;

import com.pi4j.io.gpio.RaspiPin;
import de.pi3g.pi.rcswitch.RCSwitch;
import just.de.smartserver.model.LightResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.BitSet;


@RestController
@RequestMapping("/lights")
public class LightsController {

    private BitSet address = RCSwitch.getSwitchGroupAddress("11010");   // Hauscode
    private RCSwitch transmitter = new RCSwitch(RaspiPin.GPIO_00);

    @GetMapping("/on")
    public String turnOn(@RequestParam(value = "name") String name) {
        transmitter.switchOn(address, 1);   //switches the switch unit A (A = 1, B = 2, ...) on
        return "Turn light on for " + name;
    }

    @GetMapping("/off")
    public String turnOff(@RequestParam(value = "name") String name) {
        transmitter.switchOff(address, 1); //switches the switch unit A off
        return "Turn light off for " + name;
    }

    @GetMapping(value = "", produces = "application/json")
    public LightResponse index() {
        return new LightResponse("Index");
    }
}
