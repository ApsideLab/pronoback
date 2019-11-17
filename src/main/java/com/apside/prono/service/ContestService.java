package com.apside.prono.service;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.errors.contest.BadRequestCreateContestException;
import com.apside.prono.errors.contest.BadRequestDeleteContestException;
import com.apside.prono.mapper.contest.ContestEntityMapper;
import com.apside.prono.mapper.contest.ContestMapper;
import com.apside.prono.model.ContestEntity;
import com.apside.prono.modelapi.Contest;
import com.apside.prono.repository.ContestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Transactional(readOnly = true)
public class ContestService {
    @Autowired
    private ContestRepository contestRepository;

    private ResourceBundle bundle = ResourceBundle.getBundle("messagesServicesError");

    @Transactional
    public Contest createContest(Contest contest) {
        ContestEntity contestEntity = ContestEntityMapper.INSTANCE.mapContestEntity(contest);

        if (contestEntity != null) {
            if (contest.getId() != null) {
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
    public ContestEntity update(Contest contest) {
        if (contest != null) {
            if (contest.getId() == null) {
                String pattern = bundle.getString("contest_wrong_id");
                String message = MessageFormat.format(pattern, contest.getId());
                throw new EntityNotFoundException(message);
            }
            Long contestId = Long.parseLong(contest.getId());
            Optional<ContestEntity> contestEntity = contestRepository.findById(contestId);

            if (contestEntity.isPresent()) {
                contestEntity = Optional.of(ContestEntityMapper.INSTANCE.mapContestEntity(contest));
            } else {
                String pattern = bundle.getString("contest_wrong_id");
                String message = MessageFormat.format(pattern, contest.getId());
                throw new EntityNotFoundException(message);
            }

            return contestRepository.save(contestEntity.get());
        } else {
            throw new EntityNotFoundException(bundle.getString("update_contest_empty"));
        }
    }

    @Transactional
    public void delete(long id) {
        Optional<ContestEntity> contestEntity = contestRepository.findById(id);
        if (!contestEntity.isPresent()) {
            String pattern = bundle.getString("contest_wrong_id");
            String message = MessageFormat.format(pattern, id);
            throw new EntityNotFoundException(message);
        }

        verifDatesContest(id, contestEntity);
        contestRepository.deleteById(contestEntity.get().getId());
    }

    private void verifDatesContest(long id, Optional<ContestEntity> contestEntity) {
        Date date = new Date();
        Date stDate = new Date();
        Date enDate = new Date();
        String startDate = contestEntity.get().getStartDate();
        String endDate = contestEntity.get().getEndDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

        try {
            stDate = format.parse(startDate);
            enDate = format.parse(endDate);
        } catch (java.text.ParseException e){
            e.printStackTrace();
        }

        if(!(stDate.after(date) && enDate.after(date))) {
            String pattern = bundle.getString("contest_finished");
            String message = MessageFormat.format(pattern, id);
            throw new BadRequestDeleteContestException(message);
        } else if(stDate.before(date) && enDate.after(date)) {
            String pattern = bundle.getString("contest_already_begun");
            String message = MessageFormat.format(pattern, id);
            throw new BadRequestDeleteContestException(message);
        }
    }

    public ContestEntity find(String label) {
        for (int i = 0; i < contestRepository.findAll().size(); i++) {
            if (contestRepository.findAll().get(i).getLabel().equals(label))
                return contestRepository.findAll().get(i);
        }
        return null;
    }
}
