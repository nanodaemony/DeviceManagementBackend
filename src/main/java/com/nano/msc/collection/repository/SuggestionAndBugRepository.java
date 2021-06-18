package com.nano.msc.collection.repository;

import com.nano.msc.collection.entity.SuggestionAndBug;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Description: 建议与Bug信息
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 21:07
 */
@Repository
public interface SuggestionAndBugRepository extends JpaRepository<SuggestionAndBug, Integer> {



}
