package bg.softuni.myrealestateproject.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTaskService {
    private final OfferService offerService;

    public ScheduledTaskService(OfferService offerService) {
        this.offerService = offerService;
    }

    //@Scheduled(cron = "0 */1 * ? * *")
    @Scheduled(cron = "0 0 6 * * ?")
    public void madeActiveOffersWithExpiredStatusAfterMonth() {
        this.offerService.setAllActiveOffersOlderThanWeekToExpired();
    }
}
