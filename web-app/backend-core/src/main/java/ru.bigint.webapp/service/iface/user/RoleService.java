package ru.bigint.webapp.service.iface.user;

import ru.bigint.webapp.data.entity.user.Role;

public interface RoleService {
    Role findRoleByCode(String code);
}
