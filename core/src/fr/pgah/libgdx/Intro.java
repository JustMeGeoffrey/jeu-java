package fr.pgah.libgdx;

import java.util.ArrayList;

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

  @Override
  public void create() {
    batch = new SpriteBatch();
    longueurFenetre = Gdx.graphics.getWidth();
    hauteurFenetre = Gdx.graphics.getHeight();

    gameOver = false;
    gameOverTexture = new Texture("game_over.png");

    initialisationSprites();
    // initialiserJoueur();
    initialiserSouris();
  }

  private void initialisationSprites() {
    sprites = new ArrayList<Sprite>();
    for (int i = 0; i < NB_SPRITES; i++) {
      sprite = new Sprite("chien.png");
      sprites.add(sprite);
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
      }
    }
    sprites.remove(indexSprite);
  }

  private void dessiner() {
    batch.begin();
    // Affichage "normal", jeu en cours
    for (Sprite sprite : sprites) {
      sprite.dessiner(batch);
    }
    souris.dessinerSouris(batch);

    batch.end();
  }
}
