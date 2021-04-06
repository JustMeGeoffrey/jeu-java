package fr.pgah.libgdx;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Intro extends ApplicationAdapter {

  final int NB_SPRITES = 5;
  SpriteBatch batch;
  int longueurFenetre;
  int hauteurFenetre;
  ArrayList<Sprite> sprites;
  Sprite sprite;
  // Joueur joueur;
  Souris souris;
  boolean gameOver;
  Texture gameOverTexture;
  Sprite indexSprite;
  boolean listeSpritesVide;
  int niveauSprite;
  Random generateurAleatoire;
  int idAAttribuer;

  @Override
  public void create() {
    batch = new SpriteBatch();
    longueurFenetre = Gdx.graphics.getWidth();
    hauteurFenetre = Gdx.graphics.getHeight();
    listeSpritesVide = false;
    gameOver = false;
    gameOverTexture = new Texture("game_over.png");

    initialisationSprites();
    // initialiserJoueur();
    initialiserSouris();
  }

  private void initialisationSprites() {
    sprites = new ArrayList<Sprite>();
    idAAttribuer = 1;
    for (int i = 0; i < NB_SPRITES; i++) {
      niveauSprite = (int) (Math.random() * ((3 - 1) + 1)) + 1;
      if (niveauSprite == 1) {
        sprite = new Sprite("GreatJaggi_icon.jpg");
        sprite.setPv(niveauSprite);
        sprites.add(sprite);
      } else if (niveauSprite == 2) {
        sprite = new Sprite("Seregios_icon.jpg");
        sprite.setPv(niveauSprite);
        sprites.add(sprite);
      } else {
        sprite = new Sprite("Dalamadur_icon.jpg");
        sprite.setPv(niveauSprite);
        sprites.add(sprite);
      }
      sprite.setIdSprite(idAAttribuer);
      idAAttribuer++;
    }
  }

  // private void initialiserJoueur() {
  // joueur = new Joueur();
  // }

  private void initialiserSouris() {
    souris = new Souris();
  }

  @Override
  public void render() {
    // gameOver est mis à TRUE dès qu'un "hit" est repéré
    if (!gameOver) {
      reinitialiserArrierePlan();
      majEtatProtagonistes();
      majEtatJeu();
      dessiner();
    }
  }

  private void reinitialiserArrierePlan() {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  }

  private void majEtatProtagonistes() {
    // Sprites
    for (Sprite sprite : sprites) {
      sprite.majEtat();
    }

    // Souris
    souris.majEtat();
  }

  private void majEtatJeu() { // On vérifie si le jeu continue ou pas
    for (Sprite sprite : sprites) {
      if (sprite.estEnCollisionAvec(souris) && souris.clicGauche()) {
        indexSprite = sprite;
        System.out.println("Le sprite a été sélectionné");
        sprite.reduirePv();
        break;
      }
    }

    for (Sprite sprite : sprites) {
      if (sprite.getPv() == 0) {
        sprites.remove(indexSprite);
        break;
      }
    }

    if (sprites.isEmpty()) {
      gameOver = true;
    }

  }

  private void dessiner() {
    batch.begin();
    if (gameOver == true) {
      batch.draw(gameOverTexture, 0, 0);
    }
    if (gameOver == false) {
      // Affichage "normal", jeu en cours
      for (Sprite sprite : sprites) {
        sprite.dessiner(batch);
      }
      souris.dessinerSouris(batch);
    }

    batch.end();
  }
}
