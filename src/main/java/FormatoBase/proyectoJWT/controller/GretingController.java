package FormatoBase.proyectoJWT.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/greeting")
public class GretingController {

    @GetMapping("/sayHelloPublic")
    public String greet() {
        return "Hello, Mundo!";
    }

    @GetMapping("/sayHelloProtected")
    public String greetProtected() {
        return "Hello, Mundo! (protegido)";
    }
}
