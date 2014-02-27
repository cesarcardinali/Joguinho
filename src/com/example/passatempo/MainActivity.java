package com.example.passatempo;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button newGame;
	Button contGame;
	Button options;
	Button quitGame;

	static int contGa = -1;
	String name;
	Context context = this;
	Bundle data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		

		newGame = (Button) findViewById(R.id.newGame);
		newGame.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				final Dialog dialog = new Dialog(context);
				dialog.setContentView(R.layout.name);
				dialog.setTitle("Player name");
				Button buttonOk = (Button) dialog
						.findViewById(R.id.dialogButtonOK);
				buttonOk.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						EditText Sname = (EditText) dialog
								.findViewById(R.id.nameEdit);
						name = "" + Sname.getText();
						contGa = 0;
						Intent telaJogo = new Intent(MainActivity.this,
								JogoActivity.class);
						telaJogo.putExtra("name", name);
						telaJogo.putExtra("contGa", contGa);
						dialog.dismiss();
						MainActivity.this.startActivity(telaJogo);
					}
				});

				dialog.show();
			}
		});
		;

		contGame = (Button) findViewById(R.id.contGame);
		if(contGa != -1){
			data = getIntent().getExtras();
			contGa = data.getInt("contGa");
		}
		if (contGa > 0) {
			contGame.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Intent telaJogo = new Intent(MainActivity.this,
							JogoActivity.class);
					telaJogo.putExtra("contGa", 1);
					telaJogo.putExtra("p1", (Player)data.getSerializable("p1"));
					telaJogo.putExtra("bot", (Player)data.getSerializable("bot"));
					MainActivity.this.startActivity(telaJogo);
				}
			});

		} else {
			contGame.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					final Dialog dialog = new Dialog(context);
					dialog.setContentView(R.layout.nocont);
					dialog.setTitle("Erro:");
					Button buttonOk = (Button) dialog
							.findViewById(R.id.dialogButtonOK);
					buttonOk.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
						}
					});

					dialog.show();
				}
			});
			;
		}

		quitGame = (Button) findViewById(R.id.quitGame);
		quitGame.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent telaMenu = new Intent(MainActivity.this, MainActivity.class);
				telaMenu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				finish();
				System.exit(0);
			}
		});
		;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	protected void onResume() {
		super.onResume();
	}

}
