package es.unex.giss.asee.whichnews.view.home
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import es.unex.giss.asee.whichnews.R
import es.unex.giss.asee.whichnews.data.models.News
import es.unex.giss.asee.whichnews.data.models.User
import es.unex.giss.asee.whichnews.databinding.ActivityHomeBinding
import es.unex.giss.asee.whichnews.login.UserManager
import es.unex.giss.asee.whichnews.view.FoldersActivity
import es.unex.giss.asee.whichnews.view.LoginActivity
import es.unex.giss.asee.whichnews.view.menu.HistoryFragment
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity(),
    NewsFragment.OnNewsClickListener,
    RecentFragment.OnNewsClickListener,
    LikesFragment.OnNewsClickListener,
    TrendingFragment.OnNewsClickListener,
    HistoryFragment.OnNewsClickListener,
    NavigationView.OnNavigationItemSelectedListener,
    NewsDetailFragment.OnNewsClickListener
{

    private lateinit var binding: ActivityHomeBinding

    private val navController by lazy{
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    private lateinit var newsFragment: NewsFragment
    private lateinit var trendingFragment: TrendingFragment
    private lateinit var likesFragment: LikesFragment
    private lateinit var recentFragment: RecentFragment

    companion object {
        const val USER_INFO = "USER_INFO"
        fun start(
            context: Context,
            user: User
        ){
            val intent = Intent(context, HomeActivity::class.java).apply{
                putExtra(USER_INFO, user)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
    }

    private fun setupUI(){
        newsFragment = NewsFragment()
        trendingFragment = TrendingFragment()
        likesFragment = LikesFragment()
        recentFragment = RecentFragment()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        binding.bottomNavigation.setupWithNavController(navHostFragment.navController)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        // Al navegar hacia la pantalla de detalles de la noticia, no se mostrará la bottom navigation bar
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.newsDetailFragment) {
                binding.toolbar.menu.clear()
                binding.bottomNavigation.visibility = View.GONE
            } else {
                binding.toolbar.visibility = View.VISIBLE
                binding.bottomNavigation.visibility = View.VISIBLE
            }
        }

    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                navController.navigate(R.id.newsFragment)
            }
            R.id.nav_folders -> {
                // TODO: go to folders fragment
                val intent = Intent(this, FoldersActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_settings -> {
                // TODO: go to settings fragment
            }
            R.id.nav_history -> {
                navController.navigate(R.id.historyFragment)
            }
            R.id.nav_logout -> {
                // Clearing session data
                lifecycleScope.launch { UserManager.clearData(applicationContext) }

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onNewsClick(news: News, fragmentId: Int) {
        when (fragmentId) {
            R.id.newsFragment -> {
                val action = NewsFragmentDirections.actionNewsFragmentToNewsDetailFragment(news)
                navController.navigate(action)
            }
            R.id.trendingFragment -> {
                val action = TrendingFragmentDirections.actionTrendingFragmentToNewsDetailFragment(news)
                navController.navigate(action)
            }
            R.id.likesFragment -> {
                val action = LikesFragmentDirections.actionLikesFragmentToNewsDetailFragment(news)
                navController.navigate(action)
            }
            R.id.recentFragment -> {
                val action = RecentFragmentDirections.actionRecentFragmentToNewsDetailFragment(news)
                navController.navigate(action)
            }
            R.id.newsDetailFragment -> {
                val action = NewsDetailFragmentDirections.actionNewsDetailFragmentToReviewsFragment(news)
                navController.navigate(action)
            }

        }
    }
}