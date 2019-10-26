package io.faizanUddin.ppmtool.services;

import io.faizanUddin.ppmtool.domain.Backlog;
import io.faizanUddin.ppmtool.domain.Project;
import io.faizanUddin.ppmtool.domain.User;
import io.faizanUddin.ppmtool.exceptions.ProjectIdException;
import io.faizanUddin.ppmtool.repositories.BacklogRepository;
import io.faizanUddin.ppmtool.repositories.ProjectRepository;
import io.faizanUddin.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;

    public Project saveOrUpdate (Project project, String username) {

        try {
            User user = userRepository.findByUsername(username);

            project.setUser(user);
            project.setProjectLeader(user.getUsername());

            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            if (project.getId() ==null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }
            if (project.getId() != null){
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }


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
