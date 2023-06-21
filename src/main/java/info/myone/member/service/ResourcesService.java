package info.myone.member.service;

import java.util.List;

import info.myone.member.domain.entity.Resources;

public interface ResourcesService {
    Resources getResources(long id);

    List<Resources> getResources();

    void createResources(Resources Resources);

    void deleteResources(long id);
}
