package de.iav.frontend.service;

import de.iav.frontend.model.TicketPriority;
import de.iav.frontend.model.TicketStatus;

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

    public TicketStatus stringToTicketStatus(String status) {
        TicketStatus selectedStatus;
        if (status.equals(TicketStatus.OPEN.toString())) {
            selectedStatus = TicketStatus.OPEN;
        } else if (status.equals(TicketStatus.IN_PROGRESS.toString())) {
            selectedStatus = TicketStatus.IN_PROGRESS;
        } else selectedStatus = TicketStatus.DONE;
        return selectedStatus;
    }
}
