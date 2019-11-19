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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


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
            Long contestId = contest.getId();
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateToday = LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter);
        LocalDateTime startDate = contestEntity.get().getStartDate();
        LocalDateTime endDate = contestEntity.get().getEndDate();

        if(dateToday.isAfter(endDate)) {
            String pattern = bundle.getString("contest_finished");
            String message = MessageFormat.format(pattern, id);
            throw new BadRequestDeleteContestException(message);
        } else if((dateToday.isAfter(startDate) || dateToday.isEqual(startDate))
                && (dateToday.isBefore(endDate) || dateToday.isEqual(endDate)) ) {
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
