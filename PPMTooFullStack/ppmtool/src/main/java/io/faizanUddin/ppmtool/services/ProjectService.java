package io.faizanUddin.ppmtool.services;

import io.faizanUddin.ppmtool.domain.Backlog;
import io.faizanUddin.ppmtool.domain.Project;
import io.faizanUddin.ppmtool.domain.User;
import io.faizanUddin.ppmtool.exceptions.ProjectIdException;
import io.faizanUddin.ppmtool.exceptions.ProjectNotFoundException;
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


//        if (project.getId() != null){
//
//            Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
//            if (existingProject != null &&(!existingProject.getProjectLeader().equals(username))){
//                throw new ProjectNotFoundException("Project not found in your account");
//            }else if (existingProject == null){
//                throw new ProjectNotFoundException("Project with ID '"+existingProject.getProjectIdentifier() +"' cannot be updated because it doesn't exist");
//            }
//        }

        if(project.getId() != null){
            Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
            if(existingProject !=null &&(!existingProject.getProjectLeader().equals(username))){
                throw new ProjectNotFoundException("Project not found in your account");
            }else if(existingProject == null){
                throw new ProjectNotFoundException("Project with ID: '"+project.getProjectIdentifier()+"' cannot be updated because it doesn't exist");
            }
        }

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



// INVALID
// try {
//            User user = userRepository.findByUsername(username);
//
//            project.setUser(user);
//            project.setProjectLeader(user.getUsername());
//
//            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
//            if (project.getId() ==null) {
//                Backlog backlog = new Backlog();
//                project.setBacklog(backlog);
//                backlog.setProject(project);
//                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
//            }
//            if (project.getId() != null){
//                Project project1 = findProjectByIdentifier(project.getProjectIdentifier(), username);
//                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
//            }
//
//
//            return projectRepository.save(project);
//        }
//        catch (Exception e ){
//            throw new ProjectIdException("Project Identifier '" + project.getProjectIdentifier() +"' is already exist");
//        }

    }

    public Project findProjectByIdentifier(String projectId, String username ) {

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null){
            throw new ProjectIdException("Project Identifier '" + projectId.toUpperCase() +"' does not exist");
        }
        if (!project.getProjectLeader().equals(username)){
            throw new ProjectNotFoundException("Project Not found in your account");
        }
        return project;
    }

    public Iterable<Project> getAllProjects(String username) {


        return projectRepository.findAllByProjectLeader(username);

    }

    public void deleteProjectByIdentifier(String projectId, String username) {

        projectRepository.delete(findProjectByIdentifier(projectId,username));
    }


}
