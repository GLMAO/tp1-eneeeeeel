package org.emp.gl.clients;

import org.emp.gl.timer.service.TimerService;
import org.emp.gl.timer.service.TimerChangeListener;
import java.util.Random;
public class CompteARebours implements TimerChangeListener {
    
    private String name;
    private TimerService timerService;
    private int compteur;
    private volatile boolean actif = true; // ✅ volatile pour la concurrence
    
    public CompteARebours(String name, TimerService timerService, int valeurInitiale) {
        this.name = name;
        this.timerService = timerService;
        this.compteur = valeurInitiale;
        
        // ✅ Synchronisation pour l'ajout
        synchronized(timerService) {
            this.timerService.addTimeChangeListener(this);
        }
        
        System.out.println(name + " initialisé avec " + compteur + " secondes");
    }
    
    @Override
    public void propertyChange(String prop, Object oldValue, Object newValue) {
        // ✅ Vérification thread-safe
        if (!actif || !TimerChangeListener.SECONDE_PROP.equals(prop)) {
            return;
        }
        
        // ✅ Décrémentation synchronisée
        synchronized(this) {
            compteur--;
        }
        
        System.out.println(name + " - " + compteur + " secondes restantes");
        
        // ✅ Vérification et désinscription thread-safe
        if (compteur <= 0) {
            System.out.println("🎉 " + name + " - TERMINÉ!");
            stop();
        }
    }
    
    public void stop() {
        // ✅ Arrêt thread-safe
        if (actif) {
            synchronized(this) {
                if (actif) { // Double vérification
                    actif = false;
                    if (timerService != null) {
                        synchronized(timerService) {
                            timerService.removeTimeChangeListener(this);
                        }
                    }
                    System.out.println("⏹️  " + name + " désinscrit");
                }
            }
        }
    }
    
    // ... autres méthodes
}