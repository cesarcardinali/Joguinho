package com.example.passatempo;

import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class JogoActivity extends Activity {

	String name;
	int Turn;

	Player P1, Bot;

	TextView Tname, Tgold, Twall, Tsold, Ttech, botName;

	EditText Ep1, Ebot;

	Button Battack, Bpass, Bwall, Btech, Bsold, Bopt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jogo);
		
		Bundle data = getIntent().getExtras();
		name = data.getString("name");
		P1 = new Player(name);
		Bot = new Player("AngryBot");
		Turn = 0;
		/*
		 * Toast.makeText(getApplicationContext(), P1.getName() + "\n" +
		 * P1.getGold() + "\n" + P1.getIncoming() + "\n" + P1.getSoldiers() +
		 * "\n" + P1.getTechnology() + "\n" + P1.getWalls() ,
		 * Toast.LENGTH_SHORT).show();
		 */

		Tname = (TextView) findViewById(R.id.PlayerName);
		Tname.setText(name + ": " + P1.getHealth());
		botName = (TextView) findViewById(R.id.BotName);
		Tgold = (TextView) findViewById(R.id.Tgold);
		Tgold.setText("" + P1.getGold());
		Twall = (TextView) findViewById(R.id.Twall);
		Twall.setText("" + P1.getWalls());
		Tsold = (TextView) findViewById(R.id.Tsold);
		Tsold.setText("" + P1.getSoldiers());
		Ttech = (TextView) findViewById(R.id.Ttech);
		Ttech.setText("" + P1.getTechnology());
		Ep1 = (EditText) findViewById(R.id.PlayerText);
		Ebot = (EditText) findViewById(R.id.BotText);

		Battack = (Button) findViewById(R.id.Battack);
		Battack.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (Turn > 0) {
					int Atc = P1.getSoldiers();
					int Def = Bot.getSoldiers() + Bot.getSoldiers()
							* Bot.getWalls() * 5 / 100;
					if (Atc > Def) {
						P1.setSoldiers(Atc - Def);
						Bot.setHealth(Bot.getHealth() - (Atc - Def));
						Bot.setSoldiers(0);
						Ep1.setText(Ep1.getText().insert(0, "Ataque efetivo!\nVoce causou " + (Atc - Def) + " de dano no inimigo\n"));
						Ebot.setText(Ebot.getText().insert(0, "Sua Defesa falhou!\nVoce perdeu " + (Atc - Def) + " de saude\n"));
						botName.setText("AngryBot: " + Bot.getHealth());
						if(VictoryVerify() == 1){
							Ep1.setText(Ep1.getText().insert(0, "\n\nVOCE VENCEU !!\n\n\n\n\n\n\n\n"));
							Ebot.setText(Ebot.getText().insert(0, "\n\nVOCE PERDEU !!\n\n\n\n\n\n\n\n"));
							buttonsFunc(false);
							//Dialog
						}
					} else if (Atc == Def) {
						P1.setSoldiers(0);
						Bot.setSoldiers(0);
						Ep1.setText(Ep1.getText().insert(0, "Ataque crítico!\nAmbas as tropas foram dizimadas\n"));
						Ebot.setText(Ebot.getText().insert(0, "Voce foi atacado e ambas as tropas padeceram\n"));
					} else if (Atc < Def) {
						P1.setSoldiers(0);
						Bot.setSoldiers(Bot.getSoldiers() - Atc);
						Ep1.setText(Ep1.getText().insert(0, "Ataque falho!\nVoce perdeu todas as tropas\n"));
						Ebot.setText(Ebot.getText().insert(0, "Defesa venceu!\nVoce teve " + (Def - Atc) + " baixas\n"));
					}
					statsUpdate(P1);
					BotPlay();
					P1.Refresh();
					statsUpdate(P1);
				} else {
					Ep1.setText(Ep1.getText().insert(0, "Voce só pode atacar após o primeiro turno\n"));
				}
			}
		});

		Bpass = (Button) findViewById(R.id.Bpass);
		Bpass.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Ebot.setText(Ebot.getText().insert(0, "Playing ...\n\n"));
				Ep1.setText(Ep1.getText().insert(0, "Waiting ...\n\n"));
				
				P1.Refresh();
				statsUpdate(P1);
				
				BotPlay();
				Bot.Refresh();
				
				Ebot.setText(Ebot.getText().insert(0, "Waiting ...\n\n"));
				Ep1.setText(Ep1.getText().insert(0, "Incoming: +" + P1.getIncoming() + " added\n\n"));
			}
		});

		Btech = (Button) findViewById(R.id.Btech);
		Btech.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				/*Toast.makeText(getApplicationContext(), P1.getTechnology() + "\n" + P1.getGold() ,
						 Toast.LENGTH_SHORT).show();*/

				if(P1.makeTech()){
					Ep1.setText(Ep1.getText().insert(0, "Technology upgraded\n"));
					statsUpdate(P1);
				} else{
					Ep1.setText(Ep1.getText().insert(0, "Not enought money\n"));
				}
			}
		});

		Bwall = (Button) findViewById(R.id.Bwall);
		Bwall.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(P1.makeWall()){
					Ep1.setText(Ep1.getText().insert(0, "Wall upgraded\n"));
					statsUpdate(P1);
				} else{
					Ep1.setText(Ep1.getText().insert(0, "Not enought money\n"));
				}
			}
		});

		Bsold = (Button) findViewById(R.id.Bsold);
		Bsold.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (P1.makeSold(1)) {
					Ep1.setText(Ep1.getText().insert(0, "I made +" + 1 + " soldiers\n"));
					statsUpdate(P1);
				} else {
					Ep1.setText(Ep1.getText().insert(0, "Not enought money\n"));
				}
			}
		});
		
		Bopt = (Button)findViewById(R.id.Bopt);
		
		
		buttonsFunc(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.jogo, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		Intent telaJogo = new Intent(JogoActivity.this, MainActivity.class);
		telaJogo.putExtra("contGa", 1); // Optional parameters
		JogoActivity.this.startActivity(telaJogo);
		// salvar status
	}
	
	private void BotPlay(){		
		Turn++;
		if (Bot.getTechnology() < 2){
			if (Bot.makeTech()){
				Ebot.setText(Ebot.getText().insert(0,
						"Upgraded technology\n"));
				int aux = Bot.getGold()/5;
				if (Bot.makeSold(aux)) {
					Ebot.setText(Ebot.getText().insert(0,
							"I made " + aux + " soldiers\n"));
				}
			} //else - pass
		} else if (Bot.getWalls() < 2){
			if (Bot.makeWall()){
				Ebot.setText(Ebot.getText().insert(0,
						"Upgraded walls\n"));
				int aux = Bot.getGold()/5;
				if (Bot.makeSold(aux)) {
					Ebot.setText(Ebot.getText().insert(0,
							"I made " + aux + " soldiers\n"));
				}
			}
		} else {
			int aux = Bot.getGold()/5;
			Bot.makeSold(aux);
			if (Bot.getSoldiers() > 15) {
				int Atc = Bot.getSoldiers();
				int Def = P1.getSoldiers() + P1.getSoldiers() * Bot.getWalls()
						* 5 / 100;
				if (Atc > Def) {
					Bot.setSoldiers(Atc - Def);
					P1.setHealth(P1.getHealth() - (Atc - Def));
					P1.setSoldiers(0);
					Ebot.setText(Ebot.getText().insert(0, "Ataque efetivo!\nVoce causou " + (Atc - Def) + " de dano no inimigo\n"));
					Ep1.setText(Ep1.getText().insert(0, "Sua Defesa falhou!\nVoce perdeu " + (Atc - Def) + " de saude\n"));
					Tname.setText(P1.getName() + ": " + P1.getHealth());
					if (VictoryVerify() == 1) {
						Ebot.setText(Ebot.getText().insert(0, "\n\nVOCE VENCEU !!\n\n\n\n\n\n\n\n"));
						Ep1.setText(Ep1.getText().insert(0, "\n\nVOCE PERDEU !!\n\n\n\n\n\n\n\n"));
						// Dialog
					}
				} else if (Atc == Def) {
					Bot.setSoldiers(0);
					P1.setSoldiers(0);
					Ebot.setText(Ebot.getText().insert(0, "Ataque crítico!\nAmbas as tropas foram dizimadas\n"));
					Ep1.setText(Ep1.getText().insert(0, "Voce foi atacado e ambas as tropas padeceram\n"));
				} else if (Atc < Def) {
					Bot.setSoldiers(0);
					P1.setSoldiers(P1.getSoldiers() - Atc);
					Ebot.setText(Ebot.getText().insert(0, "Ataque falho!\nVoce perdeu todas as tropas\n"));
					Ep1.setText(Ep1.getText().insert(0, "Defesa venceu!\nVoce teve " + (Def - Atc) + " baixas\n"));
				}
				statsUpdate(P1);
				if (VictoryVerify() == 2) {
					Ebot.setText(Ebot.getText().insert(0, "\n\nVOCE VENCEU !!\n\n\n\n\n\n\n\n"));
					Ep1.setText(Ep1.getText().insert(0, "\n\nVOCE PERDEU !!\n\n\n\n\n\n\n\n"));
					buttonsFunc(false);
					// Dialog
				}
			}
		}
		//Toast.makeText(getApplicationContext(), Bot.getGold() + "\n" + Bot.getSoldiers() + "\n" + Bot.getTechnology() + "\n" + Bot.getWalls(), Toast.LENGTH_SHORT).show();
	}
	
	private void statsUpdate(Player P){
		Tname.setText(name + ": " + P1.getHealth());
		Tgold.setText("" + P1.getGold());
		Twall.setText("" + P1.getWalls());
		Tsold.setText("" + P1.getSoldiers());
		Ttech.setText("" + P1.getTechnology());
	}
	
	private int VictoryVerify(){
		if(Bot.getHealth() < 1)
			return 1;
		if(P1.getHealth() < 1)
			return 2;
		return 0;
	}
	
	private void buttonsFunc(boolean onoff){
		Battack.setEnabled(onoff);
		Bpass.setEnabled(onoff);
		Bsold.setEnabled(onoff);
		Btech.setEnabled(onoff);
		Bwall.setEnabled(onoff);
		Bopt.setEnabled(onoff);
	}

}
