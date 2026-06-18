## INSERT

Il cliente registrato visualizzare l'ordine nell'elenco degli ordini da lui effettuati
Admin: Visualizzare gli ordini complessivi, dalla data x alla data y, e per cliente

Immagini
CSS.
Produzione

## SCEMENZE

Separare il codice JSP dal codice CSS (usare i fogli di stile esterni) e dal codice JavaScript (usare gli script esterni) e organizzare opportunamente le risorse Web

I file JavaScript vanno nella cartella «scripts», le immagini (statiche) nella cartella «images» e i fogli di stile nella cartella «styles» (non in «WEB-INF»!)

A corredo del progetto va presentato il documento di website design (assicurarsi che sia consistente col sito web presentato)
Essendo un documento di design, la sua compilazione dovrebbe precedere lo sviluppo del sito web (prima si progetta, poi si sviluppa)!
Un template del documento di website design è disponibile per il download (vedi "P01b - Template di Website Design Document")


QUERY X UTENTE:
SELECT 
    o.seqId,
    MIN(o.data_ordine)             AS data_ordine,
    SUM(o.prezzo * o.quantita)     AS totale,
    i.nome,
    i.cognome,
    i.indirizzo,
    i.citta,
    i.cap,
    i.paese,
    GROUP_CONCAT(d.nome ORDER BY d.nome SEPARATOR ', ') AS dvd_acquistati
FROM ORDINE o
JOIN INDIRIZZO i ON i.seqId = o.seqId
JOIN DVD       d ON d.id    = o.dvd_id
WHERE o.utente_id = ?
GROUP BY 
    o.seqId,
    i.nome, i.cognome, i.indirizzo, i.citta, i.cap, i.paese
ORDER BY data_ordine DESC;

TUTTI GLI ORDINI INNTERVALLO DI DATE:
SELECT 
    o.seqId,
    MIN(o.data_ordine)                                   AS data_ordine,
    SUM(o.prezzo * o.quantita)                           AS totale,
    u.username,
    u.email,
    i.nome,
    i.cognome,
    i.citta,
    i.paese,
    GROUP_CONCAT(d.nome ORDER BY d.nome SEPARATOR ', ') AS dvd_acquistati
FROM ORDINE o
JOIN UTENTE    u ON u.id     = o.utente_id
JOIN INDIRIZZO i ON i.seqId  = o.seqId
JOIN DVD       d ON d.id     = o.dvd_id
WHERE o.data_ordine BETWEEN ? AND ?
GROUP BY 
    o.seqId, u.username, u.email,
    i.nome, i.cognome, i.citta, i.paese
ORDER BY data_ordine DESC;


Per cliente specifico in un intervallo di date:
sqlSELECT 
    o.seqId,
    MIN(o.data_ordine)                                   AS data_ordine,
    SUM(o.prezzo * o.quantita)                           AS totale,
    u.username,
    u.email,
    i.nome,
    i.cognome,
    i.citta,
    i.paese,
    GROUP_CONCAT(d.nome ORDER BY d.nome SEPARATOR ', ') AS dvd_acquistati
FROM ORDINE o
JOIN UTENTE    u ON u.id     = o.utente_id
JOIN INDIRIZZO i ON i.seqId  = o.seqId
JOIN DVD       d ON d.id     = o.dvd_id
WHERE o.utente_id = ?
  AND o.data_ordine BETWEEN ? AND ?
GROUP BY 
    o.seqId, u.username, u.email,
    i.nome, i.cognome, i.citta, i.paese
ORDER BY data_ordine DESC;
