package com.dixie.repository;

import com.dixie.model.entity.ImagerPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<ImagerPost, Long> {

//    @Query(value = "select * from pastebin.snippets", nativeQuery = true)
//    List<ImagerPost> getAllSnippets();
//
//    @Query(value = "select * from pastebin.snippets where id = :id", nativeQuery = true)
//    ImagerPost getSnippetById(@Param("id") String id);
//
//    @Query(value = "update pastebin.snippets set body = :body " +
//            "where id = :id returning 'Snippet updated!'", nativeQuery = true)
//    String update(@Param("id") String id, @Param("body") String body);
//
//    @Query(value = "delete from pastebin.snippets where id = :id returning 'Snipped deleted!'", nativeQuery = true)
//    String delete(@Param("id") String id);

}
