package io.faizanUddin.ppmtool.web;


import io.faizanUddin.ppmtool.domain.Project;
import io.faizanUddin.ppmtool.services.MapValidationErrorService;
import io.faizanUddin.ppmtool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("")
    public ResponseEntity<?> createproject(@Valid @RequestBody Project project, BindingResult result, Principal principal){

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);

        if (errorMap != null ) { return errorMap;  }

        Project project1 = projectService.saveOrUpdate(project, principal.getName());
        return new ResponseEntity<Project> (project1, HttpStatus.CREATED);
    }


    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectId(@PathVariable String projectId) {

        Project project = projectService.findProjectByIdentifier(projectId);
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProjects() {

        Iterable<Project> projects = projectService.getAllProjects();
        return new ResponseEntity<Iterable<Project>>( projects, HttpStatus.OK );
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProjectById(@PathVariable String projectId ){

        projectService.deleteProjectByIdentifier(projectId);

        return new ResponseEntity<String>("Project with ID '"+projectId.toUpperCase()+"' is deleted", HttpStatus.OK);
    }


}
