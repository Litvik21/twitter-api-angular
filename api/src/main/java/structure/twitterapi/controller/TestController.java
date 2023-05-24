package structure.twitterapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import structure.twitterapi.model.Role;
import structure.twitterapi.service.RoleService;

@RestController
@RequestMapping("/test")
public class TestController {
    private final RoleService roleService;

    public TestController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public String saveRoles() {
        Role user = new Role();
        user.setRoleName(Role.RoleName.USER);
        roleService.save(user);

        Role admin = new Role();
        admin.setRoleName(Role.RoleName.ADMIN);
        roleService.save(admin);

        return "Done!";
    }
}
