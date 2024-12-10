package kab.auca.skilllink.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kab.auca.skilllink.model.Achievement;
import kab.auca.skilllink.model.User;
import kab.auca.skilllink.repository.AchievementRepository;

@Service
public class AchievementService {

    @Autowired
    private AchievementRepository achievementRepository;

    public Achievement saveAchievement(Achievement achievement) {
        return achievementRepository.save(achievement);
    }

    public List<Achievement> getAchievementsByUser(User user) {
        return achievementRepository.findByUser(user);
    }

    public Optional<Achievement> getAchievementById(Long id) {
        return achievementRepository.findById(id);
    }

    public void deleteAchievement(Long id) {
        achievementRepository.deleteById(id);
    }
}
