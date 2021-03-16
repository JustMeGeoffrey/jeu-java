package fr.pgah.libgdx;

import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Sprite {

  int longueurFenetre;
  int hauteurFenetre;
  Texture img;
  int coordX;
  int coordY;
  boolean versLaDroite;
  boolean versLeHaut;
  double facteurTaille;
  int vitesse;
  float rotation;
  int vitesseRotation;
  int longueurEffective;
  int hauteurEffective;
  Random generateurAleatoire;
  Rectangle zoneDeHit;

  public Sprite(String img) {
    // On pourrait aussi copier tout le contenu de la méthode ici
    initialiser(img);
  }

  private void initialiser(String img) {
    longueurFenetre = Gdx.graphics.getWidth();
    hauteurFenetre = Gdx.graphics.getHeight();

    generateurAleatoire = new Random();
    this.img = new Texture(img);
    facteurTaille = 1;
    vitesse = 1 + generateurAleatoire.nextInt(10);
    rotation = 0;
    vitesseRotation = 5 + generateurAleatoire.nextInt(21);
    versLaDroite = generateurAleatoire.nextBoolean();
    versLeHaut = generateurAleatoire.nextBoolean();
    longueurEffective = (int) (this.img.getWidth() * facteurTaille);
    hauteurEffective = (int) (this.img.getHeight() * facteurTaille);
    coordX = generateurAleatoire.nextInt(longueurFenetre - longueurEffective);
    coordY = generateurAleatoire.nextInt(hauteurFenetre - hauteurEffective);
    zoneDeHit = new Rectangle(coordX, coordY, longueurEffective, hauteurEffective);
  }

  public void majEtat() {
    deplacer();
    pivoter();
    forcerAResterDansLeCadre();
  }

  public void pivoter() {
    rotation += vitesseRotation;
  }

  public void deplacer() {
    if (versLaDroite) {
      coordX += vitesse;
    } else {
      coordX -= vitesse;
    }
    if (versLeHaut) {
      coordY += vitesse;
    } else {
      coordY -= vitesse;
    }

    // Coordonnées modifiées => Mise à jour de la zone de "hit"
    zoneDeHit.setPosition(coordX, coordY);
  }

  public void forcerAResterDansLeCadre() {
    // Gestion bordure droite
    if (coordX + longueurEffective > longueurFenetre) {
      coordX = longueurFenetre - longueurEffective;
      versLaDroite = false;
    }

    // Gestion bordure gauche
    if (coordX < 0) {
      coordX = 0;
      versLaDroite = true;
    }

    // Gestion bordures haute
    if (coordY + hauteurEffective > hauteurFenetre) {
      coordY = hauteurFenetre - hauteurEffective;
      versLeHaut = false;
    }

    // Gestion bordure basse
    if (coordY < 0) {
      coordY = 0;
      versLeHaut = true;
    }

    // Coordonnées modifiées => Mise à jour de la zone de "hit"
    zoneDeHit.setPosition(coordX, coordY);

  }

  public void dessiner(SpriteBatch batch) {
    batch.draw(img, coordX, coordY, longueurEffective / 2, hauteurEffective / 2, longueurEffective, hauteurEffective, 1,
        1, rotation, 0, 0, img.getWidth(), img.getHeight(), false, false);
  }
}
