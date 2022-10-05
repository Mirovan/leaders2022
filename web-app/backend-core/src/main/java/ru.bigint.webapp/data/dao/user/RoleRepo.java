package ru.bigint.webapp.data.dao.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bigint.webapp.data.entity.user.Role;

@Repository("roleDAO")
public interface RoleRepo extends JpaRepository<Role, Integer> {
	Role findByCode(String code);

//	@Query(value = "select u.email, r.role from users u inner join user_role ur on(u.id=ur.user_id) inner join role r on(ur.role_id=r.id) where u.email=?", nativeQuery = true)
//    Set<Role> findRoleByEmail(String email);
}
