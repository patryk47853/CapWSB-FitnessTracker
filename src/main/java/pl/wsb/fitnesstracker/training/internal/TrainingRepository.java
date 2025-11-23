package pl.wsb.fitnesstracker.training.internal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import pl.wsb.fitnesstracker.training.api.Training;

import java.util.List;

@Repository
public class TrainingRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Training> findByUserId(Long userId) {
        return em.createQuery(
                        "SELECT t FROM Training t WHERE t.user.id = :uid",
                        Training.class
                )
                .setParameter("uid", userId)
                .getResultList();
    }
}
