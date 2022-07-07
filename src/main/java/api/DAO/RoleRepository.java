package api.DAO;

import api.entity.Role;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

    Role getRoleByName(String name);
    List<Role> findAll();

}
