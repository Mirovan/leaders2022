package ru.bigint.webapp.service.impl.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.bigint.webapp.data.dao.user.RoleRepo;
import ru.bigint.webapp.data.entity.user.Role;
import ru.bigint.webapp.service.iface.user.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final RoleRepo roleRepo;

    public RoleServiceImpl(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public Role findRoleByCode(String code) {
        return roleRepo.findByCode(code);
    }
}
