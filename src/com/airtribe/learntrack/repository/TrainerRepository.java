package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Trainer;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class TrainerRepository extends BaseRepository<Trainer> {
    public Trainer findById(int id) throws EntityNotFoundException {
        for (Trainer trainer : items) {
            if (trainer.getId() == id) {
                return trainer;
            }
        }
        throw new EntityNotFoundException("Trainer with ID " + id + " not found");
    }

    public List<Trainer> findAllActive() {
        List<Trainer> activeTrainers = new ArrayList<>();
        for (Trainer trainer : items) {
            if (trainer.isActive()) {
                activeTrainers.add(trainer);
            }
        }
        return activeTrainers;
    }

    public boolean exists(int id) {
        try {
            findById(id);
            return true;
        } catch (EntityNotFoundException e) {
            return false;
        }
    }
}
