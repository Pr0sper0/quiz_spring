package sbr.track.btsystem.services;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import sbr.track.btsystem.domain.Backlog;
import sbr.track.btsystem.domain.Project;
import sbr.track.btsystem.domain.User;
import sbr.track.btsystem.exceptions.ProjectIdException;
import sbr.track.btsystem.exceptions.ProjectNotFoundException;
import sbr.track.btsystem.repositories.BacklogRepository;
import sbr.track.btsystem.repositories.ProjectRepository;
import sbr.track.btsystem.repositories.UserRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;

    //@Async("MyConcurrentTaskExecutor")
    public Project saveOrUpdateProject(Project project, String username) {

        if (project.getId() != null) {
            Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());

            if (existingProject != null && (!existingProject.getUser().getUsername().equals(username))) {
                throw new ProjectNotFoundException("Project not found in your account");
            } else if (existingProject == null) {
                throw new ProjectNotFoundException("Project with ID: '" + project.getProjectIdentifier() + "' can't be updated");
            }
        }

        try {

            User user = userRepository.findByUsername(username);
            project.setUser(user);
            project.setProjectLeader(user.getUsername());

            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            if (project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

            if (project.getId() != null) {
                project.setBacklog(
                        backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }

            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException(
                    "Project ID '" + project.getProjectIdentifier().toUpperCase() + "' Already exists");
        }

    }

    public Project findProjectByIdentifier(@NotNull String projectId, String username) {

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null) {
            throw new ProjectIdException("Project ID '" + projectId + "' Does not exist");
        }

        if (!project.getProjectLeader().equals(username)) {
            throw new ProjectNotFoundException("Project not found in your account");
        }

        return project;
    }

    public Project findProjectByName(String projectName) {
        Project project = projectRepository.findByProjectName(projectName.toUpperCase());

        if (project == null) {
            throw new ProjectIdException("Project name '" + projectName + "' Does not exist");
        }
        return project;
    }

    public Iterable<Project> findAllProjects(String username) {

        return projectRepository.findAllByProjectLeader(username);
    }

    public void deleteProjectByIdentifier(String projectId, String username) {


/*        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null) {
            throw new ProjectIdException(
                    "Cannot delete Project with id '" + projectId + "'. This Project does not exist");
        }*/

        projectRepository.delete(findProjectByIdentifier(projectId, username));
    }

    // public Project updateProjectByIdentifier(String projectId) {
    // Project project =
    // projectRepository.findByProjectIdentifier(projectId.toUpperCase());

    // if (project == null) {
    // throw new ProjectIdException("Project ID '" + projectId + "' Does not
    // exist");
    // }

    // try {
    // project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
    // return projectRepository.save(project);
    // } catch (Exception e){
    // throw new ProjectIdException("Project ID '" + projectId + "' Does not
    // exist");
    // }

    // }
}