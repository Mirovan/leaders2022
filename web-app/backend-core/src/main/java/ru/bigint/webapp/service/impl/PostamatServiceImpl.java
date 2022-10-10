package ru.bigint.webapp.service.impl;

import org.springframework.stereotype.Service;
import ru.bigint.webapp.entity.Postamat;
import ru.bigint.webapp.repository.PostamatRepo;
import ru.bigint.webapp.service.iface.PostamatService;

import java.util.List;

@Service
public class PostamatServiceImpl implements PostamatService {
    private final PostamatRepo postamatRepo;

    public PostamatServiceImpl(PostamatRepo postamatRepo) {
        this.postamatRepo = postamatRepo;
    }

    @Override
    public Postamat getById(Integer id) {
        return null;
    }

    @Override
    public Postamat add(Postamat postamat) {
        return postamatRepo.save(postamat);
    }

    @Override
    public Postamat update(Postamat postamat) {
        return null;
    }

    @Override
    public List<Postamat> getAll() {
        return postamatRepo.findAll();
    }
}
