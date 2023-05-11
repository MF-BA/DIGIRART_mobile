/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.ui.Button;
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
    Label eventNameLabel = new Label("Event Name: " + event.getEventName(),"NewsTopLine2");
    Label startDateLabel = new Label("Start Date: " + event.getStartDate(),"NewsTopLine2");
    Label endDateLabel = new Label("End Date: " + event.getEndDate(),"NewsTopLine2");
    Label nbParticipantsLabel = new Label("Number of Participants: " + event.getNbParticipants(),"NewsTopLine2");
    Label detailsLabel = new Label("Details:"+event.getDetail(),"NewsTopLine2");
  
    
    
        
    // Set layout and add components to the form
    setLayout(new BoxLayout(BoxLayout.Y_AXIS));
    addAll(eventNameLabel, startDateLabel, endDateLabel, nbParticipantsLabel, detailsLabel);
    
    Button button = new Button("Participate");
button.addActionListener(e -> {
    // Action to be performed when the button is clicked
    System.out.println("Button clicked!");
});
    add(button);
}
  
}

