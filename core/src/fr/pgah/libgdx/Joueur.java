package fr.pgah.libgdx;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Joueur {

  final int vitesse = 10;
  int longueurFenetre;
  int hauteurFenetre;
  Texture img;
  int coordX;
  int coordY;
  int longueurEffective;
  int hauteurEffective;
  Rectangle zoneDeHit; // pour la détection des collisions

  public Joueur() {
    img = new Texture("toto.png");
    longueurEffective = img.getWidth();
    hauteurEffective = img.getHeight();
    longueurFenetre = Gdx.graphics.getWidth();
    hauteurFenetre = Gdx.graphics.getHeight();
    zoneDeHit = new Rectangle(coordX, coordY, longueurEffective, hauteurEffective);
  }

  public void majEtat() {
    deplacer();
    forcerAResterDansLeCadre();
  }

  private void deplacer() {
    if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
      coordX -= vitesse;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
      coordX += vitesse;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
      coordY += vitesse;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
      coordY -= vitesse;
    }

    // Coordonnées ont potentiellement changé
    // => Mise à jour zone de "hit"
    zoneDeHit.setPosition(coordX, coordY);
  }

  private void forcerAResterDansLeCadre() {
    // Gestion bordure droite
    if (coordX + longueurEffective > longueurFenetre) {
      coordX = longueurFenetre - longueurEffective;
    }

    // Gestion bordure gauche
    if (coordX < 0) {
      coordX = 0;
    }

    // Gestion bordures haute
    if (coordY + hauteurEffective > hauteurFenetre) {
      coordY = hauteurFenetre - hauteurEffective;
    }

    // Gestion bordure basse
    if (coordY < 0) {
      coordY = 0;
    }

    // Coordonnées ont potentiellement changé
    // => Mise à jour zone de "hit"
    zoneDeHit.setPosition(coordX, coordY);
  }

  public void dessiner(SpriteBatch batch) {
    batch.draw(img, coordX, coordY);
  }

  public boolean estEnCollisionAvec(ArrayList<Sprite> sprites) {
    // pour chaque sprite dans sprites
    // si le sprite touche le joueur
    // alors renvoyer vrai
    for (Sprite sprite : sprites) {
      if (estEnCollisionAvec(sprite)) {
        return true;
      }
    }
    // si on arrive ici, on a parcouru tout le tableau sans return,
    // donc on n'a aucune collision => faux
    return false;
  }

  private boolean estEnCollisionAvec(Sprite sprite) {
    // 'overlaps' est une méthode fournie par libGDX
    // Elle teste si 2 rectangles se touchent
    if (zoneDeHit.overlaps(sprite.zoneDeHit)) {
      return true;
    } else {
      return false;
    }
  }
}
