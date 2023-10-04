package de.iav.frontend.service;

import de.iav.frontend.security.AppUserRole;

public class ChoiceBoxService {
    public String OutputFormatter(AppUserRole role) {
        String output;
        if (role.equals(AppUserRole.USER)) {
            output = AppUserRole.USER.toString();
        } else if (role.equals(AppUserRole.ADMIN)) {
            output = AppUserRole.ADMIN.toString();
        } else output = AppUserRole.DEVELOPER.toString();
        return output;
    }
}
