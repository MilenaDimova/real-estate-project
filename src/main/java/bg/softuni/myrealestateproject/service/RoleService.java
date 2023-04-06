package bg.softuni.myrealestateproject.service;

import bg.softuni.myrealestateproject.model.entity.RoleEntity;
import bg.softuni.myrealestateproject.model.enums.RoleTypeEnum;
import bg.softuni.myrealestateproject.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RoleService implements DataBaseInitService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void dbInit() {
        Arrays.stream(RoleTypeEnum.values())
                .forEach(roleTypeEnum -> {
                    this.roleRepository.save(new RoleEntity(roleTypeEnum));
                });
    }

    @Override
    public boolean isDbInit() {
        return this.roleRepository.count() == 0;
    }

    public RoleEntity findRoleEntityByRoleType(RoleTypeEnum roleTypeEnum) { return this.roleRepository.findRoleEntityByRoleType(roleTypeEnum); }

    public List<RoleEntity> findAll() {
        return this.roleRepository.findAll();
    }
}
