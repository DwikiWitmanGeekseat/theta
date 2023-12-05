package au.com.geekseat.theta.controller;

import au.com.geekseat.theta.model.Role;
import au.com.geekseat.theta.repository.RoleRepository;
import au.com.geekseat.theta.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    private final RoleService roleService;
    private final RoleRepository roleRepository;

    public RoleController(RoleService roleService, RoleRepository roleRepository) {
        this.roleService = roleService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public ResponseEntity<Iterable<Role>> findAll() {
        return ResponseEntity.ok(roleRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Role> save(@RequestBody Role role) {
        return ResponseEntity.ok(roleRepository.save(role));
    }

    @PostMapping("/to/person")
    public ResponseEntity<?> addRoleToPerson(@RequestParam String username, @RequestParam String roleName) {
        roleService.addRoleToPerson(username, roleName);
        return ResponseEntity.ok().build();
    }
}
