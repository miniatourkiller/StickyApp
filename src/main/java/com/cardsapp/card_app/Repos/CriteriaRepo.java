package com.cardsapp.card_app.Repos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.cardsapp.card_app.DTO.Requests.RequestCard;
import com.cardsapp.card_app.DTO.Requests.RequestUsers;
import com.cardsapp.card_app.Entities.Card;
import com.cardsapp.card_app.Entities.UserEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class CriteriaRepo {
    private final EntityManager entityManager;
    
    public Page<Card> getCards(RequestCard requestCard) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Card> criteriaQuery = criteriaBuilder.createQuery(Card.class);
        Root<Card> root = criteriaQuery.from(Card.class);

        List<Predicate> predicates = getCardsPredicates(requestCard, criteriaBuilder, root);
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("dateCreated")));

        //count the total records
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Card> countRoot = countQuery.from(Card.class);
        List<Predicate> countPredicates = getCardsPredicates(requestCard, criteriaBuilder, countRoot);
        countQuery.select(criteriaBuilder.count(countRoot));
        countQuery.where(countPredicates.toArray(new Predicate[0]));
        Long total = entityManager.createQuery(countQuery).getSingleResult();

        Pageable pageable = PageRequest.of(requestCard.getPageNumber(), requestCard.getPageSize());
        List<Card> cards = entityManager.createQuery(criteriaQuery)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        return new PageImpl<>(cards, pageable, total);
    }

    private List<Predicate> getCardsPredicates(RequestCard requestCard, CriteriaBuilder criteriaBuilder, Root<Card> root) {
        List<Predicate> predicates = new ArrayList<>();

        //handle delete
        predicates.add(criteriaBuilder.equal(root.get("deleted"), false));
        
        //check title
        if(requestCard.getTitle() != null){
            predicates.add(criteriaBuilder.like(root.get("title"), "%"+requestCard.getTitle()+"%"));
        }
        return predicates;
    }

    public Page<UserEntity> getUsers(RequestUsers requestUsers){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> criteriaQuery = criteriaBuilder.createQuery(UserEntity.class);
        Root<UserEntity> root = criteriaQuery.from(UserEntity.class);

        List<Predicate> predicates = getUsersPredicates(requestUsers, criteriaBuilder, root);
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("dateCreated")));

        //count the total records
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<UserEntity> countRoot = countQuery.from(UserEntity.class);
        List<Predicate> countPredicates = getUsersPredicates(requestUsers, criteriaBuilder, countRoot);
        countQuery.select(criteriaBuilder.count(countRoot));
        countQuery.where(countPredicates.toArray(new Predicate[0]));
        Long total = entityManager.createQuery(countQuery).getSingleResult();

        Pageable pageable = PageRequest.of(requestUsers.getPageNumber(), requestUsers.getPageSize());
        List<UserEntity> users = entityManager.createQuery(criteriaQuery)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        return new PageImpl<>(users, pageable, total);
    }

    private List<Predicate> getUsersPredicates(RequestUsers requestUsers, CriteriaBuilder criteriaBuilder, Root<UserEntity> root) {
        List<Predicate> predicates = new ArrayList<>();

        //handle delete
        predicates.add(criteriaBuilder.equal(root.get("deleted"), false));
        
        //check email
        if(requestUsers.getEmail() != null){
            predicates.add(criteriaBuilder.like(root.get("email"), "%"+requestUsers.getEmail()+"%"));
        }
        //check fullName
        if(requestUsers.getFullName() != null){
            predicates.add(criteriaBuilder.like(root.get("fullName"), "%"+requestUsers.getFullName()+"%"));
        }
        //check dob
        if(requestUsers.getDob() != null){
            predicates.add(criteriaBuilder.like(root.get("dob"), "%"+requestUsers.getDob()+"%"));
        }
        return predicates;
    }
}
