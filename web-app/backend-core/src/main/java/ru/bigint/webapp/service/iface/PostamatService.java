package ru.bigint.webapp.service.iface;

import ru.bigint.webapp.entity.Postamat;

import java.util.List;

public interface PostamatService {
    Postamat getById(Integer id);

    Postamat add(Postamat channel);

    Postamat update(Postamat channel);

    List<Postamat> getAll();
}
