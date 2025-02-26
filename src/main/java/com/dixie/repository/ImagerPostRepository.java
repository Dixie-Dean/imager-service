package com.dixie.repository;

import com.dixie.model.entity.ImagerPost;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ImagerPostRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Transactional
    public void saveImagerPost(ImagerPost imagerPost) {
        entityManager.persist(imagerPost);
    }

    public Optional<ImagerPost> getImagerPost(String id) {
        ImagerPost imagerPost = entityManager.find(ImagerPost.class, id);
        return Optional.ofNullable(imagerPost);
    }

    public Optional<List<ImagerPost>> getImagerPostsByUsername(String user) {
        var query = entityManager.createQuery(
                "SELECT i FROM ImagerPost i WHERE i.user = :user", ImagerPost.class);
        query.setParameter("user", user);
        var list = query.getResultList();
        return Optional.ofNullable(list);
    }

    @Transactional
    public Optional<ImagerPost> editImagerPost(ImagerPost updatedImagerPost) {
        ImagerPost mergedImagerPost = entityManager.merge(updatedImagerPost);
        return Optional.ofNullable(mergedImagerPost);
    }

    @Transactional
    public void deleteImagerPost(ImagerPost entity) {
        entityManager.remove(entity);
    }

    @Transactional
    public void deleteExpiredImagerPosts() {
        entityManager.createQuery(
                "DELETE FROM ImagerPost WHERE expirationTime < CURRENT_TIMESTAMP")
                .executeUpdate();
    }
}
