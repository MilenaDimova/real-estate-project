package bg.softuni.myrealestateproject.service;

import bg.softuni.myrealestateproject.model.entity.UserEntity;
import bg.softuni.myrealestateproject.model.service.UserServiceModel;

public interface UserService {
    boolean tryToRegister(UserServiceModel userServiceModel);

    boolean passwordsMatched(UserServiceModel userServiceModel);

    UserServiceModel register(UserServiceModel userServiceModel);

    UserServiceModel findByEmailAndPassword(String email, String password);

    UserEntity findById(Long ownerId);
}
