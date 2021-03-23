package fr.pgah.libgdx;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Souris {

    int longueurFenetre;
    int hauteurFenetre;
    Texture img;
    int coordX;
    int coordY;
    int longueurEffective;
    int hauteurEffective;
    Rectangle zoneDeHit;
    int numeroSpriteTouche;

    public Souris() {
        img = new Texture("toto.png");
        longueurEffective = img.getWidth();
        hauteurEffective = img.getHeight();
        longueurFenetre = Gdx.graphics.getWidth();
        hauteurFenetre = Gdx.graphics.getHeight();
        zoneDeHit = new Rectangle(coordX, coordY, longueurEffective, hauteurEffective);
    }

    public void majEtat() {
        suivreSouris();
        forcerLaSourisAResterDansLeCadre();
    }

    private void suivreSouris() {
        coordX = Gdx.input.getX();
        coordY = Gdx.graphics.getHeight() - Gdx.input.getY();
    }

    private void forcerLaSourisAResterDansLeCadre() {
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

    public void dessinerSouris(SpriteBatch batch) {
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

    public boolean clicGauche() {
        if (entreeClicGauche()) {
            return true;
        }
        return false;
    }

    private boolean entreeClicGauche() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            return true;
        }
        return false;
    }

}