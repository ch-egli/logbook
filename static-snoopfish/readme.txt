Hosttech Konfiguration:
- Der "A Record" der Root-Domain (snoopfish.ch) zeigt auf den AWS Bucket "snoopfish.ch" mit der IP Adresse 54.231.193.47
- Der "CNAME Record" von www.snoopfish.ch zeigt auf den AWS Bucket www.snoopfish.ch
- Auf AWS leitet der Bucket "snoopfish.ch" alle Anfragen auf den Bucket "www.snoopfish.ch" weiter

Die Ausgangslage auf Hosttech (ohne eigene Webseite) ist im File Hosttech-Default-Configuration.png ersichtlich.
