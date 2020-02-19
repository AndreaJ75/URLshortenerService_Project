package com.bnppf.upskilling.project.urlshortener.service;

import com.bnppf.upskilling.project.urlshortener.model.AppUser;
import com.bnppf.upskilling.project.urlshortener.repository.AppUserRepository;

import java.util.List;
import java.util.Optional;

import static com.sun.tools.doclint.Entity.exist;

public class AppUserServiceImpl implements AppUserService {


    private AppUserRepository appUserRepository;

    public AppUserServiceImpl(AppUserRepository appUserRepository){
        this.appUserRepository = appUserRepository;
    }

    @Override
    public AppUser createAppUser(AppUser appUserToBeCreated) {
        return appUserRepository.save(appUserToBeCreated);
    }

    @Override
    public List<AppUser> getAppUserList() {
        return this.appUserRepository.findAll();
    }

    /**
     * Try to find an Appuser from its UID
     * @param appUserUID
     * @return
     */
    @Override
    public Optional<AppUser> getAppUserByUID(String appUserUID) {
        Optional appUser = appUserRepository.findByUidEquals(appUserUID);

        if (appUser.isPresent()){
            return appUser;
        } else {
            return null;
        }
    }

    @Override
    public Optional<List<AppUser>> getAppUserListByName(String appUserName) {
        Optional appUserList = appUserRepository.findAllByName(appUserName);

        if (appUserList.isPresent()) {
            return appUserList;
        } else {
            return null;
        }
    }


    @Override
    public AppUser updateAppUser(AppUser appUserToUpdated) {
        return null;
    }

    @Override
    public boolean deleteAppUser(Long appUserIdToDelete) {
        return false;
    }

    @Override
    public boolean deleteAppUserList(Long appUserIdListToDelete) {
        return false;
    }
}
