package io.faizanUddin.ppmtool.services;

import io.faizanUddin.ppmtool.domain.Project;
import io.faizanUddin.ppmtool.exceptions.ProjectIdException;
import io.faizanUddin.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdate (Project project) {

        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        }
        catch (Exception e ){
            throw new ProjectIdException("Project Identifier '" + project.getProjectIdentifier() +"' is already exist");
        }
    }

    public Project findProjectByIdentifier(String projectId ) {

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null){
            throw new ProjectIdException("Project Identifier '" + projectId.toUpperCase() +"' does not exist");
        }

         return project;

    }

    public Iterable<Project> getAllProjects() {

        return projectRepository.findAll();

    }

    public void deleteProjectByIdentifier(String projectId) {

        Project project = projectRepository.findByProjectIdentifier(projectId);

        if (project == null) {
            throw new ProjectIdException("Cannot Project with ID'" + projectId +"' This project does not exist");
        }
        projectRepository.delete(project);
    }


}
