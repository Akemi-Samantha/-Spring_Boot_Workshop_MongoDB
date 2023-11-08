package com.project.workshop_mongodb.config;

import com.project.workshop_mongodb.domain.Post;
import com.project.workshop_mongodb.domain.User;
import com.project.workshop_mongodb.dto.AuthorDTO;
import com.project.workshop_mongodb.repository.PostRepository;
import com.project.workshop_mongodb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

@Configuration

public class Instantiation implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        userRepository.deleteAll();
        postRepository.deleteAll();

        User maria = new User(null, "Maria Brown", "maria@gmail.com");
        User alex = new User(null, "Alex Green", "alex@gmail.com");
        User bob = new User(null, "Bob Grey", "bob@gmail.com");
        User gui = new User(null, "Guilherme Oliveira", "gui@gmail.com");

        //Instanciando o post ja com o autor dele...

        Post post1 = new Post(null,simpleDateFormat.parse("21/03/2018"),"Partiu Viagem","Vou viajar para Sao Paulo, Abracos!!! ;) ", new AuthorDTO(maria));
        Post post2 = new Post(null,simpleDateFormat.parse("23/03/2018"),"Bom dia ","Acordei feliiz hojee :3 ", new AuthorDTO(maria));

        userRepository.saveAll(Arrays.asList(maria, alex, bob, gui));
        postRepository.saveAll(Arrays.asList(post1,post2));

        maria.getPosts().addAll(Arrays.asList(post1, post2));
        userRepository.save(maria);
    }
}
