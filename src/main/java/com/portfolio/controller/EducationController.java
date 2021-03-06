package com.portfolio.controller;

import com.portfolio.dto.EducationDto;
import com.portfolio.dto.Mensaje;
import com.portfolio.entity.Education;
import com.portfolio.security.entity.User;
import com.portfolio.security.service.UserService;
import com.portfolio.service.EducationService;
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
@RequestMapping("/api/education")
@CrossOrigin(origins = "*")
public class EducationController {
    
    @Autowired
    EducationService educationService;
    
    @Autowired
    UserService userService;
    
    @GetMapping("/list")
    public ResponseEntity<List<Education>> list() {
        List<Education> list = educationService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }
    
    @GetMapping("/details/id/{id}")
    public ResponseEntity<Education> getById(@PathVariable("id") int id) {
        if(!educationService.existsById(id))
            return new ResponseEntity(new Mensaje("Education not found"), HttpStatus.NOT_FOUND);
        Education education = educationService.get(id).get();
        return new ResponseEntity(education, HttpStatus.OK);
    }
    
    @GetMapping("/details/{username}")
    public ResponseEntity<List<Education>> getByUsername(@PathVariable("username") String username) {
        if (!userService.existsByUsername(username))
            return new ResponseEntity(new Mensaje("User not found"), HttpStatus.NOT_FOUND);
        User user = userService.getByUsername(username).get();
        List<Education> list = educationService.listByUserId(user.getId());
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @RequestMapping("/create")
    public ResponseEntity<?> create(@RequestBody EducationDto educationDto, Principal principal) {
        if(StringUtils.isBlank(educationDto.getInstitution()))
            return new ResponseEntity(new Mensaje("institution can not be empty"), HttpStatus.BAD_REQUEST);

                
        Education education = new Education(
                educationDto.getInstitution(),
                educationDto.getTitle(), 
                educationDto.getLink(),
                educationDto.getDate()


        );
        
        User user = userService.getByUsername(principal.getName()).get();
        education.setUser(user);

        educationService.save(education);
        return new ResponseEntity(new Mensaje("Education created"), HttpStatus.CREATED);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody EducationDto educationDto, Principal principal) {
        if (!educationService.get(id).get().getUser().getUsername()
                .equals(principal.getName()))
            return new ResponseEntity(new Mensaje("Not allowed to do that"), HttpStatus.FORBIDDEN);
        if (!educationService.existsById(id))
            return new ResponseEntity(new Mensaje("Education not found"), HttpStatus.NOT_FOUND);
        if(StringUtils.isBlank(educationDto.getInstitution()))
            return new ResponseEntity(new Mensaje("institution can not be empty"), HttpStatus.BAD_REQUEST);


        
        Education education = educationService.get(id).get();
        education.setInstitution(educationDto.getInstitution());
        education.setTitle(educationDto.getTitle());
        education.setLink(educationDto.getLink());
        education.setDate(educationDto.getDate());

        educationService.save(education);
        return new ResponseEntity(new Mensaje("Education updated"), HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id, Principal principal) {
        if (!educationService.get(id).get().getUser().getUsername()
                .equals(principal.getName()))
            return new ResponseEntity(new Mensaje("Not allowed to do that"), HttpStatus.FORBIDDEN);
        if (!educationService.existsById(id))
            return new ResponseEntity(new Mensaje("Education not found"), HttpStatus.NOT_FOUND);
        educationService.delete(id);
        return new ResponseEntity(new Mensaje("Education deleted"), HttpStatus.OK);
    }
}
