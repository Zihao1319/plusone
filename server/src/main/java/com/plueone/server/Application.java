package com.plueone.server;

import java.util.HashSet;
import java.util.Set;

import com.plueone.server.models.JwtAuth.Role;
import com.plueone.server.models.JwtAuth.User;
import com.plueone.server.repo.JwtRepo.RoleRepository;
import com.plueone.server.repo.JwtRepo.UserRepository;
import com.plueone.server.service.openAI.OpenAiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private OpenAiService aiSvc;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// aiSvc.generateAnswers();
	}

	// @Bean
	// CommandLineRunner run() {
	// return args -> {

	// if (roleRepo.findByAuthority("ADMIN").isPresent())
	// return;

	// Role adminRole = roleRepo.save(new Role("ADMIN"));
	// roleRepo.save(new Role("USER"));

	// Set<Role> roles = new HashSet<>();
	// roles.add(adminRole);

	// User admin = new User("aaaaaa", "admin", "admin@gmail.com",
	// encoder.encode("password"), roles);

	// userRepo.save(admin);

	// };
	// }

}
