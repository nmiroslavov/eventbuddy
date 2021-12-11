package bg.mycompany.eventbuddy.service;

import bg.mycompany.eventbuddy.model.view.StatsView;

public interface StatsService {
    void onRequest();
    StatsView getStats();
}
