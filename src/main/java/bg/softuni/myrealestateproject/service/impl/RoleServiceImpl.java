package bg.softuni.myrealestateproject.service.impl;

import bg.softuni.myrealestateproject.model.entity.RoleEntity;
import bg.softuni.myrealestateproject.model.enums.RoleTypeEnum;
import bg.softuni.myrealestateproject.repository.RoleRepository;
import bg.softuni.myrealestateproject.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void initRoles() {
        if (this.roleRepository.count() == 0) {
            Arrays.stream(RoleTypeEnum.values())
                    .forEach(roleTypeEnum -> {
                        this.roleRepository.save(new RoleEntity(roleTypeEnum));
                    });
        }
    }
}
