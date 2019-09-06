package io.faizanUddin.ppmtool.services;


import io.faizanUddin.ppmtool.domain.Backlog;
import io.faizanUddin.ppmtool.domain.Project;
import io.faizanUddin.ppmtool.domain.ProjectTask;
import io.faizanUddin.ppmtool.exceptions.ProjectNotFoundException;
import io.faizanUddin.ppmtool.repositories.BacklogRepository;
import io.faizanUddin.ppmtool.repositories.ProjectRepository;
import io.faizanUddin.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;
    @Autowired
    private ProjectTaskRepository projectTaskRepository;
    @Autowired
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask (String projectIdentifier, ProjectTask projectTask){

        try {

            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

            projectTask.setBacklog(backlog);

            Integer backlogSequence = backlog.getPTSequence();
            backlogSequence++;
            backlog.setPTSequence(backlogSequence);
            projectTask.setProjectSequence(projectIdentifier+"-"+backlogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            //System.out.println("Priority: " + projectTask.getPriority());

            if (projectTask.getPriority() == null || projectTask.getPriority() == 0 ){
                projectTask.setPriority(3);
            }
            if (projectTask.getStatus() == null || projectTask.getStatus() == ""){
                projectTask.setStatus("TO_DO");
            }
            return projectTaskRepository.save(projectTask);

        }
        catch(Exception e){
            throw new ProjectNotFoundException("Project Not found");
        }


    }

    public Iterable<ProjectTask> findBacklogById(String id) {

        Project project = projectRepository.findByProjectIdentifier(id);

        if (project == null){
            throw new ProjectNotFoundException("Project with id" + id + "does not exist");
        }

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }


    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id){

        return projectTaskRepository.findByProjectSequence( pt_id);
    }
}
