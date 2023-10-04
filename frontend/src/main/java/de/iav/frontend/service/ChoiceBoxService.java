package de.iav.frontend.service;

import de.iav.frontend.model.TicketPriority;

public class ChoiceBoxService {

    public TicketPriority stringToTicketPriority(String priority) {
        TicketPriority selectedPriority;
        if (priority.equals(TicketPriority.LOW.toString())) {
            selectedPriority = TicketPriority.LOW;
        } else if (priority.equals(TicketPriority.MEDIUM.toString())) {
            selectedPriority = TicketPriority.MEDIUM;
        } else selectedPriority = TicketPriority.HIGH;
        return selectedPriority;
    }

}
