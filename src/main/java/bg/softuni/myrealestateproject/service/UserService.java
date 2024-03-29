package bg.softuni.myrealestateproject.service;

import bg.softuni.myrealestateproject.exception.ObjectNotFoundException;
import bg.softuni.myrealestateproject.model.binding.AdminUpdateProfileBindingModel;
import bg.softuni.myrealestateproject.model.binding.UpdateProfileBindingModel;
import bg.softuni.myrealestateproject.model.binding.UserRegisterBindingModel;
import bg.softuni.myrealestateproject.model.entity.RoleEntity;
import bg.softuni.myrealestateproject.model.entity.UserEntity;
import bg.softuni.myrealestateproject.model.enums.RoleTypeEnum;
import bg.softuni.myrealestateproject.model.view.OfferViewModel;
import bg.softuni.myrealestateproject.model.view.OwnerViewModel;
import bg.softuni.myrealestateproject.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class UserService implements DataBaseInitService {
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private  final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, UserDetailsService userDetailsService, RoleService roleService, PasswordEncoder passwordEncoder, ImageService imageService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.imageService = imageService;
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
                setRoles(this.roleService.findAll());

        userRepository.save(admin);
    }

    @Override
    public boolean isDbInit() {
        return this.userRepository.count() == 0;
    }

    public void registerUser(UserRegisterBindingModel userRegisterBindingModel, Consumer<Authentication> successfulLoginProcessor) {

        UserEntity userEntity = new UserEntity()
                .setFirstName(userRegisterBindingModel.getFirstName())
                .setLastName(userRegisterBindingModel.getLastName())
                .setEmail(userRegisterBindingModel.getEmail())
                .setPassword(passwordEncoder.encode(userRegisterBindingModel.getPassword()))
                .setPhoneNumber(userRegisterBindingModel.getPhoneNumber())
                .setRoles(List.of(this.roleService.findRoleEntityByRoleType(RoleTypeEnum.USER)));

        userRepository.save(userEntity);

        successfulLoginProcessor.accept(this.getAuthenticationToken(userRegisterBindingModel.getEmail()));
    }

    private Authentication getAuthenticationToken(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        return authentication;
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

    public OwnerViewModel getUserProfileInformation(String email) {
        return this.userRepository.findByEmail(email)
                .map(userEntity -> {
                    int activeOffers = userEntity.getOffers().stream()
                            .filter(o -> o.getStatus().getStatusType().name().equals("ACTIVE")).toList()
                            .size();

                    OwnerViewModel ownerViewModel = this.modelMapper.map(userEntity, OwnerViewModel.class);
                    ownerViewModel.setFirstName(userEntity.getFirstName());
                    ownerViewModel.setLastName(userEntity.getLastName());
                    ownerViewModel.setEmail(userEntity.getEmail());
                    ownerViewModel.setPhoneNumber(ownerViewModel.getPhoneNumber());
                    ownerViewModel.setCountActiveOffer(activeOffers);

                    return ownerViewModel;
                }).orElse(null);
    }

    public boolean existUserByEmail(String bindingEmail, String currentUserEmail) {
        return this.userRepository.existsByEmail(bindingEmail) && !bindingEmail.equals(currentUserEmail);
    }

    public boolean existUserByPhoneNumber(String bindingPhoneNumber, String currentUserPhoneNumber) {
        return this.userRepository.existsByPhoneNumber(bindingPhoneNumber) && !bindingPhoneNumber.equals(currentUserPhoneNumber);
    }

    public void updateProfile(UpdateProfileBindingModel updateProfileBindingModel, Long userDetailsId) {
        UserEntity currentUser = this.userRepository.findById(userDetailsId).get();
        currentUser.setEmail(updateProfileBindingModel.getEmail());
        currentUser.setFirstName(updateProfileBindingModel.getFirstName());
        currentUser.setLastName(updateProfileBindingModel.getLastName());
        currentUser.setPhoneNumber(updateProfileBindingModel.getPhoneNumber());

        this.userRepository.saveAndFlush(currentUser);

        SecurityContextHolder.getContext().setAuthentication(this.getAuthenticationToken(currentUser.getEmail()));
    }

    public List<OfferViewModel> getUserProfileOffers(String email) {
        UserEntity userEntity = this.userRepository.findByEmail(email).get();
        return userEntity.getOffers()
                .stream()
                .map(offerEntity -> {
                    OfferViewModel offerViewModel = this.modelMapper.map(offerEntity, OfferViewModel.class);
                    offerViewModel.setOfferType(offerEntity.getOfferType().getOfferType());
                    offerViewModel.setDescription(offerEntity.getDescription());
                    offerViewModel.setImagesIds(this.imageService.getImagesIds(offerEntity.getId()));

                    return offerViewModel;
                }).toList();
    }

    public UpdateProfileBindingModel findUpdateProfileBindingModelByEmail(String email) {
        Optional<UserEntity> userByEmail = this.userRepository.findByEmail(email);
        if (userByEmail.isEmpty()) {
            throw new ObjectNotFoundException("Email " + email + " not found!");
        }

        UserEntity userEntity = userByEmail.get();

        return new UpdateProfileBindingModel().setId(userEntity.getId())
                .setEmail(userEntity.getEmail())
                .setFirstName(userEntity.getFirstName())
                .setLastName(userEntity.getLastName())
                .setPhoneNumber(userEntity.getPhoneNumber());
    }

    public AdminUpdateProfileBindingModel findUserById(Long id) {
        return this.userRepository.findById(id)
                .map(userEntity -> {
                    AdminUpdateProfileBindingModel adminUpdateProfileBindingModel = this.modelMapper.map(userEntity, AdminUpdateProfileBindingModel.class);
                    List<RoleTypeEnum> userRoles = userEntity.getRoles().stream().map(RoleEntity::getRoleType).toList();

                    adminUpdateProfileBindingModel.setEmail(userEntity.getEmail());
                    adminUpdateProfileBindingModel.setFirstName(userEntity.getFirstName());
                    adminUpdateProfileBindingModel.setLastName(userEntity.getLastName());
                    adminUpdateProfileBindingModel.setPhoneNumber(userEntity.getPhoneNumber());
                    adminUpdateProfileBindingModel.setRole(userRoles.contains(RoleTypeEnum.ADMIN) ? RoleTypeEnum.ADMIN : RoleTypeEnum.USER);

                    return adminUpdateProfileBindingModel;
                }).orElseThrow(() -> new ObjectNotFoundException("User with id " + id + " was not found!"));
    }


    public void adminUpdateProfile(AdminUpdateProfileBindingModel adminUpdateProfileBindingModel, AdminUpdateProfileBindingModel baseUser) {
        UserEntity userEntity = this.userRepository.findByEmail(baseUser.getEmail()).get();

        userEntity.setEmail(adminUpdateProfileBindingModel.getEmail());
        userEntity.setFirstName(adminUpdateProfileBindingModel.getFirstName());
        userEntity.setLastName(adminUpdateProfileBindingModel.getLastName());
        userEntity.setPhoneNumber(adminUpdateProfileBindingModel.getPhoneNumber());

        List<RoleEntity> userRoles = new ArrayList<>();
        userRoles.add(this.roleService.findRoleEntityByRoleType(RoleTypeEnum.USER));
        if (adminUpdateProfileBindingModel.getRole() == RoleTypeEnum.ADMIN) {
            userRoles.add(this.roleService.findRoleEntityByRoleType(RoleTypeEnum.ADMIN));
        }

        userEntity.setRoles(userRoles);
        this.userRepository.saveAndFlush(userEntity);
    }

    public boolean existByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public boolean existByPhoneNumber(String phoneNumber) {
        return this.userRepository.existsByPhoneNumber(phoneNumber);
    }

    public boolean passwordsMatcher(UserRegisterBindingModel userRegisterBindingModel) {
        return !userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword());
    }
}
