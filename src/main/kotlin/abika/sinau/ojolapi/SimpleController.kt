package abika.sinau.ojolapi

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class SimpleController {
    
    @GetMapping("/ping")
    fun testPing(): String {
        return "OK"
    }
}