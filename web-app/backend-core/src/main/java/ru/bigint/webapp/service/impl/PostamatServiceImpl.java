package ru.bigint.webapp.service.impl;

import org.springframework.stereotype.Service;
import ru.bigint.webapp.entity.Postamat;
import ru.bigint.webapp.entity.PostamatUsing;
import ru.bigint.webapp.repository.PostamatRepo;
import ru.bigint.webapp.service.iface.PostamatService;
import ru.bigint.webapp.utils.Util;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PostamatServiceImpl implements PostamatService {
    private final PostamatRepo postamatRepo;

    public PostamatServiceImpl(PostamatRepo postamatRepo) {
        this.postamatRepo = postamatRepo;
    }

    @Override
    public Postamat getById(Integer id) {
        return postamatRepo.findById(id).get();
    }

    @Override
    @Transactional
    public Postamat add(Postamat postamat) {
        List<PostamatUsing> postamatUsingList = new ArrayList<>();
        for (int i=1; i<=11; i++) {
            PostamatUsing postamatUsing = new PostamatUsing();
            postamatUsing.setPostamat(postamat);
            postamatUsing.setYear(2022);
            postamatUsing.setMonth(i);
            postamatUsing.setUseCount(Util.getRandomNumber(100, 600));
            postamatUsingList.add(postamatUsing);
        }
        postamat.setPostamatUsing(postamatUsingList);
        postamat = postamatRepo.save(postamat);
        return postamat;
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
