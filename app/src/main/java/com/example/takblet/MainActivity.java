package com.example.takblet;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.takblet.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

  private ActivityMainBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    EdgeToEdge.enable(this);
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    AppBarConfiguration appBarConfiguration =
        new AppBarConfiguration.Builder(
                R.id.navigation_quiz, R.id.navigation_stats, R.id.navigation_trivia)
            .build();
    NavController navController =
        Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    NavigationUI.setupWithNavController(binding.navView, navController);
  }
}
