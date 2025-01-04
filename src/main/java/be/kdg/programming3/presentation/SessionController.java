package be.kdg.programming3.presentation;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Class for applying and log sessions.
 */
@Controller
public class SessionController {
    private static final Logger logger = LoggerFactory.getLogger(SessionController.class);

    @GetMapping("/session-history")
    public String getSessionHistory(HttpSession session, Model model) {
        List<String> visitHistory = (List<String>) session.getAttribute("visitHistory");
        model.addAttribute("visitHistory", visitHistory);
        logger.info("Accessed combined session history page with visit history: {}", visitHistory);
        return "session-history"; // returns the session-history.html template
    }
}
