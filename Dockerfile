# Usa l'immagine ufficiale di Tomcat
FROM tomcat:9.0-jdk17-temurin

# Rimuovi le app di default di Tomcat (opzionale, per pulizia)
RUN rm -rf /usr/local/tomcat/webapps/*

# Copia il tuo file .war compilato nella cartella webapps di Tomcat
COPY Dividdi.war /usr/local/tomcat/webapps/ROOT.war

# Esponi la porta 8080
EXPOSE 8080

# Avvia Tomcat
CMD ["catalina.sh", "run"]
