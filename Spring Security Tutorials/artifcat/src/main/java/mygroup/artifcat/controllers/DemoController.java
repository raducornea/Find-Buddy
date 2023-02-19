package mygroup.artifcat.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    // in postman, by default Spring Security is set to use HTTP Basic -> go in Authorization and use:
    // user (only 1 pair of <user, password>)
    // b57c2ecf-a1bd-46ae-a892-02d82a726244 (generatedPassword)
    // Authorization: <Basic encode64("user:generatedPassword")>
    @GetMapping("/hello")
    public String Hello() {
        return "Hello!";
    }

    /**
     * // Encoding
     * - function always possible to reverse (it can use secrets, not necessarily, but can)
     * - 1 3 4 5 -> 5 4 3 1 (e o functie de transformare (regula de codificare e reverse))
     * - A B C D E -> Z A D E F => B C D E -> A D E F (?)
     *
     * // Encrypt
     * - transform input -> output
     * - but to go from output -> input + SECRET (ALWAYS!!!! not everyone knows it)
     * - Secret == Key
     *
     * // Hash Function
     * - input -> output
     * - output -/-> input. NEVER COULD YOU DO THAT!!!!
     * - input -> output => input ~ output (?)
     * - passwords are hashed
     *
     */
}
