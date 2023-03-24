package bg.softuni.myrealestateproject.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBInit {
    private final List<DataBaseInitService> initServices;

    @Autowired
    public DBInit(List<DataBaseInitService> initServices) {
        this.initServices = initServices;
    }

    @PostConstruct
    public void postConstruct() {
        dbInit();
    }

    public void dbInit() {
        initServices
                .stream()
                .filter(DataBaseInitService::isDbInit)
                .forEach(DataBaseInitService::dbInit);
    }

}
