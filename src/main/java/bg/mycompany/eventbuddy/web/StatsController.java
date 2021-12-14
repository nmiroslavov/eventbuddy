package bg.mycompany.eventbuddy.web;

import bg.mycompany.eventbuddy.service.StatsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/statistics")
    public String getStatsPage(Model model) {
        model.addAttribute("stats", statsService.getStats());

        return "statistics";
    }
}
