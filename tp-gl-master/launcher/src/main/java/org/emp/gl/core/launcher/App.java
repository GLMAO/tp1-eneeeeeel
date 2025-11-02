
/* 


partie 01:


// ❌ ANCIEN (incorrect)
// package org.emp.gl.clients;

// ✅ NOUVEAU (correct)
package org.emp.gl.core.launcher;

import org.emp.gl.clients.Horloge;
import org.emp.gl.timer.service.TimerService;
import org.emp.gl.time.service.impl.DummyTimeServiceImpl;

public class App {
    public static void main(String[] args) {
        
        // ✅ 1. Instancier d'abord le TimerService
        TimerService timerService = new DummyTimeServiceImpl();
        
        System.out.println("=== DÉMONSTRATION PATTERN OBSERVER ===");
        System.out.println("Étape 1: TimerService instancié\n");
        
        // ✅ 2. Injecter l'instance dans plusieurs Horloges
        System.out.println("Étape 2: Création des horloges avec injection");
        Horloge horloge1 = new Horloge("Salon", timerService);
        Horloge horloge2 = new Horloge("Cuisine", timerService);
        Horloge horloge3 = new Horloge("Chambre", timerService);
        Horloge horloge4 = new Horloge("Bureau", timerService);
        
        System.out.println("\nÉtape 3: Observation du fonctionnement");
        System.out.println("Les horloges doivent s'actualiser AUTOMATIQUEMENT chaque seconde");
        System.out.println("Test en cours pendant 15 secondes...\n");
        
        // ✅ 3. Laisser tourner pour observer le fonctionnement
        try {
            Thread.sleep(15000); // 15 secondes d'observation
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // ✅ 4. Arrêt propre
        System.out.println("\nÉtape 4: Arrêt des horloges");
        horloge1.stop();
        horloge2.stop();
        horloge3.stop();
        horloge4.stop();
        
        System.out.println("✅ TEST RÉUSSI - Pattern Observer validé!");
    }
}
    */



package org.emp.gl.core.launcher;

import org.emp.gl.clients.Horloge;
import org.emp.gl.clients.CompteARebours; // ✅ AJOUTER CET IMPORT
import org.emp.gl.timer.service.TimerService;
import org.emp.gl.time.service.impl.DummyTimeServiceImpl;
import java.util.Random;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        testDuTimeService();
    }

    private static void testDuTimeService() {
        // Créer le TimerService
        TimerService timerService = new DummyTimeServiceImpl();
        
        System.out.println("=== TEST COMPLET PATTERN OBSERVER ===");
        
        // 1. Test avec Horloge
        System.out.println("\n1. Horloge normale:");
        Horloge horloge = new Horloge("Principal", timerService);
        
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // 2. Test avec CompteARebours de 5 secondes
        System.out.println("\n2. Compte à rebours de 5 secondes:");
        CompteARebours compte5s = new CompteARebours("Test-5s", timerService, 5);
        
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // 3. Test avec 10 comptes aléatoires
        System.out.println("\n3. 10 comptes à rebours aléatoires:");
        testerComptesAleatoires(timerService);
        
        System.out.println("\n=== TEST TERMINÉ ===");
    }
    
    private static void testerComptesAleatoires(TimerService timerService) {
        Random random = new Random();
        CompteARebours[] comptes = new CompteARebours[10];
        
        for (int i = 0; i < 10; i++) {
            int valeur = 10 + random.nextInt(11); // 10-20 secondes
            comptes[i] = new CompteARebours("Compte-" + (i+1), timerService, valeur);
        }
        
        System.out.println("⏳ Observation des 10 comptes...");
        
        // Attendre que tous se terminent
        try {
            Thread.sleep(25000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("✅ Tous les comptes sont terminés");
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}