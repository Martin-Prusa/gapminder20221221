package cz.martin.beans;

import cz.martin.models.Country;
import cz.martin.services.GapminderService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@RequestScoped
@Named
public class YearDetailBean {
    @Inject
    private GapminderService gapminderService;

    public int getYear() {
        return Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("year"));
    }

    public List<Country> getCountries() {
        return gapminderService.getCountries(getYear());
    }

    public List<Country> getContinents() {
        return gapminderService.getContinentsYear(getYear());
    }
}
