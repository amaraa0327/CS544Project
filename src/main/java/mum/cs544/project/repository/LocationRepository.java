package mum.cs544.project.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mum.cs544.project.entity.Location;
import mum.cs544.project.entity.Session;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {

}
