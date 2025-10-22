package com.example.pventure.domain.folder.service;

import com.example.pventure.domain.folder.entity.Folder;
import com.example.pventure.domain.user.entity.User;

public interface FolderService {
    Folder getUserDefaultFolder(User user);
}
