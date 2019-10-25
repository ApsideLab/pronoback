package com.apside.prono.service;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.errors.contest.BadRequestCreateContestException;
import com.apside.prono.mapper.contest.ContestEntityMapper;
import com.apside.prono.mapper.contest.ContestMapper;
import com.apside.prono.model.ContestEntity;
import com.apside.prono.modelapi.Contest;
import com.apside.prono.repository.ContestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


@Service
@Transactional(readOnly = true)
public class ContestService {
    @Autowired
    private ContestRepository contestRepository;
    @Autowired
    private Environment env;
    private ResourceBundle bundle = ResourceBundle.getBundle("messagesServicesError");

    @Transactional
    public Contest createContest(Contest contest) {
        ContestEntity contestEntity = ContestEntityMapper.INSTANCE.mapContestEntity(contest);

        if (contestEntity != null) {
            if (contestEntity.getId() != null) {
                throw new BadRequestCreateContestException(bundle.getString("new_contest_create"));
            }
            if (contestRepository.findByLabel(contestEntity.getLabel()) != null) {
                throw new BadRequestCreateContestException(bundle.getString("new_contest_exists"));
            }
            contestEntity = contestRepository.save(contestEntity);
        } else {
            throw new BadRequestCreateContestException(bundle.getString("new_contest_empty"));
        }
        return ContestMapper.INSTANCE.mapContest(contestEntity);
    }

    public Contest getContest(long id) {
        Optional<ContestEntity> contestEntity = contestRepository.findById(id);
        if (!contestEntity.isPresent()) {
            String pattern = bundle.getString("contest_wrong_id");
            String message = MessageFormat.format(pattern, id);
            throw new EntityNotFoundException(message);
        }
        return ContestMapper.INSTANCE.mapContest(contestEntity.get());
    }

    @Transactional
    public List<ContestEntity> getAll() {
        return contestRepository.findAll();
    }

    @Transactional
    public ContestEntity update(ContestEntity contestEntity) {
        if (contestEntity != null) {
            Optional<ContestEntity> player = contestRepository.findById(contestEntity.getId());
            if (!player.isPresent()) {
                String pattern = bundle.getString("contest_wrong_id");
                String message = MessageFormat.format(pattern, contestEntity.getId());
                throw new EntityNotFoundException(message);
            }
            contestRepository.findById(contestEntity.getId()).get().setLabel(contestEntity.getLabel());
            ContestEntity contestEntity1 = contestRepository.findById(contestEntity.getId()).get();

            contestRepository.flush();
            return contestEntity1;
        }
        throw new EntityNotFoundException(bundle.getString("update_contest_empty"));
    }

    @Transactional
    public void delete(long id) {
        if (!contestRepository.findById(id).isPresent()) {
            String pattern = bundle.getString("contest_wrong_id");
            String message = MessageFormat.format(pattern, id);
            throw new EntityNotFoundException(message);
        }
        contestRepository.deleteById(id);
    }

    public ContestEntity find(String label) {
        for (int i = 0; i < contestRepository.findAll().size(); i++) {
            if (contestRepository.findAll().get(i).getLabel().equals(label))
                return contestRepository.findAll().get(i);
        }
        return null;
    }
}
