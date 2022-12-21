package cz.martin.services;

import cz.martin.models.Country;
import cz.martin.repositories.GapminderRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@ApplicationScoped
@Named
public class GapminderService {
    @Inject
    private GapminderRepository gapminderRepository;

    public List<Integer> getYears() {
        return gapminderRepository.getYears();
    }

    public List<Country> getCountries(int year) {
        return gapminderRepository.getCountries(year);
    }
}
