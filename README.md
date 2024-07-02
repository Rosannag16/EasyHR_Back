# EasyHR_Back

# Backend del Sistema di Gestione Ferie e Permessi

Questo progetto rappresenta il backend di un'applicazione per la gestione delle ferie e dei permessi dei dipendenti di un'azienda. Fornisce API RESTful per gestire le richieste di ferie e permessi, nonché funzionalità di autenticazione degli utenti.

## Tecnologie Utilizzate
- Java
- Spring Boot
- Spring Security
- PgaAdmin

## Prerequisiti
- Java JDK 8 o superiore
- Maven
- PgaAdmin

## Configurazione
1. Clona il repository sul tuo ambiente locale.
2. Configura il file `application.properties` (o `application.yml`) con le credenziali del database.
3. Esegui `mvn spring-boot:run` per avviare l'applicazione.

## Endpoints API

- **Autenticazione**
    - `POST /auth/login` - Per effettuare il login di un utente.

- **Gestione delle Ferie**
    - `GET /auth/request/ferie` - Recupera tutte le richieste di ferie.
    - `GET /auth/request/ferie/all` - Recupera tutte le richieste di ferie di tutti gli utenti.
    - `POST /auth/request/ferie` - Aggiunge una nuova richiesta di ferie.
    - `POST /auth/request/ferie/approve?ferieId={id}` - Approva una richiesta di ferie specificata.
    - `POST /auth/request/ferie/reject?ferieId={id}` - Rifiuta una richiesta di ferie specificata.
    - `PUT /auth/request/ferie/updateStatus/{id}` - Aggiorna lo stato di una richiesta di ferie.

- **Gestione dei Permessi**
    - `GET /auth/request/permessi/all` - Recupera tutte le richieste di permessi.
    - `GET /auth/request/permessi` - Recupera tutte le richieste di permessi di un utente specificato.
    - `POST /auth/request/permessi` - Aggiunge una nuova richiesta di permessi.
    - `POST /auth/request/permessi/approve?permessoId={id}` - Approva una richiesta di permessi specificata.
    - `POST /auth/request/permessi/reject?permessoId={id}` - Rifiuta una richiesta di permessi specificata.
    - `PUT /auth/request/permessi/updateStatus/{id}` - Aggiorna lo stato di una richiesta di permessi.

- **Gestione degli Utenti**
    - `GET /auth/users` - Recupera tutti gli utenti registrati nel sistema.
    - `POST /auth/register` - Registra un nuovo utente nel sistema.

## Documentazione API
Per ulteriori dettagli sugli endpoint disponibili e sugli schemi delle richieste e delle risposte, consulta la documentazione API fornita nella directory `docs` del progetto.
