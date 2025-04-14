package vnua.kltn.herb.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vnua.kltn.herb.entity.User;
import vnua.kltn.herb.repository.UserRepository;
import vnua.kltn.herb.service.SecurityService;

@Service("securityService")
@AllArgsConstructor
@Slf4j
public class SecurityServiceImpl implements SecurityService {
    private final UserRepository userRepo;

    @Override
    public boolean isCurrentUser(Long id) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }

        String username = authentication.getName();
        User user = userRepo.findByUsername(username).orElse(null);

        return user != null && user.getUsername().equals(username);
    }
}
