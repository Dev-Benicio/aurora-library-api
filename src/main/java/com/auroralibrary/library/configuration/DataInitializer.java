package com.auroralibrary.library.configuration;

import com.auroralibrary.library.model.Administrator;
import com.auroralibrary.library.repositories.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--> Verificando existência do usuário Admin...");

        String loginAdmin = "admin@aurora.com";
        String name = "Administrador";
        String senha = "admin";
        String profile_picture = "https://i.ibb.co/4W2DGKm/admin-profile.png";

        // Verifica se o admin já existe
        UserDetails usuarioExistente = administratorRepository.findByLogin(loginAdmin);

        // Se o admin ainda não existe (pode criar)
        if (usuarioExistente == null) {

            Administrator admin = new Administrator();
            admin.setLogin(loginAdmin);
            admin.setName(name);
            admin.setProfilePicture(profile_picture);
            admin.setPassword(passwordEncoder.encode(senha)); // Criptografa

            administratorRepository.save(admin);
        }

        System.out.println("\n################################################################");
        System.out.println(" BEM-VINDO AO AURORA LIBRARY - AMBIENTE DE TESTES ");
        System.out.println(" Para facilitar a avaliação das funcionalidades," +
                "\n um usuário administrador foi validado/criado automaticamente.\n");
        System.out.println(" Login: " + loginAdmin);
        System.out.println(" Senha: " + senha);
        System.out.println("\n NOTA: Exibição de credenciais ativa apenas para modo DEV.");
        System.out.println("################################################################\n");
    }
}
