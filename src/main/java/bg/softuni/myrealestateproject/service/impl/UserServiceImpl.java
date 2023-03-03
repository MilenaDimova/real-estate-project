package bg.softuni.myrealestateproject.service.impl;

import bg.softuni.myrealestateproject.model.entity.UserEntity;
import bg.softuni.myrealestateproject.model.service.UserServiceModel;
import bg.softuni.myrealestateproject.repository.UserRepository;
import bg.softuni.myrealestateproject.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean tryToRegister(UserServiceModel userServiceModel) {
        boolean isValid = true;
        Optional<UserEntity> byUsername = this.userRepository.findByUsername(userServiceModel.getUsername());
        if (byUsername.isPresent()) {
            isValid = false;
        }

        Optional<UserEntity> byEmail = this.userRepository.findByEmail(userServiceModel.getEmail());
        if (byEmail.isPresent()) {
            isValid = false;
        }

        return isValid;
    }

    @Override
    public boolean passwordsMatched(UserServiceModel userServiceModel) {
        return userServiceModel.getPassword().equals(userServiceModel.getConfirmPassword());
    }

    @Override
    public UserServiceModel register(UserServiceModel userServiceModel) {
        UserEntity user = this.modelMapper.map(userServiceModel, UserEntity.class);

        return this.modelMapper.map(this.userRepository.save(user), UserServiceModel.class);
    }

    @Override
    public UserServiceModel findByEmailAndPassword(String email, String password) {
        return this.userRepository.findByEmailAndPassword(email, password)
                .map(user -> this.modelMapper.map(user, UserServiceModel.class))
                .orElse(null);
    }

    @Override
    public UserEntity findById(Long ownerId) {
        return this.userRepository.findById(ownerId).orElse(null);
    }

}
