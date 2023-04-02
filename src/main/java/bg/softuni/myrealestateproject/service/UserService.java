package bg.softuni.myrealestateproject.service;

import bg.softuni.myrealestateproject.model.binding.UserRegisterBindingModel;
import bg.softuni.myrealestateproject.model.entity.UserEntity;
import bg.softuni.myrealestateproject.model.enums.RoleTypeEnum;
import bg.softuni.myrealestateproject.model.view.OwnerViewModel;
import bg.softuni.myrealestateproject.repository.RoleRepository;
import bg.softuni.myrealestateproject.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class UserService implements DataBaseInitService {
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, UserDetailsService userDetailsService, RoleRepository roleRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }


    @Override
    public void dbInit() {
        UserEntity admin = new UserEntity().
                setFirstName("Admin").
                setLastName("Adminov").
                setEmail("admin@example.com").
                setPassword(passwordEncoder.encode("123")).
                setPhoneNumber("0899443363").
                setRoles(roleRepository.findAll());

        userRepository.save(admin);
    }

    @Override
    public boolean isDbInit() {
        return this.userRepository.count() == 0;
    }

    public void registerUser(UserRegisterBindingModel userRegisterBindingModel,
                             Consumer<Authentication> successfulLoginProcessor) {

        UserEntity userEntity = new UserEntity()
                .setFirstName(userRegisterBindingModel.getFirstName())
                .setLastName(userRegisterBindingModel.getLastName())
                .setEmail(userRegisterBindingModel.getEmail())
                .setPassword(passwordEncoder.encode(userRegisterBindingModel.getPassword()))
                .setPhoneNumber(userRegisterBindingModel.getPhoneNumber())
                .setRoles(List.of(roleRepository.findRoleEntityByRoleType(RoleTypeEnum.USER)));

        userRepository.save(userEntity);

        UserDetails userDetails = userDetailsService.loadUserByUsername(userRegisterBindingModel.getEmail());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        successfulLoginProcessor.accept(authentication);
    }

    public Optional<UserEntity> findByEmail(String username) {
        return this.userRepository.findByEmail(username);
    }

    public Page<OwnerViewModel> findAllUsers(Pageable pageable) {
        return this.userRepository.findAll(pageable)
                .map(userEntity -> {
                    List<String> outputRoles = userEntity.getRoles()
                            .stream()
                            .map((role) -> role.getRoleType().name())
                            .toList();

                    OwnerViewModel ownerViewModel = this.modelMapper.map(userEntity, OwnerViewModel.class);
                    ownerViewModel.setFirstName(userEntity.getFirstName());
                    ownerViewModel.setEmail(userEntity.getEmail());
                    ownerViewModel.setPhoneNumber(userEntity.getPhoneNumber());
                    ownerViewModel.setRoles(outputRoles);

                    return ownerViewModel;
                });
    }
}
