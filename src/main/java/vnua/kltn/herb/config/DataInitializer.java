package vnua.kltn.herb.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import vnua.kltn.herb.entity.User;
import vnua.kltn.herb.repository.UserRepository;

import static vnua.kltn.herb.constant.enums.UserRoleEnum.ADMIN;
import static vnua.kltn.herb.constant.enums.UserStatusEnum.ACTIVE;

@Configuration
@AllArgsConstructor
public class DataInitializer {
    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initAdminPlusAccount() {
        return args -> {
            if (userRepo.findByUsername("adminplus").isEmpty()) {
                var adminPlus = new User();
                adminPlus.setUsername("adminplus");
                adminPlus.setEmail("adminplus@gmail.com");
                adminPlus.setRoleType(ADMIN.getType());
                adminPlus.setFullName("Đinh Công Sơn");
                adminPlus.setStatus(ACTIVE.getType());
                adminPlus.setPassword(passwordEncoder.encode("adminplus"));
                adminPlus.setCreatedBy("system");
                adminPlus.setUpdatedBy("system");
                userRepo.save(adminPlus);
                System.out.println("Tài khoản admin dược tạo với tên đăng nhập: adminplus, mật khẩu: adminplus.");
            } else {
                System.out.println("Tài khoản admin đã được tạo trước đây.");
            }
        };
    }
}
