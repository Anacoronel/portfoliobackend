package com.portfolio.controller;

import com.portfolio.dto.ExperienceDto;
import com.portfolio.dto.Mensaje;
import com.portfolio.entity.Experience;
import com.portfolio.security.entity.User;
import com.portfolio.security.service.UserService;
import com.portfolio.service.ExperienceService;
import java.security.Principal;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/experience")
@CrossOrigin(origins = "*")
public class ExperienceController {
    
    @Autowired
    ExperienceService experienceService;
    
    @Autowired
    UserService userService;
    
    @GetMapping("/list")
    public ResponseEntity<List<Experience>> list() {
        List<Experience> list = experienceService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

     @GetMapping("/details/id/{id}")
    public ResponseEntity<Experience> getById(@PathVariable("id") int id) {
        if (!experienceService.existsById(id))
            return new ResponseEntity(new Mensaje("Experience not found"), HttpStatus.NOT_FOUND);
        Experience experience = experienceService.get(id).get();
        return new ResponseEntity(experience, HttpStatus.OK);
    }
    
    @GetMapping("/details/{username}")
    public ResponseEntity<List<Experience>> getByUsername(@PathVariable("username") String username) {
        if (!userService.existsByUsername(username))
            return new ResponseEntity(new Mensaje("User not found"), HttpStatus.NOT_FOUND);
        User user = userService.getByUsername(username).get();        
        List<Experience> list = experienceService.listByUserId(user.getId());
        return new ResponseEntity(list, HttpStatus.OK);
    }
    
    @RequestMapping("/create")
    public ResponseEntity<?> create(@RequestBody ExperienceDto experienceDto, Principal principal) {
        if(StringUtils.isBlank(experienceDto.getPosition()))
            return new ResponseEntity(new Mensaje("position can not be empty"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(experienceDto.getCompany()))
            return new ResponseEntity(new Mensaje("company can not be empty"), HttpStatus.BAD_REQUEST);

        Experience experience = new Experience(
                experienceDto.getPosition(), 
                experienceDto.getCompany(), 
                experienceDto.getLink(),
                experienceDto.getStartTime(),
                experienceDto.getEndTime());

        User user = userService.getByUsername(principal.getName()).get();
        experience.setUser(user);
        
        experienceService.save(experience);
        return new ResponseEntity(new Mensaje("Experience created"), HttpStatus.CREATED);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody ExperienceDto experienceDto, Principal principal) {
        if (!experienceService.get(id).get().getUser().getUsername()
                .equals(principal.getName()))
            return new ResponseEntity(new Mensaje("Not allowed to do that"), HttpStatus.FORBIDDEN);
        if (!experienceService.existsById(id))
            return new ResponseEntity(new Mensaje("Not found"), HttpStatus.NOT_FOUND);
        if(StringUtils.isBlank(experienceDto.getPosition()))
            return new ResponseEntity(new Mensaje("position can not be empty"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(experienceDto.getCompany()))
            return new ResponseEntity(new Mensaje("company can not be empty"), HttpStatus.BAD_REQUEST);

        Experience experience = experienceService.get(id).get();
        experience.setPosition(experienceDto.getPosition());
        experience.setCompany(experienceDto.getCompany());
        experience.setLink(experienceDto.getLink());
        experience.setStartTime(experienceDto.getStartTime());
        experience.setEndTime(experienceDto.getEndTime());

        experienceService.save(experience);
        return new ResponseEntity(new Mensaje("Experience updated"), HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id, Principal principal) {
        if (!experienceService.get(id).get().getUser().getUsername()
                .equals(principal.getName()))
            return new ResponseEntity(new Mensaje("Not allowed to do that"), HttpStatus.FORBIDDEN);
        if (!experienceService.existsById(id))
            return new ResponseEntity(new Mensaje("Experience not found"), HttpStatus.NOT_FOUND);
        experienceService.delete(id);
        return new ResponseEntity(new Mensaje("Experience deleted"), HttpStatus.OK);
    }
}
