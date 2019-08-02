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

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("")
    public ResponseEntity<?> createproject(@Valid @RequestBody Project project, BindingResult result){

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidaationService(result);

        if (errorMap != null ) { return errorMap;  }

        Project project1 = projectService.saveOrUpdate(project);
        return new ResponseEntity<Project> (project1, HttpStatus.CREATED);
    }


    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectId(@PathVariable String projectId) {

        Project project = projectService.findProjectByIdentifier(projectId);
        return new ResponseEntity<Project>(project, HttpStatus.OK);

    }

}
