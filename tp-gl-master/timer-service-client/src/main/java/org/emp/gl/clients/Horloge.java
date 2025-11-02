package org.emp.gl.clients;

import org.emp.gl.timer.service.TimerService;
import org.emp.gl.timer.service.TimerChangeListener;

public class Horloge implements TimerChangeListener { // ✅ Implémente l'interface
    
    private String name; 
    private TimerService timerService;
    
    // ✅ Constructeur avec INJECTION de dépendance
    public Horloge(String name, TimerService timerService) {
        this.name = name;
        this.timerService = timerService; // ✅ Injection du service
        
        // ✅ Inscription comme OBSERVER
        this.timerService.addTimeChangeListener(this);
        
        System.out.println("Horloge " + name + " initialized!");
    }
    
    // ✅ Méthode appelée AUTOMATIQUEMENT quand le temps change
    @Override
    public void propertyChange(String prop, Object oldValue, Object newValue) {
        // Se déclenche à chaque changement de seconde
        if (TimerChangeListener.SECONDE_PROP.equals(prop)) {
            afficherHeure(); // ✅ Appel automatique
        }
    }
    
    public void afficherHeure() {
        if (timerService != null) {
            System.out.println(name + " affiche " + 
                              String.format("%02d", timerService.getHeures()) + ":" +
                              String.format("%02d", timerService.getMinutes()) + ":" + 
                              String.format("%02d", timerService.getSecondes()));
        }
    }
    
    // Méthode pour se désinscrire
    public void stop() {
        if (timerService != null) {
            this.timerService.removeTimeChangeListener(this);
        }
    }
}