# Joc miniRol — Projecte Java

Aquest projecte és un petit **joc de rol** desenvolupat en **Java**, on els personatges poden equipar **armes** i **armadures**, guanyar experiència i guardar o carregar la seva partida des de fitxers JSON.

---

## ⚙️ Requisits

- **Java 17** o superior  
- Llibreries externes:
  - [**Gson 2.10.1**](https://github.com/google/gson) → per llegir i escriure fitxers JSON

---

## Instruccions d’execució

### Opció 1: Sense Maven (execució directa)

1. **Compila el projecte:**
   ```bash
   javac -d out src/main/java/com/projecte/jocminiro/**/**/*.java

   Herència:
Les classes Arma i Armadura hereten d’una classe base Equipament.

Interfícies:
La interfície Accions defineix mètodes comuns per a tots els personatges (atacar(), defensar()...).

Classes estàtiques:
FitxerUtils conté mètodes estàtics per carregar i guardar dades JSON amb Gson.

Patró de disseny:
S’ha fet servir el patró Factory per crear objectes Arma o Armadura a partir de fitxers JSON.

Organització de paquets:

model/ → classes de domini (Personatge, Arma, Armadura)

game/ → lògica del joc (Joc.java, Combats.java)

utils/ → funcions auxiliars (FitxerUtils.java)




