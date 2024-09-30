package pl.crafthype.core.user;

public class UserSaveTask implements Runnable {

    private final UserRepository userRepository;
    private final UserService userService;

    public UserSaveTask(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public void run() {
        for (User user : this.userService.users()) {
            this.userRepository.save(user);
        }
    }
}
