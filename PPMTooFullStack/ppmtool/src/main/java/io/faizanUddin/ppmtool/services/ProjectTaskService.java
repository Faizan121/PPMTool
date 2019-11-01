package io.faizanUddin.ppmtool.services;


import io.faizanUddin.ppmtool.domain.Backlog;
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
    @Autowired
    private ProjectService projectService;

    public ProjectTask addProjectTask (String projectIdentifier, ProjectTask projectTask, String username){


            Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier,username).getBacklog();
                    //backlogRepository.findByProjectIdentifier(projectIdentifier);

            projectTask.setBacklog(backlog);

            Integer backlogSequence = backlog.getPTSequence();
            backlogSequence++;
            backlog.setPTSequence(backlogSequence);
            projectTask.setProjectSequence(projectIdentifier+"-"+backlogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            //System.out.println("Priority: " + projectTask.getPriority());
            if (projectTask.getStatus() == null || projectTask.getStatus() == ""){
                projectTask.setStatus("TO_DO");
            }

            if (projectTask.getPriority() == null || projectTask.getPriority() == 0 ){
                projectTask.setPriority(3);
            }

            return projectTaskRepository.save(projectTask);
    }

    public Iterable<ProjectTask> findBacklogById(String id, String username) {

        projectService.findProjectByIdentifier(id, username);

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }


    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id, String username){

        //make sure we are searching on an existing back_log
        Backlog backlog = projectService.findProjectByIdentifier(backlog_id, username).getBacklog();

        //make sure our task exist
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence( pt_id);
        if (projectTask == null){
            throw new ProjectNotFoundException("Project with id " + pt_id + " not found");
    }
    if(!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectNotFoundException("Project with id " + pt_id + " does not exist in project " + backlog_id );
    }

        return projectTask;
    }

    public ProjectTask updatePTByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id, String username){

        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);

        projectTask = updatedTask;

        return projectTaskRepository.save(projectTask);

    }

    public void deletePTByProjectSequence(String backlog_id, String pt_id, String username) {

        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);

//        Backlog backlog = projectTask.getBacklog();
//        List<ProjectTask> pts = backlog.getProjectTasks();
//        pts.remove(projectTask);
//        backlogRepository.save(backlog);

        projectTaskRepository.delete(projectTask);
    }
}
