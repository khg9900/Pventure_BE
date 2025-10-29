package com.example.pventure.domain.folder.repository;

import com.example.pventure.domain.folder.entity.Folder;
import com.example.pventure.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder, Long> {

    @Query("SELECT f FROM Folder f WHERE f.user = :user AND f.isDefault = true")
    Optional<Folder> findDefaultFolderByUser(@Param("user") User user);

}