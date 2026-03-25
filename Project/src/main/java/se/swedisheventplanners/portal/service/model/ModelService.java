package se.swedisheventplanners.portal.service.model;

import org.springframework.ui.Model;

public interface ModelService {

    void addAuthenticationToModel(Model model);

    void addAssigneesAndPrioritiesToModel(Model model);

    void addTaskPrioritiesToModel(Model model);

    void addPrioritiesToModel(Model model);

    void addStatusesToModel(Model model);
}
