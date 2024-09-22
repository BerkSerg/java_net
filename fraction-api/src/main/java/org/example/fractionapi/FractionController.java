package org.example.fractionapi;

import org.example.fractionapi.entity.Fraction;
import org.example.fractionapi.entity.BooleanResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class FractionController {

    @GetMapping("/isfractionright")
    public BooleanResult testFraction(@RequestBody Map<String , Fraction> fractionList) {
        return new BooleanResult(fractionList.get("first").isRightFraction());
    }

    @GetMapping("/cutfraction")
    public Fraction cutFraction(@RequestBody Map<String , Fraction> fractionList) {
        return fractionList.get("first").cutFraction();
    }
    @GetMapping("/addfractions")
    public Fraction addFraction(@RequestBody Map<String , Fraction> fractionList) {
        return fractionList.get("first").addFraction(fractionList.get("second"));
    }

    @GetMapping("/decfractions")
    public Fraction decFraction(@RequestBody Map<String , Fraction> fractionList) {
        return fractionList.get("first").decFraction(fractionList.get("second"));
    }
    @GetMapping("/multiplyfractions")
    public Fraction multiplyFraction(@RequestBody Map<String , Fraction> fractionList) {
        return fractionList.get("first").multiplyFraction(fractionList.get("second"));
    }

    @GetMapping("/divfractions")
    public Fraction divFraction(@RequestBody Map<String , Fraction> fractionList) {
        return fractionList.get("first").divFraction(fractionList.get("second"));
    }

}
