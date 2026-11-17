import java.util.*;

// Enumeration des statuts
enum Statut {
    EN_ATTENTE,
    ACTIF,
    SUSPENDU,
    FERME
}

// Classe StudentAccount
class StudentAccount implements Cloneable {

    String id;
    String fullName;
    String email;
    String phone;
    String level;
    String major;
    boolean scholarship;
    List<String> courses;
    Statut statut;

    // Pattern Builder
    public static class Builder {

        String id;
        String fullName;
        String email;

        String phone = null;
        String level = "L1";
        String major = "";
        boolean scholarship = false;
        List<String> courses = new ArrayList<>();

        public Builder(String id, String fullName, String email) {

            if (id == null || id.isEmpty()||
                     fullName == null || fullName.isEmpty()||
                     email == null || !email.contains("@")) {

                throw new IllegalArgumentException(
                    "Informations invalides"
                );
            }

            this.id = id;
            this.fullName = fullName;
            this.email = email;
        }

        public Builder phone(String p) {
            phone = p;
            return this;
        }

        public Builder level(String l) {
            level = l;
            return this;
        }

        public Builder major(String m) {
            major = m;
            return this;
        }

        public Builder scholarship(boolean s) {
            scholarship = s;
            return this;
        }

        public Builder courses(List<String> c) {
            courses = new ArrayList<>(c);
            return this;
        }

        public StudentAccount build() {
            return new StudentAccount(this);
        }
    }

    // Constructeur privé
    private StudentAccount(Builder b) {

        id = b.id;
        fullName = b.fullName;
        email = b.email;
        phone = b.phone;
        level = b.level;
        major = b.major;
        scholarship = b.scholarship;
        courses = new ArrayList<>(b.courses);
        statut = Statut.EN_ATTENTE;
    }

    // Pattern Prototype
    @Override
    public StudentAccount clone() {

        try {
            StudentAccount copie =
                (StudentAccount) super.clone();

            copie.courses = new ArrayList<>(courses);

            return copie;

        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    // Affichage du compte
    public void afficher() {

        System.out.println("ID : " + id);
        System.out.println("Nom : " + fullName);
        System.out.println("Email : " + email);
        System.out.println("Telephone : " + phone);
        System.out.println("Niveau : " + level);
        System.out.println("Specialite : " + major);
        System.out.println("Bourse : " + scholarship);
        System.out.println("Cours : " + courses);
        System.out.println("Statut : " + statut);
        System.out.println("----------------------");
    }
}

// Pattern Singleton
class Depot {

    private static final Depot instance = new Depot();

    private Map<String, StudentAccount> comptes =
        new HashMap<>();

    private List<String> journal =
        new ArrayList<>();

    private Depot() {
    }

    public static Depot getInstance() {
        return instance;
    }

    // Ajouter un compte
    public void ajouter(StudentAccount compte) {

        if (comptes.containsKey(compte.id)) {
            throw new IllegalArgumentException(
                "ID deja utilise"
            );
        }

        comptes.put(compte.id, compte);
        journal.add("Ajout : " + compte.id);
    }

    // Rechercher un compte
    public StudentAccount chercher(String id) {
        return comptes.get(id);
    }

    // Afficher tous les comptes
    public void afficherTous() {

        for (StudentAccount s : comptes.values()) {
            s.afficher();
        }
    }

    // Afficher le journal
    public void afficherJournal() {
        System.out.println("Journal des operations :");
        
                for (String operation : journal) {
                    System.out.println(operation);
                }
            }
        }
        
        // Classe principale
        public class Main {
        
            public static void main(String[] args) {
        
                // Singleton
                Depot depot = Depot.getInstance();
        
                // Creation avec Builder
                StudentAccount etudiant =
                    new StudentAccount.Builder(
                        "001",
                        "Ali Benali",
                        "ali@gmail.com"
                    )
                    .phone("0550000000")
                    .level("L2")
                    .major("Informatique")
                    .scholarship(true)
                    .courses(Arrays.asList("Java", "UML"))
                    .build();
        
                // Ajouter le premier compte
                depot.ajouter(etudiant);
        
                // Prototype : copier le compte
                StudentAccount copie = etudiant.clone();
        
                copie.id = "002";
                copie.fullName = "Sara Ahmed";
                copie.email = "sara@gmail.com";
        
                // Ajouter le compte copie
                depot.ajouter(copie);
        
                // Afficher les comptes
                System.out.println("Liste des comptes :");
                depot.afficherTous();
        
                // Afficher le journal
                depot.afficherJournal();
        
                // Rechercher un compte
                System.out.println("Recherche du compte 001 :");
        
                StudentAccount resultat = depot.chercher("001");
        
                if (resultat != null) {
                    resultat.afficher();
                } else {
                    System.out.println("Compte introuvable");
                }
            }
        }