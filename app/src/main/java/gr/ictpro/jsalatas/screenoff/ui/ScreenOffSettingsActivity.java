/*
  Screen Off: turn off and lock the screen of any Android 6.0
  (Marshmallow) or 7.0 (Nougat) device, in a way that it can be
  unlocked without violating the security policy that requires a
  PIN to unlock.
  Copyright (C) 2017 John Salatas

  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package gr.ictpro.jsalatas.screenoff.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import gr.ictpro.jsalatas.screenoff.R;

public class ScreenOffSettingsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_off_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        loadFragment(new AboutFragment(), false);
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager manager = getFragmentManager();
            if(manager.getBackStackEntryCount() > 0) {
                Fragment oldFragment = manager.findFragmentById(R.id.content_frame);
                changeMenuStatus(oldFragment, false);
                super.onBackPressed();
                Fragment currentFragment = manager.findFragmentById(R.id.content_frame);
                changeMenuStatus(currentFragment, true);
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        loadFragment(item.getItemId());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void loadFragment(int id) {
        if (id == R.id.nav_about) {
            loadFragment(new AboutFragment(), true);
        } else if (id == R.id.nav_license) {
            loadFragment(new LicenseFragment(), true);
        } else if (id == R.id.nav_settings) {
            loadFragment(new SettingsFragment(), true);
        } else if (id == R.id.nav_email) {
            Uri uri = Uri.parse("mailto:\"Screen Off App\"<screenoff@ictpro.gr>");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);

            startActivity(Intent.createChooser(intent, getString(R.string.email_chooser)));
        }
    }

    private void loadFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fm = getFragmentManager();
        if(addToBackStack) {
            fm.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
        } else {
            fm.beginTransaction().replace(R.id.content_frame, fragment).commit();
        }
    }

    private void changeMenuStatus(Fragment fragment, boolean checked) {
        int index = -1;
        if(fragment instanceof AboutFragment){
            index = 0;
        }
        else if(fragment instanceof LicenseFragment){
            index = 1;
        }
        else if(fragment instanceof SettingsFragment){
            index = 2;
        }
        if(index != -1) {
            navigationView.getMenu().getItem(index).setChecked(checked);
        }

    }
}
