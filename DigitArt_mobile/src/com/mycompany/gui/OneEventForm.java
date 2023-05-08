/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.mycompany.entities.Event;
import com.mycompany.services.ServiceEvent;
/**
 *
 * @author kossay
 */
public class OneEventForm extends Form {

  public OneEventForm(Event event) {
    setTitle("Event Details");
    
    // Create UI components
    Label eventNameLabel = new Label("Event Name: " + event.getEventName());
    Label startDateLabel = new Label("Start Date: " + event.getStartDate());
    Label endDateLabel = new Label("End Date: " + event.getEndDate());
    Label nbParticipantsLabel = new Label("Number of Participants: " + event.getNbParticipants());
    Label detailsLabel = new Label("Details:");
    TextArea detailsTextArea = new TextArea(event.getDetail());
    detailsTextArea.setEditable(false);
    detailsTextArea.setRows(4);
    detailsTextArea.setUIID("Label");
        
    // Set layout and add components to the form
    setLayout(new BoxLayout(BoxLayout.Y_AXIS));
    addAll(eventNameLabel, startDateLabel, endDateLabel, nbParticipantsLabel, detailsLabel, detailsTextArea);
}
}

