package com.riwi.filtro.seeder;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.riwi.filtro.domain.persistence.RoleEntity;
import com.riwi.filtro.domain.persistence.UserEntity;
import com.riwi.filtro.domain.repositories.RoleRepository;
import com.riwi.filtro.domain.repositories.UserRepository;

import org.springframework.transaction.annotation.Transactional;

@Component
public class DataBaseSeeder implements CommandLineRunner {
  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public void run(String... args) throws Exception {
    if (roleRepository.count() == 0) {
      RoleEntity adminRole = new RoleEntity();
      adminRole.setName("ROLE_ADMIN");
      roleRepository.save(adminRole);
      RoleEntity userRole = new RoleEntity();
      userRole.setName("ROLE_USER");
      roleRepository.save(userRole);
    }

    if (userRepository.findByUsername("admin") == null) {
      UserEntity adminUser = new UserEntity();
      adminUser.setName("ShantiAdmin");
      adminUser.setEmail("andresx1277@gmail.com");
      adminUser.setUsername("admin");
      adminUser.setPassword(passwordEncoder.encode("adminPassword"));
      Set<RoleEntity> adminRoles = new HashSet<>();
      adminRoles.add(roleRepository.findByName("ROLE_ADMIN"));
      adminUser.setRoles(adminRoles);
      userRepository.save(adminUser);

    }
    System.out.println("Database seeding completed.");
  }
}
