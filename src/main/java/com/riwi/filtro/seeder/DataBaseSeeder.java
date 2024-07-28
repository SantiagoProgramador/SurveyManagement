package com.riwi.filtro.seeder;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.riwi.filtro.domain.entities.Role;
import com.riwi.filtro.domain.entities.User;
import com.riwi.filtro.domain.repositories.RoleRepository;
import com.riwi.filtro.domain.repositories.UserRepository;

@Component
public class DataBaseSeeder implements CommandLineRunner {
  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) throws Exception {
    if (roleRepository.count() == 0) {
      Role adminRole = new Role();
      adminRole.setName("ROLE_ADMIN");
      roleRepository.save(adminRole);
      Role userRole = new Role();
      userRole.setName("ROLE_USER");
      roleRepository.save(userRole);
    }

    if (userRepository.findByUserName("admin") == null) {
      User adminUser = new User();
      adminUser.setUsername("admin");
      adminUser.setPassword(passwordEncoder.encode("adminPassword"));
      Set<Role> adminRoles = new HashSet<>();
      adminRoles.add(roleRepository.findByName("ROLE_ADMIN"));
      adminUser.setRoles(adminRoles);
      userRepository.save(adminUser);

    }
    System.out.println("Database seeding completed.");
  }
}
