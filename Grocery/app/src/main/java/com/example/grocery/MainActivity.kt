package com.example.grocery

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.grocery.fragment.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.navigationview_header.view.*

class MainActivity : AppCompatActivity() {

    var navigationPosition: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initView()
    }

    private fun initView() {
        setSupportActionBar(toolbar)
        setUpDrawerLayout()

        //Load category fragment first
        navigationPosition = R.id.nav_category
        navigateToFragment(CategoryFragment.newInstance())
        navigationView.setCheckedItem(navigationPosition)
        toolbar.title = getString(R.string.app_name)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_category -> {
                    toolbar.title = getString(R.string.category)
                    navigationPosition = R.id.nav_category
                    navigateToFragment(CategoryFragment.newInstance())
                }
                R.id.nav_order -> {
                    toolbar.title = getString(R.string.order)
                    navigationPosition = R.id.nav_order
                    navigateToFragment(OrderFragment.newInstance())
                }
                R.id.nav_profile -> {
                    toolbar.title = getString(R.string.profile)
                    navigationPosition = R.id.nav_profile
                    navigateToFragment(ProfileFragment.newInstance())
                }
                R.id.nav_contact -> {
                    toolbar.title = getString(R.string.contact)
                    navigationPosition = R.id.nav_contact
                    navigateToFragment(ContactFragment.newInstance())
                }
                R.id.nav_faq -> {
                    toolbar.title = getString(R.string.faq)
                    navigationPosition = R.id.nav_faq
                    navigateToFragment(FaqFragment.newInstance())
                }
                R.id.nav_Logout -> {
                    Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show()
                }
            }
            // set item as selected to persist highlight
            menuItem.isChecked = true
            // close drawer when item is tapped
            drawerLayout.closeDrawers()
            true
        }

        //Change navigation header information
        changeNavigationHeaderInfo()

        drawerLayout.addDrawerListener(object:DrawerLayout.DrawerListener{
            override fun onDrawerStateChanged(p0: Int) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDrawerSlide(p0: View, p1: Float) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDrawerClosed(p0: View) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDrawerOpened(p0: View) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    private fun changeNavigationHeaderInfo() {
        val headerView = navigationView.getHeaderView(0)
        headerView.textEmail.text = "lokeshdesai@android4dev.com"
    }

    private fun setUpDrawerLayout() {
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun navigateToFragment(fragmentToNavigate: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragmentToNavigate)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    @SuppressLint("WrongConstant")
    override fun onBackPressed() {

        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawer(Gravity.START)
        }

        if (navigationPosition == R.id.nav_category) {

            finishAffinity()

        } else {
            //Navigate to Category Fragment
            navigationPosition = R.id.nav_category
            navigateToFragment(CategoryFragment.newInstance())
            navigationView.setCheckedItem(navigationPosition)
            toolbar.title = getString(R.string.category)

//            super.onBackPressed()
//            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
        }
    }

}