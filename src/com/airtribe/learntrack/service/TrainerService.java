package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Trainer;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.TrainerRepository;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;
import java.util.List;

public class TrainerService {
    private TrainerRepository repository;

    public TrainerService(TrainerRepository repository) {
        this.repository = repository;
    }

    public Trainer addTrainer(String firstName, String lastName, String email, String specialization)
            throws InvalidInputException {
        InputValidator.validateName(firstName, "First name");
        InputValidator.validateName(lastName, "Last name");
        InputValidator.validateEmail(email);
        InputValidator.validateNonEmptyString(specialization, "Specialization");

        int id = IdGenerator.getNextTrainerId();
        Trainer trainer = new Trainer(id, firstName, lastName, email, specialization, true);
        repository.save(trainer);
        return trainer;
    }

    public Trainer findTrainerById(int id) throws EntityNotFoundException {
        return repository.findById(id);
    }

    public List<Trainer> getAllTrainers() {
        return repository.findAll();
    }

    public List<Trainer> getActiveTrainers() {
        return repository.findAllActive();
    }

    public void updateTrainer(int id, String firstName, String lastName, String email, String specialization)
            throws EntityNotFoundException, InvalidInputException {
        Trainer trainer = findTrainerById(id);
        InputValidator.validateName(firstName, "First name");
        InputValidator.validateName(lastName, "Last name");
        InputValidator.validateEmail(email);
        InputValidator.validateNonEmptyString(specialization, "Specialization");

        trainer.setFirstName(firstName);
        trainer.setLastName(lastName);
        trainer.setEmail(email);
        trainer.setSpecialization(specialization);
        repository.update(trainer);
    }

    public void deactivateTrainer(int id) throws EntityNotFoundException {
        Trainer trainer = findTrainerById(id);
        trainer.setActive(false);
        repository.update(trainer);
    }

    public void activateTrainer(int id) throws EntityNotFoundException {
        Trainer trainer = findTrainerById(id);
        trainer.setActive(true);
        repository.update(trainer);
    }
}
