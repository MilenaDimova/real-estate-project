package bg.softuni.myrealestateproject.service;

import bg.softuni.myrealestateproject.model.entity.StatusEntity;
import bg.softuni.myrealestateproject.model.enums.StatusTypeEnum;
import bg.softuni.myrealestateproject.repository.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class StatusService implements DataBaseInitService{
    private final StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public void dbInit() {
        Arrays.stream(StatusTypeEnum.values())
                .forEach(statusTypeEnum -> {
                    this.statusRepository.save(new StatusEntity(statusTypeEnum));
                });
    }

    @Override
    public boolean isDbInit() {
        return this.statusRepository.count() == 0;
    }

    public StatusEntity findStatusPending() {
        return this.statusRepository.findByStatusType(StatusTypeEnum.PENDING);
    }
}
