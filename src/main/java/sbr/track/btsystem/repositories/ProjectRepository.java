package sbr.track.btsystem.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sbr.track.btsystem.domain.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

    // @Query("select p from Project p where p.projectName = ?1")
    Project findByProjectName(String projectName);

    Project findByProjectIdentifier(String projectIdentifier);

    @Override
    Iterable<Project> findAll();

    Iterable<Project> findAllByProjectLeader(String username);

    @Override
    void delete(Project entity);

}