package com.example.takblet.ui;

import android.os.Bundle;
import androidx.activity.OnBackPressedCallback;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceFragmentCompat;
import com.example.takblet.R;

public class SettingsFragment extends PreferenceFragmentCompat {

  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    setPreferencesFromResource(R.xml.root_preferences, rootKey);
  }

  @Override
  public void onDestroyView() {
    // TODO: 03.09.2024 хз не работает навигация назад с фрагмента настроек и все тут
    super.onDestroyView();
    requireActivity()
        .getOnBackPressedDispatcher()
        .addCallback(
            this,
            new OnBackPressedCallback(true) {
              @Override
              public void handleOnBackPressed() {
                NavController navController =
                    Navigation.findNavController(
                        requireActivity(), R.id.nav_host_fragment_activity_main);
                navController.popBackStack();
              }
            });
  }
}
