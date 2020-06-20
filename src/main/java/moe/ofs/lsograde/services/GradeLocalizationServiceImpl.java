package moe.ofs.lsograde.services;

import moe.ofs.lsograde.config.UTF8Control;
import moe.ofs.lsograde.model.ErrorItem;
import moe.ofs.lsograde.model.LandingGrade;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Service
public class GradeLocalizationServiceImpl implements GradeLocalizationService {

    @Override
    public LandingGrade translate(LandingGrade landingGrade, String localeName) {
        Locale locale = new Locale(localeName);

        ResourceBundle bundle =
                ResourceBundle.getBundle("moe/ofs/lsograde/l10n/LandingGradeResources",
                        locale, new UTF8Control());

        LandingGrade localizedLandingGrade = new LandingGrade();
        localizedLandingGrade.setGrade(landingGrade.getGrade());
        localizedLandingGrade.setCarrierName(landingGrade.getCarrierName());
        localizedLandingGrade.setRawGrade(landingGrade.getRawGrade());
        localizedLandingGrade.setTimestamp(landingGrade.getTimestamp());
        localizedLandingGrade.setWire(landingGrade.getWire());
        localizedLandingGrade.setId(landingGrade.getId());

        if(landingGrade.getAircraftType() != null) {
            try {
                localizedLandingGrade.setAircraftType(bundle.getString(landingGrade.getAircraftType()));
            } catch (MissingResourceException | NullPointerException ignored) {}
        }

        if(landingGrade.getGrade() != null) {
            try {
                localizedLandingGrade.setGrade(bundle.getString(landingGrade.getGrade()));
            } catch (MissingResourceException | NullPointerException ignored) {}
        }

        localizedLandingGrade.setErrorItems(landingGrade.getErrorItems().stream()
                .map(errorItem -> localizeText(errorItem, bundle))
                .collect(Collectors.toList()));

        return localizedLandingGrade;
    }

    private ErrorItem localizeText(ErrorItem errorItem, ResourceBundle bundle) {

        ErrorItem localizedErrorItem = new ErrorItem();

        if(errorItem.getLevel() != null) {
            try {
                localizedErrorItem.setLevel(bundle.getString(errorItem.getLevel()));
            } catch (MissingResourceException | NullPointerException ignored) {}
        }

        if(errorItem.getError() != null) {
            try {
                localizedErrorItem.setError(bundle.getString(errorItem.getError()));
            } catch (MissingResourceException | NullPointerException ignored) {}
        }

        if(errorItem.getPosition() != null) {
            try {
                localizedErrorItem.setPosition(bundle.getString(errorItem.getPosition()));
            } catch (MissingResourceException | NullPointerException ignored) {}
        }

        return localizedErrorItem;
    }
}
